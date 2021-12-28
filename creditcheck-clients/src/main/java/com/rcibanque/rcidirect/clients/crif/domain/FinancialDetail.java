package com.rcibanque.rcidirect.clients.crif.domain;

import com.rcibanque.rcidirect.services.core.domain.DTO;
import com.rcibanque.rcidirect.services.core.domain.Price;

public class FinancialDetail extends DTO {

	private static final long serialVersionUID = -6230655498816861663L;

	private Integer _financeTypeCode;

	private String _currency;

	private Price _financedAmount;

	public Integer getFinanceTypeCode() {
		return _financeTypeCode;
	}

	public void setFinanceTypeCode(Integer pFinanceTypeCode) {
		_financeTypeCode = pFinanceTypeCode;
	}

	public Price getFinancedAmount() {
		return _financedAmount;
	}

	public void setFinancedAmount(Price pFinancedAmount) {
		_financedAmount = pFinancedAmount;
	}

	public String getCurrency() {
		return _currency;
	}

	public void setCurrency(String pCurrency) {
		_currency = pCurrency;
	}


}
