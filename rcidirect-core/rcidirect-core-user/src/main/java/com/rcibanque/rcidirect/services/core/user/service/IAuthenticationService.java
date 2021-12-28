package com.rcibanque.rcidirect.services.core.user.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.rcibanque.rcidirect.services.core.domain.IContext;

public interface IAuthenticationService {


	int loginAuthenticate(String pUserName, String pPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException;


	String createPasswordResetToken(String pUserName);

	Boolean isValidPasswordResetToken(String pToken, String pUserName);


	void changePassword(IContext pContext, String pUserName);

	void changePassword(IContext pContext, String pUserName, String pPassword);

}
