package com.rcibanque.rcidirect.services.core.dao;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.exception.DAOException;
import com.rcibanque.rcidirect.services.core.exception.ExceptionUtils;

public interface IProcedureResultHandler {

	/**
	 * Handle part or all of the stored procedure result codes:
	 * <ol>
	 * <li>Add error messages to the context</li>
	 * <li>Throw an exception ({@link ExceptionUtils#throwInvalidRequestException(IContext)} for example)</li>
	 * </ol>
	 *
	 * @param pContext Context
	 * @param pProcedureResult The procedure result
	 * @return False if the result was not handled: in this case a log message indicating the procedure result is added to the context and a {@link DAOException} (with a generic error message) is raised
	 */
	boolean handleResult(IContext pContext, Integer pProcedureResult);

}
