package com.rcibanque.rcidirect.services.core.user.dao;

import java.util.Date;

import com.rcibanque.rcidirect.services.core.domain.IContext;

public interface IAuthenticationDAO {


	void createPasswordResetToken(String pUserActorCode, String pToken, Date pExpiryDate);

	Date getResetPasswordTokenExpiryDate(String pUserActorCode, String pToken);

	void deleteResetPasswordToken(IContext pContext, String pUserActorCode);


	void updatePassword(IContext pContext, String pUserActorCode, String pPassword);

	void createHiPassword(IContext pContext, String pUserActorCode, String pPassword);


	int authenticate(String pUsername, String pPassword);

	boolean checkPasswordExistence(IContext pContext, String pUserActorCode, String pEncodedPassword, Integer pLastNPassword);


}