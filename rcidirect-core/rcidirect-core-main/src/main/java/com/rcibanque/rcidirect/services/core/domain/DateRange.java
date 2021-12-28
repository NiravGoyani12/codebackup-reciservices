package com.rcibanque.rcidirect.services.core.domain;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.rcibanque.rcidirect.services.core.utils.ConvertUtils;
import com.rcibanque.rcidirect.services.core.utils.CoreMessages;

public final class DateRange extends DTO {

	private static final long serialVersionUID = 1L;


	private final Date _from;

	private final Date _to;


	public DateRange(Date pFrom, Date pTo) {
		_from = pFrom;
		_to = pTo;
	}


	public Date getFrom() {
		return _from;
	}

	public Date getTo() {
		return _to;
	}


	public String toString(IContext pContext) {
		String res = StringUtils.EMPTY;

		if(_from != null) {
			res += CoreMessages.getInstance().getMessage(pContext.getLocale(), "domain.date.range.from", ConvertUtils.toString(_from));
		}

		if(_to != null) {
			if(res.length() > 0) {
				res += StringUtils.SPACE;
			}
			res += CoreMessages.getInstance().getMessage(pContext.getLocale(), "domain.date.range.to", ConvertUtils.toString(_to));
		}

		return res;
	}

}
