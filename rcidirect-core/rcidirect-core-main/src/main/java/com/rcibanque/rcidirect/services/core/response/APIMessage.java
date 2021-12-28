package com.rcibanque.rcidirect.services.core.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rcibanque.rcidirect.services.core.utils.MessageBundle;

/**
 * REST service call response message
 */
@JsonPropertyOrder({ "status", "description", "details" })
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class APIMessage implements Serializable, IMessage {

	private static final long serialVersionUID = -1L;


	@JsonIgnore
	private String _messageCode;

	private APIMessageStatus _status;

	private String _description;

	private List<APIMessageDetail> _details;


	public APIMessage(@JsonProperty("status") APIMessageStatus pStatus,
			@JsonProperty("description") String pDescription) {
		super();
		_status = pStatus;
		_description = pDescription;
		_details = new ArrayList<>();
	}


	public APIMessageStatus getStatus() {
		return _status;
	}

	public String getDescription() {
		return _description;
	}

	public List<APIMessageDetail> getDetails() {
		return _details;
	}

	public void setDetails(List<APIMessageDetail> pDetails) {
		_details = pDetails;
	}

	public String getMessageCode() {
		return _messageCode;
	}

	public void setMessageCode(String pMessageCode) {
		_messageCode = pMessageCode;
	}

	@Override
	public IMessage addDetail(Locale pLocale,
			String pDomainName, String pFieldName, String pReferenceId,
			MessageBundle pBundle, String pKey, Object... pParams) {

		String message = pBundle.getMessage(pLocale, pKey, pParams);

		return addDetail(pDomainName, pFieldName, pReferenceId, message);
	}

	IMessage addDetail(String pDomainName, String pFieldName, String pReferenceId, String pDescription) {

		APIMessageDetail detail = new APIMessageDetail(pDescription, pDomainName, pFieldName, pReferenceId);

		if(! _details.contains(detail)) {
			_details.add(detail);
		}

		return this;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
