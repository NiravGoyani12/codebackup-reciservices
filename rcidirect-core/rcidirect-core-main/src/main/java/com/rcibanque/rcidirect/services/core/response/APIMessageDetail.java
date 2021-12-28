package com.rcibanque.rcidirect.services.core.response;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "description", "domain", "field", "reference_key" })
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class APIMessageDetail implements Serializable {

	private static final long serialVersionUID = -1L;


	private String _description;

	private String _domain;

	private String _field;

	private String _referenceKey;


	public APIMessageDetail(@JsonProperty("description") String pDescription, @JsonProperty("domain") String pDomain, @JsonProperty("field") String pField, @JsonProperty("reference_key") String pReferenceKey) {
		_description = pDescription;
		_domain = pDomain;
		_field = pField;
		_referenceKey = pReferenceKey;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String pDescription) {
		_description = pDescription;
	}

	public String getDomain() {
		return _domain;
	}

	public void setDomain(String pDomain) {
		_domain = pDomain;
	}

	public String getField() {
		return _field;
	}

	public void setField(String pField) {
		_field = pField;
	}

	public String getReferenceKey() {
		return _referenceKey;
	}

	public void setReferenceKey(String pReferenceKey) {
		_referenceKey = pReferenceKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_description == null) ? 0 : _description.hashCode());
		result = prime * result + ((_domain == null) ? 0 : _domain.hashCode());
		result = prime * result + ((_field == null) ? 0 : _field.hashCode());
		result = prime * result + ((_referenceKey == null) ? 0 : _referenceKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		APIMessageDetail other = (APIMessageDetail) obj;

		if (_description == null) {
			if (other._description != null) {
				return false;
			}
		} else if (!_description.equals(other._description)) {
			return false;
		}

		if (_domain == null) {
			if (other._domain != null) {
				return false;
			}
		} else if (!_domain.equals(other._domain)) {
			return false;
		}

		if (_field == null) {
			if (other._field != null) {
				return false;
			}
		} else if (!_field.equals(other._field)) {
			return false;
		}

		if (_referenceKey == null) {
			if (other._referenceKey != null) {
				return false;
			}
		} else if (!_referenceKey.equals(other._referenceKey)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}




}
