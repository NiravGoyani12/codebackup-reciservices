package com.rcibanque.rcidirect.services.core.exception;

import java.io.Serializable;
import java.util.MissingResourceException;

import org.apache.commons.lang3.SerializationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.rcibanque.rcidirect.services.core.domain.ContextFactory;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.response.APIError;
import com.rcibanque.rcidirect.services.core.response.APIResponse;
import com.rcibanque.rcidirect.services.core.utils.CoreMessages;
import com.rcibanque.rcidirect.services.core.utils.IteratorUtils;



/**
 * Exception handler managed by Spring, which does not need to be called explicitly.
 * <br/><br/>
 * This handler returns the standard {@link APIResponse}.
 */
@ControllerAdvice
public class ExceptionsHandler {


	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionsHandler.class);


	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public APIResponse<Void> rulesForEntityNotFoundException(EntityNotFoundException pException) {

		return logError(pException, false, pException.getError());
	}


	@ExceptionHandler(ForbiddenException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ResponseBody
	public APIResponse<Void> rulesForForbiddenException(ForbiddenException pException) {

		return logError(pException, false, pException.getError());
	}


	@ExceptionHandler(UnauthorizedException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public Serializable rulesForUnauthorizedException(UnauthorizedException pException) {

		return pException.getResponse();
	}


	@ExceptionHandler({InvalidRequestException.class, ValidationException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public APIResponse<Void> rulesForBadRequestExceptions(AbstractException pException) {

		return logError(pException, false, pException.getError());
	}


	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public APIResponse<Void> rulesForMissingServletRequestParameterException(MissingServletRequestParameterException pException) {

		return logError(pException, false, "error.missingParameterError", pException.getParameterName());
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public APIResponse<Void> rulesForMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException  pException) {

		return logError(pException, false, "error.argument.invalid", pException.getValue(), pException.getName());
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public APIResponse<Void> rulesForHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException pException) {

		return logError(pException, false, "error.http.method.unsupported", pException.getMethod());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public APIResponse<Void> rulesForHttpMessageNotReadableException(HttpMessageNotReadableException pException) {

		APIResponse<Void> res = null;

		if(pException.getCause() instanceof InvalidFormatException) {
			InvalidFormatException ife = (InvalidFormatException) pException.getCause();

			Object value = ife.getValue();
			StringBuilder fieldName = new StringBuilder();
			for(Reference path : IteratorUtils.nullableIterable(ife.getPath())) {
				fieldName.append("/").append(path.getFieldName());
			}
			if(fieldName.length() == 0) {
				fieldName.append("?");
			}

			res = logError(pException, false, "error.argument.invalid", value, fieldName);
		}
		else if(pException.getCause() instanceof MismatchedInputException) {
			MismatchedInputException mie = (MismatchedInputException) pException.getCause();

			if(mie.getLocation() != null) {
				res = logError(pException, false, "error.http.message.invalid.with.location", mie.getLocation().getLineNr(), mie.getLocation().getColumnNr());
			}
		}

		// Has precise error been handled?
		if(res == null) {
			res = logError(pException, false, "error.http.message.invalid.general", pException.getMessage());
		}

		return res;
	}

	@ExceptionHandler(AccessDeniedException.class)
	public void rulesForAccessDeniedException(AccessDeniedException pException) {

		// Let Spring handle the exception
		throw pException;
	}


	@ExceptionHandler({Exception.class, SerializationException.class, MissingResourceException.class})
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public APIResponse<Void> rulesForException(Exception pException) {

		return logError(pException, true, "error.internalServerError");
	}


	@ExceptionHandler(InternalServerErrorException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public APIResponse<Void> rulesForInternalServerErrorException(InternalServerErrorException pException) {

		return logError(pException, true, pException.getError());
	}


	@ExceptionHandler(DAOException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public APIResponse<Void> rulesForDAOException(DAOException pException) {

		// Add generic error message
		pException.getError().getMessages().error(CoreMessages.getInstance(), "error.databaseError");

		return logError(pException, true, pException.getError());
	}


	@ExceptionHandler(ServiceInvocationException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public APIResponse<Void> rulesForServiceInvocationException(ServiceInvocationException pException) {

		return logError(pException, true, pException.getError());
	}


	private APIResponse<Void> logError(Exception pException, boolean pLogStackTrace, String pMessageKey, Object...pParameters) {

		// OK to get a new context IF we have no access to the actual one
		IContext context = ContextFactory.getContext();

		context.getMessages().error(CoreMessages.getInstance(), pMessageKey, pParameters);

		return logError(pException, pLogStackTrace, new APIError(context.getMessages()));
	}


	private APIResponse<Void> logError(Exception pException, boolean pLogStackTrace, APIError pError) {

		// Log exception with stack trace and tracking ID: it is easier to search in logs if we are given the ID
		StringBuilder sb = new StringBuilder(StringUtils.LF);

		sb.append("ERROR-TRACKING-ID : ").append(pError.getErrorTrackingID()).append(StringUtils.LF);

		sb.append("ERROR-MESSAGES : ").append(StringUtils.LF);
		sb.append(pError.getMessages()).append(StringUtils.LF);

		if(pLogStackTrace) {
			sb.append("STACK-TRACE : ").append(StringUtils.LF);
			sb.append(ExceptionUtils.getStackTrace(pException));
		}

		LOGGER.error(sb.toString());

		return new APIResponse<>(pError);
	}

}
