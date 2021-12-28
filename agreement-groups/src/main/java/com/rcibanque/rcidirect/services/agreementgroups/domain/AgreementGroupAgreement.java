package com.rcibanque.rcidirect.services.agreementgroups.domain;

import java.math.BigDecimal;

import com.rcibanque.rcidirect.services.core.domain.DTO;

public class AgreementGroupAgreement extends DTO {

	private static final long serialVersionUID = -8227107671434626453L;


	private Long _agreementId;

	private String _agreementCode;

	private String _simulationCode;

	private Integer _agreementStatusCode;

	private Integer _financeTypeCode;

	private Integer _vehicleNatureCode;

	private String _vehicleBrandLabel;

	private String _vehicleRegistration;

	private BigDecimal _rentalAmount;


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

	public Integer getAgreementStatusCode() {
		return _agreementStatusCode;
	}

	public void setAgreementStatusCode(Integer pAgreementStatusCode) {
		_agreementStatusCode = pAgreementStatusCode;
	}

	public Integer getFinanceTypeCode() {
		return _financeTypeCode;
	}

	public void setFinanceTypeCode(Integer pFinanceTypeCode) {
		_financeTypeCode = pFinanceTypeCode;
	}

	public Integer getVehicleNatureCode() {
		return _vehicleNatureCode;
	}

	public void setVehicleNatureCode(Integer pVehicleNatureCode) {
		_vehicleNatureCode = pVehicleNatureCode;
	}

	public String getVehicleBrandLabel() {
		return _vehicleBrandLabel;
	}

	public void setVehicleBrandLabel(String pVehicleBrandLabel) {
		_vehicleBrandLabel = pVehicleBrandLabel;
	}

	public String getVehicleRegistration() {
		return _vehicleRegistration;
	}

	public void setVehicleRegistration(String pVehicleRegistration) {
		_vehicleRegistration = pVehicleRegistration;
	}

	public BigDecimal getRentalAmount() {
		return _rentalAmount;
	}

	public void setRentalAmount(BigDecimal pRentalAmount) {
		_rentalAmount = pRentalAmount;
	}

}
