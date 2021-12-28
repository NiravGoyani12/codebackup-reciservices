package com.rcibanque.rcidirect.services.core.exception;

import java.io.Serializable;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.response.APIError;

public class ExceptionUtils {


	private ExceptionUtils() {}


	/**
	 * Throws a {@link InternalServerErrorException}
	 * <br/>
	 * Error messages must have been added to context before calling this method
	 */
	public static final void throwInternalServerErrorException(IContext pContext) {

		throw new InternalServerErrorException(getAPIError(pContext));
	}


	/**
	 * Throws an {@link UnauthorizedException}
	 * <br/>
	 * Only to be used by authentication
	 */
	public static final void throwUnauthorizedException(Serializable pResponse) {

		throw new UnauthorizedException(pResponse);
	}


	/**
	 * Throws a {@link ForbiddenException}
	 * <br/>
	 * Error messages must have been added to context before calling this method
	 */
	public static final void throwForbiddenException(IContext pContext) {

		throw new ForbiddenException(getAPIError(pContext));
	}


	/**
	 * Throws a {@link InvalidRequestException}
	 * <br/>
	 * Error messages must have been added to context before calling this method
	 */
	public static final void throwInvalidRequestException(IContext pContext) {

		throw new InvalidRequestException(getAPIError(pContext));
	}


	/**
	 * Throws a {@link ServiceInvocationException}
	 * <br/>
	 * Error messages must have been added to context before calling this method
	 */
	public static final void throwServiceInvocationException(IContext pContext) {

		throw new ServiceInvocationException(getAPIError(pContext));
	}


	/**
	 * Throws a {@link DAOException}
	 * <br/>
	 * Should not be called by developer
	 * <br/>
	 * Generic error message is added to context in {@link ExceptionsHandler exception handler}
	 */
	public static final void throwDAOException(IContext pContext) {
		throw new DAOException(getAPIError(pContext));
	}


	/**
	 * Throws a {@link EntityNotFoundException}
	 * <br/>
	 * Error messages must have been added to context before calling this method
	 */
	public static final void throwEntityNotFoundException(IContext pContext) {

		throw new EntityNotFoundException(getAPIError(pContext));
	}

	/**
	 * Throws a {@link ValidationException}
	 * <br/>
	 * Error messages must have been added to context before calling this method
	 */
	public static final void throwValidationException(IContext pContext) {

		throw new ValidationException(getAPIError(pContext));
	}

	private static final APIError getAPIError(IContext pContext) {

		return new APIError(pContext.getMessages());
	}


	/**
	 * See {@link org.apache.commons.lang3.exception.ExceptionUtils#getStackTrace(Throwable)}
	 */
	public static final String getStackTrace(Exception pEx) {

		return org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(pEx);
	}
}
