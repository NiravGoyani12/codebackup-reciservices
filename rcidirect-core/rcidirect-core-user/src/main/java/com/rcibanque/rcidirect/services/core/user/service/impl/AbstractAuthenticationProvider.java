package com.rcibanque.rcidirect.services.core.user.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.rcibanque.rcidirect.services.core.domain.ContextFactory;
import com.rcibanque.rcidirect.services.core.domain.DateTime;
import com.rcibanque.rcidirect.services.core.exception.ExceptionUtils;
import com.rcibanque.rcidirect.services.core.property.dao.IPropertyDao;
import com.rcibanque.rcidirect.services.core.system.service.ISystemService;
import com.rcibanque.rcidirect.services.core.user.UserDetails;
import com.rcibanque.rcidirect.services.core.user.property.UserProperty;
import com.rcibanque.rcidirect.services.core.user.service.IAuthenticationService;
import com.rcibanque.rcidirect.services.core.user.service.IUserDetailsService;
import com.rcibanque.rcidirect.services.core.utils.ConvertUtils;

public abstract class AbstractAuthenticationProvider implements UserDetailsService, AuthenticationProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAuthenticationProvider.class);

	public static final Integer LOGIN_SUCCESS = 1;
	private static final Integer LOGIN_INVALID = -101;
	private static final Integer LOGIN_LOCKED = -102;


	@Autowired
	private IUserDetailsService _userDetailsService;

	@Autowired
	private IAuthenticationService _authenticationService;

	@Autowired
	private ISystemService _systemService;

	@Autowired
	private IPropertyDao _propertyDAO;


	@Override
	public UserDetails loadUserByUsername(String pUsername) throws UsernameNotFoundException {

		return _userDetailsService.loadUserByUsername(pUsername);
	}


	@Override
	public Authentication authenticate(Authentication pAuthentication) throws AuthenticationException {
		Authentication auth = null;
		try {

			String userName = (String) pAuthentication.getPrincipal();
			String password = (String) pAuthentication.getCredentials();

			int returnCode = _authenticationService.loginAuthenticate(userName, password);

			if (returnCode == LOGIN_SUCCESS) {
				UserDetails user = loadUserByUsername(userName);

				if(user != null && validateLogin(user)) {
					auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
					updateLastLoginTimestamp(user);
				}
				else {
					returnCode = LOGIN_INVALID;
				}
			}

			if (returnCode == LOGIN_INVALID) {
				throw new BadCredentialsException("Bad credentials");
			}
			else if (returnCode == LOGIN_LOCKED) {
				throw new LockedException("Account locked");
			}

		}
		catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// Unlikely, and the exceptions should probably not even be declared in the method's signature
			auth = null;
		}
		catch (DataAccessException e) {
			// Can happen for various reasons, so we need to catch the exception to hide DB details to client in case of failure
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw new InternalAuthenticationServiceException("Internal server error");
		}
		return auth;
	}

	protected boolean validateLogin(UserDetails pUser) {

		return true;
	}

	private void updateLastLoginTimestamp(UserDetails pUser) {

		_propertyDAO.updateOrInsert(ContextFactory.getContext(null), UserProperty.LAST_LOGGED_IN, pUser.getUser().getUserActorCode(), ConvertUtils.toStringDisplayV1(new DateTime(_systemService.getCurrentDateUpdatedTime())));
	}


	@Override
	public boolean supports(Class<?> pAuthentication) {
		return pAuthentication.equals(UsernamePasswordAuthenticationToken.class);
	}


}

