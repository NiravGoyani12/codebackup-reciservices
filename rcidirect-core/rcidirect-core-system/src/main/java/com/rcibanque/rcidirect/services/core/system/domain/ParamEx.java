package com.rcibanque.rcidirect.services.core.system.domain;

import com.rcibanque.rcidirect.services.core.domain.DTO;
import com.rcibanque.rcidirect.services.core.utils.ConvertUtils;

public class ParamEx extends DTO {

	private static final long serialVersionUID = -1843189200194249606L;


	private ParamExEnum _param;

	private String _paramLabel;

	private String _paramValue;


	public ParamEx() {
		super();
	}

	public ParamEx(ParamExEnum pParam, String pParamLabel, String pParamValue) {
		super();
		_param = pParam;
		_paramLabel = pParamLabel;
		_paramValue = pParamValue;
	}


	public ParamExEnum getParam() {
		return _param;
	}

	public void setParam(ParamExEnum pParam) {
		_param = pParam;
	}


	@SuppressWarnings("unchecked")
	public <T> T getValue() {

		T res = null;

		if(_param != null)
			switch(_param.getParamType()) {
			case STRING:
				res = (T) getParamValue();
				break;
			case INTEGER:
				res = (T) ConvertUtils.parseInteger(getParamValue());
				break;
			case AMOUNT:
				res = (T) ConvertUtils.parseBigDecimal(getParamValue());
				break;
			case DATE:
				res = (T) ConvertUtils.parseDateProperties(getParamValue());
				break;
			case BOOLEAN:
				res = (T) ConvertUtils.parseBoolean(getParamValue());
				break;
			case PERCENTAGE_RATE:
				res = (T) ConvertUtils.parseDouble(getParamValue());
				break;
			default:
				throw new UnsupportedOperationException("Unsupported paramEX type");
			}

		return res;
	}


	public String getParamLabel() {
		return _paramLabel;
	}

	public void setParamLabel(String pParamLabel) {
		_paramLabel = pParamLabel;
	}


	public String getParamValue() {
		return _paramValue;
	}

	public void setParamValue(String pParamValue) {
		_paramValue = pParamValue;
	}

}
