package com.rcibanque.rcidirect.services.core.domain;

import java.math.BigDecimal;

import com.rcibanque.rcidirect.services.core.utils.MathUtils;
import com.rcibanque.rcidirect.services.core.utils.VATUtils;

public class Price extends DTO {

	private static final long serialVersionUID = -3986616090347452845L;


	/**
	 * @param pInclVat Amount including VAT
	 * @param pVatRate VAT rate
	 * @return Price with both amounts including and excluding VAT (<b>rounded to 2 decimals</b>)
	 */
	public static final Price getFromInclVat(BigDecimal pInclVat, Double pVatRate) {

		BigDecimal inclVatRounded = MathUtils.round2(pInclVat);

		return pInclVat == null ? null : new Price(MathUtils.round2(VATUtils.getExclVat(inclVatRounded, pVatRate)), inclVatRounded);
	}

	/**
	 * @param pExclVat Amount excluding VAT
	 * @param pVatRate VAT rate
	 * @return Price with both amounts including and excluding VAT (<b>rounded to 2 decimals</b>)
	 */
	public static final Price getFromExclVat(BigDecimal pExclVat, Double pVatRate) {

		BigDecimal exclVatRounded = MathUtils.round2(pExclVat);

		return pExclVat == null ? null : new Price(exclVatRounded, MathUtils.round2(VATUtils.getInclVat(exclVatRounded, pVatRate)));
	}

	/**
	 * @param pPrice
	 * @return excluding VAT amount or null
	 */
	public static final BigDecimal getExclVatOrNull(Price pPrice) {

		return pPrice != null ? pPrice.getExclVat() : null;
	}

	/**
	 * @param pPrice
	 * @return including VAT amount or null
	 */
	public static final BigDecimal getInclVatOrNull(Price pPrice) {

		return pPrice != null ? pPrice.getInclVat() : null;
	}

	/**
	 * @param pPrice Price
	 * @return Price whose values are copied from input, or null
	 */
	public static final Price getFromPrice(Price pPrice) {
		Price res = null;

		if(! isNull(pPrice)) {

			res = new Price(pPrice.getExclVat(), pPrice.getInclVat());
		}

		return res;
	}

	/**
	 * @return Price with 0.00 including and excluding VAT
	 */
	public static final Price getZero() {

		return new Price(MathUtils.ZERO, MathUtils.ZERO);
	}

	/**
	 * @param pPrice Price
	 * @return True if price is null, or if either including or excluding VAT are null
	 */
	public static final boolean isNull(Price pPrice) {

		return pPrice == null
				|| pPrice.getExclVat() == null
				|| pPrice.getInclVat() == null;
	}

	/**
	 * @param pPrice Price
	 * @return True if price is not null and if both including and excluding VAT are greater than 0.00
	 */
	public static final boolean gtZero(Price pPrice) {

		return ! isNull(pPrice)
				&& MathUtils.gt(pPrice.getExclVat(), MathUtils.ZERO)
				&& MathUtils.gt(pPrice.getInclVat(), MathUtils.ZERO);
	}


	private BigDecimal _exclVat;

	private BigDecimal _inclVat;


	public Price() {
		super();
	}

	/**
	 * Create a price with including and excluding VAT (<b>rounded to 2 decimals</b>)
	 *
	 * @see #getFromExclVat(BigDecimal, Double)
	 * @see #getFromInclVat(BigDecimal, Double)
	 * @see #getFromPrice(Price)
	 * @see #getZero()
	 */
	public Price(BigDecimal pExclVat, BigDecimal pInclVat) {
		this();
		_exclVat = MathUtils.round2(pExclVat);
		_inclVat = MathUtils.round2(pInclVat);
	}


	public BigDecimal getExclVat() {
		return _exclVat;
	}

	public void setExclVat(BigDecimal pExclVat) {
		_exclVat = pExclVat;
	}

	public BigDecimal getInclVat() {
		return _inclVat;
	}

	public void setInclVat(BigDecimal pInclVat) {
		_inclVat = pInclVat;
	}


	public void add(Price pPrice) {
		if(! isNull(pPrice) && ! isNull(this)) {
			_exclVat = MathUtils.add(_exclVat, pPrice.getExclVat());
			_inclVat = MathUtils.add(_inclVat, pPrice.getInclVat());
		}
	}

	public void subtract(Price pPrice) {
		if(! isNull(pPrice) && ! isNull(this)) {
			_exclVat = MathUtils.sub(_exclVat, pPrice.getExclVat());
			_inclVat = MathUtils.sub(_inclVat, pPrice.getInclVat());
		}
	}

}
