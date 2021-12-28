package com.rcibanque.rcidirect.services.core.exception;

import com.rcibanque.rcidirect.services.core.response.APIError;

/**
 * Runtime exception raised when:
 * <ul>
 * <li>the SQL cannot be called due to invalid input parameters</li>
 * <li>the SQL failed or did not give the expected result</li>
 * </ul>
 */
public final class DAOException extends AbstractException {

	private static final long serialVersionUID = 1L;


	DAOException(APIError pError) {
		super(pError);
	}

}
