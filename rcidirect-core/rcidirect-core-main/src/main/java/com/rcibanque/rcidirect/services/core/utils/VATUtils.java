package com.rcibanque.rcidirect.services.core.utils;

import java.math.BigDecimal;

import com.rcibanque.rcidirect.services.core.domain.Price;

public class VATUtils {

	private VATUtils() {}

	public static final String CODE_VAT_ZERO_RATE 		= "000";
	public static final String CODE_VAT_STANDARD_RATE 	= "001";
	public static final String CODE_VAT_EXEMPT_RATE 	= "004";
	public static final String CODE_VAT_OUT_SCOPE_RATE 	= "005";


	/**
	 * @param pInclVat Amount including Vat
	 * @param pVatRate Vat rate
	 * @return Amount excluding Vat
	 */
	public static final BigDecimal getExclVat(BigDecimal pInclVat, Double pVatRate) {
		BigDecimal res = null;
		if(pInclVat != null && pVatRate != null) {
			res = MathUtils.div(pInclVat, 1d + pVatRate);
		}
		return res;
	}

	/**
	 * @param pExclVat Amount excluding Vat
	 * @param pVatRate Vat rate
	 * @return Amount including Vat
	 */
	public static final BigDecimal getInclVat(BigDecimal pExclVat, Double pVatRate) {
		BigDecimal res = null;
		if(pExclVat != null && pVatRate != null) {
			res = MathUtils.mult(pExclVat, 1d + pVatRate);
		}
		return res;
	}

	/**
	 * @param pInclVat Amount including Vat
	 * @param pExclVat Amount excluding Vat
	 * @return Amount Vat
	 */
	public static final BigDecimal getVat(BigDecimal pInclVat, BigDecimal pExclVat) {
		BigDecimal res = null;
		if(pInclVat != null && pExclVat != null) {
			res = MathUtils.sub(pInclVat, pExclVat);
		}
		return res;
	}

	/**
	 * @param pPrice Price
	 * @return Amount Vat
	 */
	public static final BigDecimal getVat(Price pPrice) {
		BigDecimal res = null;
		if(pPrice != null) {
			res = getVat(pPrice.getInclVat(), pPrice.getExclVat());
		}
		return res;
	}


	/**
	 * @param pTotalPrice Total price
	 * @return Average VAT rate
	 */
	public static Double getAverageVatRate(Price pPrice) {

		double vatRate = 0d;

		if(! Price.isNull(pPrice)) {

			double exclVat = pPrice.getExclVat().doubleValue();
			double inclVat = pPrice.getInclVat().doubleValue();

			if(exclVat > 0d && inclVat > 0d && inclVat > exclVat) {
				vatRate = (inclVat / exclVat) - 1;
			}
		}

		return vatRate;
	}

}
