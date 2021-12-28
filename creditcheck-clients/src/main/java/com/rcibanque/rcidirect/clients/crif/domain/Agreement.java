package com.rcibanque.rcidirect.clients.crif.domain;

import java.util.Date;

import com.rcibanque.rcidirect.services.core.domain.DTO;


public class Agreement extends DTO {

	private static final long serialVersionUID = -5794385556322942929L;

	private Long _agreementId;

	private String _agreementCode;

	private String _simulationCode;

	private Integer _statusCode;

	private Date _proposalCreationDate;

	private FinancialDetail _financialDetail;

	public Date getProposalCreationDate() {
		return _proposalCreationDate;
	}

	public void setProposalCreationDate(Date pProposalCreationDate) {
		_proposalCreationDate = pProposalCreationDate;
	}

	public Long getAgreementId() {
		return _agreementId;
	}

	public void setAgreementId(Long pAgreementId) {
		_agreementId = pAgreementId;
	}

	public String getAgreementCode() {
		return _agreementCode;
	}

	public void setAgreementCode(String pAgreementCode) {
		_agreementCode = pAgreementCode;
	}

	public String getSimulationCode() {
		return _simulationCode;
	}

	public void setSimulationCode(String pSimulationCode) {
		_simulationCode = pSimulationCode;
	}

	public Integer getStatusCode() {
		return _statusCode;
	}

	public void setStatusCode(Integer pStatusCode) {
		_statusCode = pStatusCode;
	}

	public FinancialDetail getFinancialDetail() {
		return _financialDetail;
	}

	public void setFinancialDetail(FinancialDetail pFinancialDetail) {
		_financialDetail = pFinancialDetail;
	}

}
