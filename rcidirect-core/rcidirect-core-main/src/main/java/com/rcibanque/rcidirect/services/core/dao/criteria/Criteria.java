package com.rcibanque.rcidirect.services.core.dao.criteria;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Criteria for searches
 * <ul>
 * <li>provides 'language' criteria</li>
 * <li>provides 'max occurrences' criteria</li>
 * <li>provides 'current date' criteria</li>
 * <li>provides basic implementation of hashCode(), equals(), toString() methods</li>
 * <li>defines JSON serialization as field based</li>
 * </ul>
 */
@SuppressWarnings("serial")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class Criteria implements Serializable {

	@JsonIgnore
	private Integer _languageCode;

	@JsonIgnore
	private Integer _maxOccurences;

	@JsonIgnore
	private Date _currentDate;

	@JsonIgnore
	private Boolean _handleCache;

	private Integer _pageNo;

	private Integer _pageSize;


	public Criteria() {
		setHandleCache(Boolean.TRUE);
	}


	@JsonIgnore
	public final Integer getLanguageCode() {
		return _languageCode;
	}

	@JsonIgnore
	public final void setLanguageCode(Integer pLanguageCode) {
		_languageCode = pLanguageCode;
	}


	@JsonIgnore
	public final Integer getMaxOccurences() {
		return _maxOccurences;
	}

	@JsonIgnore
	public final void setMaxOccurences(Integer pMaxOccurences) {
		if(pMaxOccurences != null && pMaxOccurences > 0) {
			_maxOccurences = pMaxOccurences;
		}
	}


	@JsonIgnore
	public final Date getCurrentDate() {
		return _currentDate;
	}

	@JsonIgnore
	public final void setCurrentDate(Date pCurrentDate) {
		_currentDate = pCurrentDate;
	}


	@JsonIgnore
	public final Boolean getHandleCache() {
		return _handleCache;
	}

	/**
	 * By default, a cached DAO method will cache all results.<br/>
	 * <br/>
	 * To cache only some results, a SPeL condition has to be added to the cacheable annotation:<br/>
	 * <pre>
	 *    &#64;Cacheable(value = "myCache", key = "#pCriteria", <b>condition="#pCriteria.getHandleCache() == true"</b>)
	 *    public List getMyObjects(IContext pContext, MyCriteria pCriteria)
	 * </pre>
	 *
	 * @param pHandleCache False, to deactivate caching
	 */
	@JsonIgnore
	public final void setHandleCache(Boolean pHandleCache) {
		_handleCache = pHandleCache;
	}

	public Integer getPageNo() {
		return _pageNo;
	}

	public void setPageNo(Integer pPageNo) {
		this._pageNo = pPageNo;
	}

	public Integer getPageSize() {
		return _pageSize;
	}

	public void setPageSize(Integer pPageSize) {
		this._pageSize = pPageSize;
	}

	@Override
	public final int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}


	@Override
	public final boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}


	@Override
	public final String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}


}
