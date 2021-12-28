package com.rcibanque.rcidirect.services.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.exception.ExceptionUtils;

public class EmailUtils {

	private EmailUtils() {}


	public static boolean templateExists(IContext pContext, Class<?> pClass, String pTemplateName) {

		return pClass.getResourceAsStream(pTemplateName) != null;
	}

	public static String loadTemplate(IContext pContext, Class<?> pClass, String pTemplateName) {

		String result = null;
		try {
			result = IOUtils.toString(pClass.getResourceAsStream(pTemplateName), StandardCharsets.ISO_8859_1);
		}
		catch (IOException e) {
			pContext.getMessages().log(e.getMessage());
			pContext.getMessages().error(CoreMessages.getInstance(), "error.email.loadTemplate", pTemplateName);
			ExceptionUtils.throwServiceInvocationException(pContext);
		}
		return result;
	}


	public static void sendEmail(IContext pContext, Email pEmail) {
		try {
			pEmail.send();
		}
		catch (EmailException e) {
			pContext.getMessages().log(e.getMessage());
			pContext.getMessages().error(CoreMessages.getInstance(), "error.email.sendMail");
			ExceptionUtils.throwServiceInvocationException(pContext);
		}
	}


	public static HtmlEmail constructEmailWithHTML(IContext pContext,
			String pHostName, Integer pPort,
			String pTo, String pFrom, String pFromName,
			String pSubject, String pHtmlMsg) {

		HtmlEmail email = new HtmlEmail();
		try {
			constructEmail(pContext, email, pHostName, pPort, pTo, pFrom, pFromName, pSubject);

			// Message
			email.setHtmlMsg(pHtmlMsg);
		}
		catch (EmailException e) {
			pContext.getMessages().log(e.getMessage());
			pContext.getMessages().error(CoreMessages.getInstance(), "error.email.createMail");
			ExceptionUtils.throwServiceInvocationException(pContext);
		}

		return email;
	}

	private static void constructEmail(IContext pContext, Email pEmail,
			String pHostName, Integer pPort,
			String pTo, String pFrom, String pFromName,
			String pSubject) throws EmailException {

		checkEmailAddress(pContext, pFrom);
		checkEmailAddress(pContext, pTo);

		pEmail.setHostName(pHostName);
		pEmail.setSmtpPort(pPort);
		pEmail.addTo(pTo);
		pEmail.setFrom(pFrom, pFromName);
		pEmail.setSubject(pSubject);
	}

	private static void checkEmailAddress(IContext pContext, String pAddress) {

		// Just a start, improve later if needed
		if(StringUtils.isBlank(pAddress)) {
			pContext.getMessages().error(CoreMessages.getInstance(), "error.email.invalidAddress");
			ExceptionUtils.throwServiceInvocationException(pContext);
		}
	}


	public static void addAttachment(IContext pContext, MultiPartEmail pEmail, EmailAttachment pAttachment) {

		if(pAttachment != null) {
			try {
				pEmail.attach(pAttachment);
			}
			catch (EmailException e) {
				pContext.getMessages().log(e.getMessage());
				pContext.getMessages().error(CoreMessages.getInstance(), "error.email.attachment", e.getMessage());
				ExceptionUtils.throwServiceInvocationException(pContext);
			}
		}
	}

	public static void addAttachment(IContext pContext, MultiPartEmail email, byte[] pAttachment, String pFileName, String pType) {

		if(pAttachment != null) {
			try {
				email.attach(new ByteArrayDataSource(pAttachment, pType), pFileName, null);
			}
			catch (EmailException e) {
				pContext.getMessages().log(e.getMessage());
				pContext.getMessages().error(CoreMessages.getInstance(), "error.email.attachment", e.getMessage());
				ExceptionUtils.throwServiceInvocationException(pContext);
			}
		}
	}


	public static void embedImage(IContext pContext, HtmlEmail pEmail, InputStream pImageIS, String pImageName, String pImageCID, String pImageMimeType) {

		try {
			DataSource imageSource = new ByteArrayDataSource(IOUtils.toByteArray(pImageIS), pImageMimeType);
			pEmail.embed(imageSource, pImageName, pImageCID);
		}
		catch (EmailException | IOException e) {
			pContext.getMessages().log(e.getMessage());
			pContext.getMessages().error(CoreMessages.getInstance(), "error.email.createMail");
			ExceptionUtils.throwServiceInvocationException(pContext);
		}
	}

}
