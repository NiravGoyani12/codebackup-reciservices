package com.rcibanque.rcidirect.services.core.utils.http;

import com.rcibanque.rcidirect.services.core.domain.DTO;

public class HttpResponse extends DTO {

	private static final long serialVersionUID = 1L;

	private boolean _error;
	private String _errorMessage;
	private int _httpResponseCode;
	private String _httpResponseMessage;
	private String _output;

	public boolean getError() {
		return _error;
	}

	public void setError(boolean pError) {
		_error = pError;
	}

	public String getErrorMessage() {
		return _errorMessage;
	}

	public void setErrorMessage(String pErrorMessage) {
		_errorMessage = pErrorMessage;
	}

	public int getHttpResponseCode() {
		return _httpResponseCode;
	}

	public void setHttpResponseCode(int pHttpResponseCode) {
		_httpResponseCode = pHttpResponseCode;
	}

	public String getHttpResponseMessage() {
		return _httpResponseMessage;
	}

	public void setHttpResponseMessage(String pHttpResponseMessage) {
		_httpResponseMessage = pHttpResponseMessage;
	}

	public String getOutput() {
		return _output;
	}

	public void setOutput(String pOutput) {
		_output = pOutput;
	}

}
