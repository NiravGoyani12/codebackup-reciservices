package com.rcibanque.rcidirect.services.core.user.service.impl;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.exception.ExceptionUtils;
import com.rcibanque.rcidirect.services.core.system.domain.ParamExEnum;
import com.rcibanque.rcidirect.services.core.system.service.IParameterService;
import com.rcibanque.rcidirect.services.core.system.service.ISystemService;
import com.rcibanque.rcidirect.services.core.user.IUser;
import com.rcibanque.rcidirect.services.core.user.UserDetails;
import com.rcibanque.rcidirect.services.core.user.dao.IAuthenticationDAO;
import com.rcibanque.rcidirect.services.core.user.service.IAuthenticationService;
import com.rcibanque.rcidirect.services.core.user.service.IUserDetailsService;
import com.rcibanque.rcidirect.services.core.user.utils.CoreUserMessages;
import com.rcibanque.rcidirect.services.core.utils.DateUtils;
import com.rcibanque.rcidirect.services.core.utils.TextUtils;

@Service
public class AuthenticationService implements IAuthenticationService {

	private static final int PASSWORD_MIN_LENGTH = 6;

	private static final int PASSWORD_MAX_LENGTH = 10;

	private static final List<String> FORBIDDEN_PASSWORDS = Arrays.asList("rci", "renault", "nissan");

	private static final List<String> KEYBOARD_SEQUENCES = Arrays.asList("azertyuiop", "qsdfghjklm", "wxcvbn", "qwertyuiop", "asdfghjkl", "zxcvbnm", "01234567890");

	private static final int KEYBOARD_SEQUENCES_MIN_LENTH_FORBIDDEN = 3;

	private static final Integer LAST_N_PASSWORD = 6;


	@Autowired
	private IAuthenticationDAO _authenticationDAO;

	@Autowired
	private IUserDetailsService _userDetailsService;

	@Autowired
	private ISystemService _systemService;

	@Autowired
	private IParameterService _parameterService;


	@Override
	public int loginAuthenticate(String pUserName, String pPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		return _authenticationDAO.authenticate(pUserName.trim(), encodePassword(pUserName, pPassword));
	}


	private String encodePassword(String pUserName, String pPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String salt = _parameterService.getParamExValue(null, ParamExEnum.LOGIN_ENCRYPTION_SALT);

		String rawPassword = StringUtils.trim(pUserName) + salt + StringUtils.trim(pPassword);

		MessageDigest messageDigest = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_1);

		byte[] digest= messageDigest.digest(rawPassword.getBytes(StandardCharsets.UTF_8.name()));

