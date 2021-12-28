package com.rcibanque.rcidirect.services.core.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.rcibanque.rcidirect.services.core.domain.DateTime;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.domain.Page;
import com.rcibanque.rcidirect.services.core.domain.Price;
import com.rcibanque.rcidirect.services.core.exception.DAOException;
import com.rcibanque.rcidirect.services.core.exception.ExceptionUtils;
import com.rcibanque.rcidirect.services.core.utils.ConvertUtils;
import com.rcibanque.rcidirect.services.core.utils.DAOUtils;
import com.rcibanque.rcidirect.services.core.utils.MathUtils;
import com.rcibanque.rcidirect.services.core.utils.Timer;

/**
 * DAO
 * <ul>
 * <li>provides methods to run SQL queries on the database</li>
 * <li>provides methods to call procedures on the database</li>
 * <li>provides methods to manage search fields wild cards</li>
 * </ul>
 */
public abstract class DAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(DAO.class);


	private static final Character USER_WILDCARD = '*';

	private static final String ROW_MAPPER_RESULT_SET_PARAMETER_NAME = "result";

	protected static final Integer PROCEDURE_RESULT_OK = 0;


	private DataSource _dataSource;

	private NamedParameterJdbcTemplate _jdbcTemplate;

	private final Map<String, SimpleJdbcCall> _storedProcedures = new HashMap<>();


	/**
	 * The <i>primary</i> data source is auto-wired, but the method can be overridden to provide a different data source
	 *
	 * @param pDataSource Data source
	 */
	@Autowired
	public void setDataSource(DataSource pDataSource) {
		setDataSourceInternal(pDataSource);
	}

	private void setDataSourceInternal(DataSource pDataSource) {
		_dataSource = pDataSource;
		_jdbcTemplate = new NamedParameterJdbcTemplate(pDataSource);
	}

	private NamedParameterJdbcTemplate getJdbcTemplate() {
		return _jdbcTemplate;
	}

	private <T> SimpleJdbcCall getJdbcProcedureCall(String pProcedureName, RowMapper<T> pRowMapper, SqlOutParameter...pOutputParameters) {

		if(! _storedProcedures.containsKey(pProcedureName)) {

			synchronized(_storedProcedures) {

				if(! _storedProcedures.containsKey(pProcedureName)) {

					SimpleJdbcCall res = new SimpleJdbcCall(_dataSource);
					res.withProcedureName(pProcedureName);

					if(pRowMapper != null) {

						res.returningResultSet(ROW_MAPPER_RESULT_SET_PARAMETER_NAME, pRowMapper);
					}
					else if(pOutputParameters != null) {

						for(SqlOutParameter pOutputParameter : pOutputParameters) {
							res.addDeclaredParameter(pOutputParameter);
						}
					}

					_storedProcedures.put(pProcedureName, res);
				}
			}
		}

		return _storedProcedures.get(pProcedureName);
	}


	/**
	 * @return A new map to hold SQL parameters
	 */
	protected static final Map<String, Object> newParamsMap() {

		return new HashMap<>(8);
	}

	/**
	 * @param pSize Size of the array
	 * @return A new map array to hold SQL parameters (for <i>batch update</i>)
	 */
	@SuppressWarnings("unchecked")
	protected static final Map<String, Object>[] newParamsMapArray(int pSize) {

		Map<String, Object>[] res = new Map[pSize];
		for(int i=0; i<pSize; i++) {
			res[i] = newParamsMap();
		}
		return res;
	}


	/**
	 * @return A new key holder to retrieve auto-generated keys in insert statements
	 */
	protected static final KeyHolder newKeyHolder() {

		return new GeneratedKeyHolder();
	}

	/**
	 * Can be useful for <i>in</i> SQL clause
	 * @param pArray Array
	 * @return List containing the array elements
	 */
	protected static final <T> List<T> asList(T[] pArray) {

		return pArray != null ? Arrays.asList(pArray) : null;
	}


	/**
	 * See {@link NamedParameterJdbcTemplate#queryForObject(String, Map, RowMapper) }
	 */
	protected <T> T queryObject(String pSql, Map<String, Object> pParams, RowMapper<T> pRowMapper) {

		T res = null;

		try {
			res = getJdbcTemplate().queryForObject(pSql, pParams, pRowMapper);
		}
		catch (EmptyResultDataAccessException e) {
			// No result found: do nothing, return null
		}
		catch (IncorrectResultSizeDataAccessException e) {
			// Multiple results found: incorrect query, throw an error
			throw e;
		}

		return res;
	}

	/**
	 * See {@link NamedParameterJdbcTemplate#queryForObject(String, Map, Class) }
	 */
	protected <T> T queryObject(String pSql, Map<String, Object> pParams, Class<T> pRequiredType) {

		T res = null;

		try {
			res = getJdbcTemplate().queryForObject(pSql, pParams, pRequiredType);
		}
		catch (EmptyResultDataAccessException e) {
			// No result found: do nothing, return null
		}
		catch (IncorrectResultSizeDataAccessException e) {
			// Multiple results found: incorrect query, throw an error
			throw e;
		}

		return res;
	}

	/**
	 * See {@link NamedParameterJdbcTemplate#query(String, Map, RowMapper) }
	 */
	protected <T> List<T> queryList(String pSql, Map<String, Object> pParams, RowMapper<T> pRowMapper) {

		List<T> res = getJdbcTemplate().query(pSql, pParams, pRowMapper);

		return res;
	}

	/**
	 * See {@link NamedParameterJdbcTemplate#queryForList(String, Map, Class) }
	 */
	protected <T> List<T> queryList(String pSql, Map<String, Object> pParams, Class<T> pRequiredType) {

		List<T> res = getJdbcTemplate().queryForList(pSql, pParams, pRequiredType);

		return res;
	}

	/**
	 * See {@link DAO#queryList(String, Map, RowMapper) }
	 * </br></br>
	 * With "pagination"
	 */
	protected <T extends Serializable> Page<T> queryPage(String pSql, Map<String, Object> pParams, RowMapper<T> pRowMapper, Integer pPageNo, Integer pPageSize) {

		List<T> allRows = queryList(pSql, pParams, pRowMapper);

		return getPageRecords(allRows, pPageNo, pPageSize);
	}

	/**
	 * See {@link DAO#queryList(String, Map, Class) }
	 * </br></br>
	 * With "pagination"
	 */
	protected <T extends Serializable> Page<T> queryPage(String pSql, Map<String, Object> pParams, Class<T> pRequiredType, Integer pPageNo, Integer pPageSize) {

		List<T> allRows = queryList(pSql, pParams, pRequiredType);

		return getPageRecords(allRows, pPageNo, pPageSize);
	}

	private static <T extends Serializable> Page<T> getPageRecords(List<T> pAllRows, Integer pPageNo, Integer pPageSize) {

		Page<T> page = new Page<>();

		if (CollectionUtils.isEmpty(pAllRows)) {
			// No data => return "empty page"
			page.setPageNumber(0);
			page.setPagesAvailable(0);
			page.setPageItems(new ArrayList<T>());
		}
		else if (pPageSize == null || pPageSize <= 0) {
			// Invalid page size => return 1 page with all data
			page.setPageItems(pAllRows);
			page.setPageNumber(1);
			page.setPagesAvailable(1);
		}
		else {
			int rowCount = pAllRows.size();

			// calculate the number of pages
			int pageCount = rowCount / pPageSize;
			if (rowCount > pPageSize * pageCount) {
				pageCount++;
			}
			if (null == pPageNo || pPageNo == 0) {
				pPageNo = 1;
			} else if (pPageNo > pageCount) {
				pPageNo = pageCount;
			}
			page.setPageNumber(pPageNo);
			page.setPagesAvailable(pageCount);

			int startRow = (pPageNo - 1) * pPageSize;

			List<T> pageItems =  pAllRows.stream().skip(startRow).limit(pPageSize).collect(Collectors.toList());
			page.setPageItems(pageItems);
			if (CollectionUtils.isEmpty(pageItems)) {
				page.setPageNumber(0);
				page.setPagesAvailable(0);
			}
		}
		return page;
	}

	/**
	 * See {@link NamedParameterJdbcTemplate#update(String, Map) }
	 */
	protected int update(String pSql, Map<String, Object> pParams) {

		int res = getJdbcTemplate().update(pSql, pParams);

		return res;
	}

	/**
	 * See {@link NamedParameterJdbcTemplate#update(String, SqlParameterSource, KeyHolder) }
	 */
	protected int update(String pSql, Map<String, Object> pParams, KeyHolder pGeneratedKeyHolder) {

		int res = getJdbcTemplate().update(pSql, new MapSqlParameterSource(pParams), pGeneratedKeyHolder);

		return res;
	}

	/**
	 * See {@link NamedParameterJdbcTemplate#batchUpdate(String, Map[]) }
	 */
	protected int[] batchUpdate(String pSql, Map<String, Object>[] pParams) {

		int[] res = getJdbcTemplate().batchUpdate(pSql, pParams);

		return res;
	}


	/**
	 * See {@link SimpleJdbcCall#execute(Map) }
	 * <br/>
	 * See {@link #getOutputParameterMoney(String) }
	 *
	 * This method sets the return value attribute of Spring JDBC call to handle the return value of Store Procedure
	 * along with SQL output parameters
	 */
	protected Map<String, Object> executeProcedureWithReturnValue(String pProcedureName, Map<String, Object> pParams, SqlOutParameter...pOutputParameters) {

		SimpleJdbcCall jdbcCall = getJdbcProcedureCall(pProcedureName, null, pOutputParameters);

		jdbcCall.withReturnValue();

		Map<String, Object> res = executeProcedure(jdbcCall, pParams);

		return res;
	}


	/**
	 * See {@link SimpleJdbcCall#execute(Map) }
	 * <br/>
	 * See {@link #getOutputParameterMoney(String) }
	 *
	 * This method returns only the SQL ouput parameters and not the return status of the Stored Procedure
	 */
	protected Map<String, Object> executeProcedure(String pProcedureName, Map<String, Object> pParams, SqlOutParameter...pOutputParameters) {

		SimpleJdbcCall jdbcCall = getJdbcProcedureCall(pProcedureName, null, pOutputParameters);

		Map<String, Object> res = executeProcedure(jdbcCall, pParams);

		return res;
	}


	/**
	 * See {@link SimpleJdbcCall#execute(Map) }
	 *
	 * This method returns only the SQL records and not the return status of the Stored Procedure
	 */
	@SuppressWarnings("unchecked")
	protected <T> List<T> executeProcedure(String pProcedureName, Map<String, Object> pParams, RowMapper<T> pRowMapper) {

		SimpleJdbcCall jdbcCall = getJdbcProcedureCall(pProcedureName, pRowMapper);

		Map<String, Object> res = executeProcedure(jdbcCall, pParams);

		return (List<T>) res.get(ROW_MAPPER_RESULT_SET_PARAMETER_NAME);
	}

	private Map<String, Object> executeProcedure(SimpleJdbcCall pJdbcCall, Map<String, Object> pParams) {

		LOGGER.debug("Calling stored procedure '{}'", pJdbcCall.getProcedureName());

		Timer timer = null;
		if(LOGGER.isDebugEnabled()) {
			timer = Timer.getInstance(true);
		}

		Map<String, Object> res = pJdbcCall.execute(pParams);

		if(timer != null && LOGGER.isDebugEnabled()) {
			LOGGER.debug("Stored procedure '{}' execution time = {}", pJdbcCall.getProcedureName(), timer.getTimeTotal());
		}

		return res;
	}

	/**
	 * @param pParameterName Parameter name
	 * @return Stored procedure output parameter - double
	 */
	protected static final SqlOutParameter getOutputParameterDouble(String pParameterName) {

		return new SqlOutParameter(pParameterName, Types.DOUBLE);
	}

	/**
	 * @param pParameterName Parameter name
	 * @return Stored procedure output parameter - money
	 */
	protected static final SqlOutParameter getOutputParameterMoney(String pParameterName) {

		return new SqlOutParameter(pParameterName, Types.DECIMAL, MathUtils.SCALE_AMOUNT);
	}

	/**
	 * @param pParameterName Parameter name
	 * @return Stored procedure output parameter - integer
	 */
	protected static final SqlOutParameter getOutputParameterInteger(String pParameterName) {

		return new SqlOutParameter(pParameterName, Types.INTEGER);
	}

	/**
	 * @param pParameterName Parameter name
	 * @return Stored procedure output parameter - long
	 */
	protected static final SqlOutParameter getOutputParameterLong(String pParameterName) {

		return new SqlOutParameter(pParameterName, Types.BIGINT);
	}

	/**
	 * @param pParameterName Parameter name
	 * @return Stored procedure output parameter - string
	 */
	protected static final SqlOutParameter getOutputParameterString(String pParameterName) {

		return new SqlOutParameter(pParameterName, Types.VARCHAR);
	}

	/**
	 * @param pParameterName Parameter name
	 * @return Stored procedure output parameter - timestamp (i.e. date time)
	 */
	protected static final SqlOutParameter getOutputParameterTimestamp(String pParameterName) {

		return new SqlOutParameter(pParameterName, Types.TIMESTAMP);
	}


	/**
	 * Throw a {@link DAOException} if no rows have been impacted by the database query.
	 * <br/><br/>
	 * @see DAO#handleResult(int, int, IContext, String, Object...)
	 *
	 * @param pQueryResult Database query result (i.e. the number of rows impacted)
	 * @param pContext Context
	 * @param pMessage Technical message
	 * @param pParameters Technical message parameters
	 */
	protected static final void handleResult(int pQueryResult, IContext pContext, String pMessage, Object ... pParameters) {

		handleResult(pQueryResult, 1, pContext, pMessage, pParameters);
	}

	/**
	 * Throw a {@link DAOException} if the number rows impacted by the database query does not match the expected value.
	 * <br/><br/>
	 * @see DAO#handleError(IContext, String, Object...)
	 *
	 * @param pQueryResult Database query result (i.e. the number of rows impacted)
	 * @param pExpected Expected number of updated rows
	 * @param pContext Context
	 * @param pMessage Technical message
	 * @param pParameters Technical message parameters
	 */
	protected static final void handleResult(int pQueryResult, int pExpected, IContext pContext, String pMessage, Object ... pParameters) {

		if (pQueryResult < pExpected) {
			handleError(pContext, pMessage, pParameters);
		}
	}


	/**
	 * Throw a {@link DAOException} if the procedure result is not 0 (OK) and not handled by the handler
	 * <br/><br/>
	 * @see IProcedureResultHandler#handleResult(IContext, Integer)
	 * @see DAO#handleError(IContext, String, Object...)
	 *
	 * @param pProcedureName Procedure name
	 * @param pProcedureResult Procedure result
	 * @param pContext Context
	 * @param pHandler Procedure result handler
	 */
	protected static final void handleResult(String pProcedureName, Integer pProcedureResult, IContext pContext, IProcedureResultHandler pHandler) {

		if (! PROCEDURE_RESULT_OK.equals(pProcedureResult)) {
			if(pProcedureResult == null || pHandler == null || ! pHandler.handleResult(pContext, pProcedureResult)) {
				handleError(pContext, "Stored procedure \"{0}\" result = {1}", pProcedureName, pProcedureResult);
			}
		}
	}


	/**
	 * <ol>
	 * <li>Add a (technical) log message (not returned to the user) to the context</li>
	 * <li>Throw a {@link DAOException} with a generic error message</li>
	 * </ol>
	 *
	 * @param pContext Context
	 * @param pMessage Technical message
	 * @param pParameters Technical message parameters
	 */
	private static final void handleError(IContext pContext, String pMessage, Object ... pParameters) {

		// Technical log inside context
		pContext.getMessages().log(pMessage, pParameters);

		// Throw exception (and generic user message in exception handler)
		ExceptionUtils.throwDAOException(pContext);
	}


	/**
	 * Replace the user wild cards <b>*</b> into database compatible wild cards <b>%</b><br/>
	 * Also, escapes other database special characters the user might have used (for example: '%')
	 *
	 * @param pString String
	 * @return Database compatible string
	 */
	protected static final String handleWildcard(String pString) {

		return DAOUtils.escapeDatabaseSpecialCharacters(pString);
	}

	/**
	 * @param pString String
	 * @return True if the parameter contains user wild cards <b>*</b>
	 */
	protected static final boolean containsWildcard(String pString) {

		return StringUtils.contains(pString, USER_WILDCARD);
	}


	protected static final Integer getInt(ResultSet rs, String pColumnName) throws SQLException {

		Integer result = rs.getInt(pColumnName);

		if(rs.wasNull()) {
			result = null;
		}

		return result;
	}
	protected static final Integer getIntProperty(ResultSet rs, String pColumnName) throws SQLException {

		return ConvertUtils.parseInteger(rs.getString(pColumnName));
	}

	protected static final Long getLong(ResultSet rs, String pColumnName) throws SQLException {

		Long result = rs.getLong(pColumnName);

		if(rs.wasNull()) {
			result = null;
		}

		return result;
	}
	protected static final Long getLongProperty(ResultSet rs, String pColumnName) throws SQLException {

		return ConvertUtils.parseLong(rs.getString(pColumnName));
	}

	protected static final Double getDouble(ResultSet rs, String pColumnName) throws SQLException {

		Double result = rs.getDouble(pColumnName);

		if(rs.wasNull()) {
			result = null;
		}

		return result;
	}
	protected static final Double getDoubleProperty(ResultSet rs, String pColumnName) throws SQLException {

		return ConvertUtils.parseDouble(rs.getString(pColumnName));
	}

	protected static final BigDecimal getBigDecimal(ResultSet rs, String pColumnName) throws SQLException {

		BigDecimal result = rs.getBigDecimal(pColumnName);

		if(rs.wasNull()) {
			result = null;
		}

		return result;
	}
	protected static final BigDecimal getBigDecimalProperty(ResultSet rs, String pColumnName) throws SQLException {

		return ConvertUtils.parseBigDecimal(rs.getString(pColumnName));
	}

	protected static final Price getPrice(ResultSet rs, String pColumnNameExclVAT, String pColumnNameInclVAT) throws SQLException {

		Price res = new Price(getBigDecimal(rs, pColumnNameExclVAT), getBigDecimal(rs, pColumnNameInclVAT));
		return Price.isNull(res) ? null : res;
	}
	protected static final Price getPriceFromExclAndVat(ResultSet rs, String pColumnNameExclVAT, String pColumnNameVAT) throws SQLException {

		Price res = null;

		BigDecimal exclVat = getBigDecimal(rs, pColumnNameExclVAT);
		if(exclVat != null) {

			BigDecimal vat = getBigDecimal(rs, pColumnNameVAT);
			if(vat == null) {
				vat = MathUtils.ZERO;
			}

			res = new Price(exclVat, exclVat.add(vat));
		}

		return Price.isNull(res) ? null : res;
	}
	protected static final Price getPriceProperty(ResultSet rs, String pColumnNameExclVAT, String pColumnNameInclVAT) throws SQLException {

		Price res = new Price(getBigDecimalProperty(rs, pColumnNameExclVAT), getBigDecimalProperty(rs, pColumnNameInclVAT));
		return Price.isNull(res) ? null : res;
	}

	protected static final Boolean getBoolean(ResultSet rs, String pColumnName) throws SQLException {

		Boolean result = rs.getBoolean(pColumnName);

		if(rs.wasNull()) {
			result = null;
		}

		return result;
	}
	protected static final Boolean getBooleanProperty(ResultSet rs, String pColumnName) throws SQLException {

		return ConvertUtils.parseBoolean(rs.getString(pColumnName));
	}

	/**
	 * @See {@link #getDateTime(ResultSet, String)} to get time part
	 */
	protected static final Date getDate(ResultSet rs, String pColumnName) throws SQLException {

		return rs.getDate(pColumnName);
	}
	/**
	 * @See {@link #getDate(ResultSet, String)} to get only date part
	 */
	protected static final DateTime getDateTime(ResultSet rs, String pColumnName) throws SQLException {

		Timestamp timestamp = rs.getTimestamp(pColumnName);

		return timestamp != null ? new DateTime(timestamp) : null;
	}
	protected static final Date getDateProperty(ResultSet rs, String pColumnName) throws SQLException {

		return ConvertUtils.parseDateProperties(rs.getString(pColumnName));
	}

	protected static final String getString(ResultSet rs, String pColumnName) throws SQLException {

		return rs.getString(pColumnName);
	}
	protected static final String getStringProperty(ResultSet rs, String pColumnName) throws SQLException {

		return rs.getString(pColumnName);
	}


	protected static boolean doesColumnExist(String columnName, ResultSet rs) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		int numCol = meta.getColumnCount();
		for (int i = 1; i <= numCol; i++) {
			if(meta.getColumnLabel(i).equalsIgnoreCase(columnName) || meta.getColumnName(i).equalsIgnoreCase(columnName)) {
				return true;
			}
		}
		return false;
	}

}
