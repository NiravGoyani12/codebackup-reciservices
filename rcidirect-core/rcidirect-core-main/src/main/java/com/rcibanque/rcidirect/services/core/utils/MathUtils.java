package com.rcibanque.rcidirect.services.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {


	private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

	public static final int SCALE_AMOUNT = 2;

	public static final int SCALE_PERCENTAGE = 4;

	public static final int SCALE_RATE = 6;

	public static final BigDecimal ZERO = valueOfAmount(0.0);


	private MathUtils() {}


	/**
	 * @param pVal1 Value 1
	 * @param pVal2 Value 2
	 *
	 * @return true if value 1 <= value 2
	 */
	public static final boolean le(BigDecimal pVal1, BigDecimal pVal2) {
		return pVal1.compareTo(pVal2) <= 0;
	}

	/**
	 * @param pVal1 Value 1
	 * @param pVal2 Value 2
	 *
	 * @return true if value 1 < value 2
	 */
	public static final boolean lt(BigDecimal pVal1, BigDecimal pVal2) {
		return pVal1.compareTo(pVal2) < 0;
	}

	/**
	 * @param pVal1 Value 1
	 * @param pVal2 Value 2
	 *
	 * @return true if value 1 = value 2
	 */
	public static final boolean eq(BigDecimal pVal1, BigDecimal pVal2) {
		return pVal1.compareTo(pVal2) == 0;
	}

	/**
	 * @param pVal1 Value 1
	 * @param pVal2 Value 2
	 *
	 * @return true if value 1 >= value 2
	 */
	public static final boolean ge(BigDecimal pVal1, BigDecimal pVal2) {
		return pVal1.compareTo(pVal2) >= 0;
	}

	/**
	 * @param pVal1 Value 1
	 * @param pVal2 Value 2
	 *
	 * @return true if value 1 > value 2
	 */
	public static final boolean gt(BigDecimal pVal1, BigDecimal pVal2) {
		return pVal1.compareTo(pVal2) > 0;
	}


	/**
	 * @param pValues Values
	 * @return Minimum value
	 */
	public static final BigDecimal min(BigDecimal ...pValues) {
		BigDecimal res = null;
		for(BigDecimal value : IteratorUtils.nullableIterable(pValues)) {
			if(res == null || lt(value, res)) {
				res = value;
			}
		}
		return res;
	}


	/**
	 * @param pValues Values
	 * @return Maximum value
	 */
	public static final BigDecimal max(BigDecimal ...pValues) {
		BigDecimal res = null;
		for(BigDecimal value : IteratorUtils.nullableIterable(pValues)) {
			if(res == null || gt(value, res)) {
				res = value;
			}
		}
		return res;
	}


	/**
	 * @param pVal1 Value 1
	 * @param pVal2 Value 2
	 * @return Big decimal result of the addition v1 + v2
	 */
	public static final BigDecimal add(BigDecimal pVal1, BigDecimal pVal2) {
		return pVal1.add(pVal2);
	}


	/**
	 * @param pVal1 Value 1
	 * @param pVal2 Value 2
	 * @return Big decimal result of the subtraction v1 - v2
	 */
	public static final BigDecimal sub(BigDecimal pVal1, BigDecimal pVal2) {
		return pVal1.subtract(pVal2);
	}


	/**
	 * @see #div(BigDecimal, BigDecimal)
	 */
	public static final BigDecimal div(BigDecimal pVal1, Integer pVal2) {
		return div(pVal1, BigDecimal.valueOf(pVal2));
	}

	/**
	 * @see #div(BigDecimal, BigDecimal)
	 */
	public static final BigDecimal div(BigDecimal pVal1, Double pVal2) {
		return div(pVal1, BigDecimal.valueOf(pVal2));
	}

	/**
	 * @see #div(BigDecimal, BigDecimal, SCALE_AMOUNT)
	 */
	public static final BigDecimal div(BigDecimal pVal1, BigDecimal pVal2) {
		return div(pVal1, pVal2, SCALE_AMOUNT);
	}

	/**
	 * @param pVal1 Value 1
	 * @param pVal2 Value 2
	 * @param pScale Result scale
	 * @return Big decimal result of the division v1 / v2
	 */
	public static final BigDecimal div(BigDecimal pVal1, BigDecimal pVal2, int pScale) {
		return pVal1.divide(pVal2, pScale, ROUNDING_MODE);
	}


	/**
	 * @see #mult(BigDecimal, BigDecimal)
	 */
	public static final BigDecimal mult(BigDecimal pVal1, Integer pVal2) {
		return mult(pVal1, BigDecimal.valueOf(pVal2.longValue()));
	}

	/**
	 * @see #mult(BigDecimal, BigDecimal)
	 */
	public static final BigDecimal mult(BigDecimal pVal1, Double pVal2) {
		return mult(pVal1, BigDecimal.valueOf(pVal2));
	}

	/**
	 * @see #mult(BigDecimal, BigDecimal, SCALE_AMOUNT)
	 */
	public static final BigDecimal mult(BigDecimal pVal1, BigDecimal pVal2) {
		return mult(pVal1, pVal2, SCALE_AMOUNT);
	}

	/**
	 * @param pVal1 Value 1
	 * @param pVal2 Value 2
	 * @param pScale Result scale
	 * @return Big decimal result of the multiplication v1 * v2
	 */
	public static final BigDecimal mult(BigDecimal pVal1, BigDecimal pVal2, int pScale) {
		BigDecimal res = pVal1.multiply(pVal2);
		res = res.setScale(pScale, ROUNDING_MODE);
		return res;
	}


	/**
	 * @param pValue Double value
	 * @return BigDecimal value <b>with scale set to {@link #SCALE_AMOUNT}</b>
	 */
	public static final BigDecimal valueOfAmount(Double pValue) {
		return valueOf(pValue, SCALE_AMOUNT);
	}

	/**
	 * @param pValue Double value
	 * @return BigDecimal value <b>with scale set to {@link #SCALE_PERCENTAGE}</b>
	 */
	public static final BigDecimal valueOfPercentage(Double pValue) {
		return valueOf(pValue, SCALE_PERCENTAGE);
	}

	/**
	 * @param pValue Double value
	 * @return BigDecimal value <b>with scale set to {@link #SCALE_RATE}</b>
	 */
	public static final BigDecimal valueOfRate(Double pValue) {
		return valueOf(pValue, SCALE_RATE);
	}

	/**
	 * @param pValue Double value
	 * @param pScale Result scale
	 * @return BigDecimal value
	 */
	private static final BigDecimal valueOf(Double pValue, int pScale) {
		BigDecimal res = null;
		if(pValue != null && ! pValue.equals(Double.NaN)) {
			res = BigDecimal.valueOf(pValue);
			res = res.setScale(pScale, ROUNDING_MODE);
		}
		return res;
	}


	/**
	 * @param pValue Value
	 * @param pScale Result scale
	 * @return BigDecimal rounded value
	 */
	public static final BigDecimal round(BigDecimal pValue, int pScale) {

		return pValue != null ? pValue.setScale(pScale, ROUNDING_MODE) : null;
	}

	/**
	 * @param pValue Value
	 * @return BigDecimal rounded value (2 decimals)
	 */
	public static final BigDecimal round2(BigDecimal pValue) {

		return round(pValue, 2);
	}


}