		return Hex.encodeHexString(digest).toLowerCase();
	}

	private String encodePassword(IContext pContext, String pUserName, String pPassword) {

		String res = null;

		try {
			res = encodePassword(pUserName, pPassword);
		}
		catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			pContext.getMessages().error(CoreUserMessages.getInstance(), "user.password.change.encrytion.fail");
			ExceptionUtils.throwServiceInvocationException(pContext);
		}

		return res;
	}


	@Override
	public Boolean isValidPasswordResetToken(String pToken, String pUserName) {
		Boolean res = Boolean.FALSE;

		UserDetails user = _userDetailsService.loadUserByUsername(pUserName);

		if(user != null) {	// Silent if invalid !

			Date tokenExpiryDate = _authenticationDAO.getResetPasswordTokenExpiryDate(user.getUser().getUserActorCode(), pToken);

			res = tokenExpiryDate != null && tokenExpiryDate.after(_systemService.getCurrentDateUpdatedTime());
		}

		return res;
	}


	@Override
	public String createPasswordResetToken(String pUserName) {

		String token = null;

		UserDetails user = _userDetailsService.loadUserByUsername(pUserName);

		if(user != null) { // Silent if invalid !

			token = createPasswordResetToken(user.getUser());
		}

		return token;
	}

	private String createPasswordResetToken(IUser pUser) {

		String token = StringUtils.remove(UUID.randomUUID().toString(), "-");

		Integer hoursBeforeExpiry = _parameterService.getParamExValue(null, ParamExEnum.LOGIN_PASSWORD_FORGOTTEN_EXPIRY_TIME);
		if(hoursBeforeExpiry == null) {
			hoursBeforeExpiry = Integer.valueOf(24);
		}

		Date tokenExpiryDate = DateUtils.addTime(_systemService.getCurrentDateUpdatedTime(), hoursBeforeExpiry, null);

		_authenticationDAO.createPasswordResetToken(pUser.getUserActorCode(), token, tokenExpiryDate);

		return token;
	}


	@Override
	public void changePassword(IContext pContext, String pUserName) {

		UserDetails user = _userDetailsService.loadUserByUsername(pUserName);

		if(user != null) { // Silent if invalid !

			String password = generatePassword(pContext, user.getUser());

			changePassword(pContext, user.getUser(), password);
		}
	}

	@Override
	public void changePassword(IContext pContext, String pUserName, String pPassword) {

		UserDetails user = _userDetailsService.loadUserByUsername(pUserName);

		if(user != null) { // Silent if invalid !

			changePassword(pContext, user.getUser(), pPassword);
		}
	}

	private void changePassword(IContext pContext, IUser pUser, String pPassword) {

		String encodedPassword = encodePassword(pContext, pUser.getUsername(), pPassword);

		validatePassword(pContext, pUser, pPassword, encodedPassword);

		String userActorCode = pUser.getUserActorCode();

		_authenticationDAO.updatePassword(pContext, userActorCode, encodedPassword);
		_authenticationDAO.createHiPassword(pContext, userActorCode, encodedPassword);
		_authenticationDAO.deleteResetPasswordToken(pContext, userActorCode);
	}


	private String generatePassword(IContext pContext, IUser pUser) {

		String password = null;

		for(int i=0; i<100; i++) {

			password = TextUtils.generateRandomString(8);

			String encodedPassword = encodePassword(pContext, pUser.getUsername(), password);

			String error = getPasswordValidationError(pContext, pUser, password, encodedPassword);

			if(StringUtils.isBlank(error)) {
				break;
			}
		}

		return password;
	}

	private void validatePassword(IContext pContext, IUser pUser, String pPassword, String pEncodedPassword) {

		String errorMessageKey = getPasswordValidationError(pContext, pUser, pPassword, pEncodedPassword);

		if(StringUtils.isNotBlank(errorMessageKey)) {
			pContext.getMessages().error(CoreUserMessages.getInstance(), errorMessageKey);
			ExceptionUtils.throwInvalidRequestException(pContext);
		}
	}

	private String getPasswordValidationError(IContext pContext, IUser pUser, String pPassword, String pEncodedPassword) {

		String res = null;

		String pwd = StringUtils.trim(pPassword);

		if (pwd == null || pwd.length() < PASSWORD_MIN_LENGTH || pwd.length() > PASSWORD_MAX_LENGTH) {
			res = "user.password.change.invalid.length";
		}

		if (pwd != null) {

			if (checkCharacters(pwd)) {
				res = "user.password.change.must.include.letter.and.digit";
			}

			if (FORBIDDEN_PASSWORDS.stream().anyMatch(forbidden -> StringUtils.containsIgnoreCase(pwd, forbidden))) {
				res = "user.password.change.contains.forbidden.word";
			}

			if (pwd.equalsIgnoreCase(pUser.getUsername())) {
				res = "user.password.change.equals.login";
			}

			if (checkPasswordEqualsName(pContext, pUser, pwd)) {
				res = "user.password.change.equals.name";
			}

			if (checkKeyboardSequences(pwd)) {
				res = "user.password.change.series.of.letters";
			}

			if(checkPasswordExistence(pContext, pUser.getUserActorCode(), pEncodedPassword)) {
				res = "user.password.change.part.of.last.passwords";
			}
		}

		return res;
	}

	private boolean checkPasswordExistence(IContext pContext, String pUserActorCode, String pEncodedPassword) {
		return _authenticationDAO.checkPasswordExistence(pContext, pUserActorCode, pEncodedPassword, LAST_N_PASSWORD);
	}

	private boolean checkPasswordEqualsName(IContext pContext, IUser pUser, String pPassword) {

		return pPassword.equalsIgnoreCase(StringUtils.trim(pUser.getFirstName()))
				|| pPassword.equalsIgnoreCase(StringUtils.trim(pUser.getLastName()));
	}

	private boolean checkKeyboardSequences(String pPassword) {
		for (String keyboard : KEYBOARD_SEQUENCES) {
			for (int x = 0; x < keyboard.length(); x++) {
				for (int y = 0; y < pPassword.length(); y++) {
					if (keyboard.regionMatches(x, pPassword.toLowerCase(), y, KEYBOARD_SEQUENCES_MIN_LENTH_FORBIDDEN)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private boolean checkCharacters(String pPassword) {
		boolean containsLetter = false;
		boolean containsDigit = false;

		for (int x = 0; x < pPassword.length(); x++) {
			int value = pPassword.charAt(x);

			if (value >= 48 && value <= 57) {
				containsDigit = true;
			}
			else if (value >= 65 && value <= 90) {
				containsLetter = true;
			}
			else if (value >= 97 && value <= 122) {
				containsLetter = true;
			}
		}

		return ! (containsDigit && containsLetter);
	}


}