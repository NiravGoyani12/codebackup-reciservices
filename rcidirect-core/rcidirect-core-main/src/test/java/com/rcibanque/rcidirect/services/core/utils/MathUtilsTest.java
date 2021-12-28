package com.rcibanque.rcidirect.services.core.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class MathUtilsTest {


	@Test
	public void testValueOf() {

		assertThat(BigDecimal.valueOf(0.1).setScale(2)).isEqualTo(MathUtils.valueOfAmount(0.1));
		assertThat(BigDecimal.valueOf(0.01).setScale(2)).isEqualTo(MathUtils.valueOfAmount(0.01));
		assertThat(BigDecimal.valueOf(0.0).setScale(2)).isEqualTo(MathUtils.valueOfAmount(0.001));
		assertThat(BigDecimal.valueOf(0.01).setScale(2)).isEqualTo(MathUtils.valueOfAmount(0.009));

		assertThat(BigDecimal.valueOf(0.1).setScale(4)).isEqualTo(MathUtils.valueOfPercentage(0.1));
		assertThat(BigDecimal.valueOf(0.0001).setScale(4)).isEqualTo(MathUtils.valueOfPercentage(0.0001));
		assertThat(BigDecimal.valueOf(0.0).setScale(4)).isEqualTo(MathUtils.valueOfPercentage(0.00001));
		assertThat(BigDecimal.valueOf(0.0001).setScale(4)).isEqualTo(MathUtils.valueOfPercentage(0.00009));

		assertThat(BigDecimal.valueOf(0.1).setScale(6)).isEqualTo(MathUtils.valueOfRate(0.1));
		assertThat(BigDecimal.valueOf(0.000001).setScale(6)).isEqualTo(MathUtils.valueOfRate(0.000001));
		assertThat(BigDecimal.valueOf(0.0).setScale(6)).isEqualTo(MathUtils.valueOfRate(0.0000001));
		assertThat(BigDecimal.valueOf(0.000001).setScale(6)).isEqualTo(MathUtils.valueOfRate(0.0000009));
	}


	@Test
	public void testComparison() {

		assertThat(! MathUtils.gt(BigDecimal.valueOf(0.01), BigDecimal.valueOf(0.02))).isTrue();	// 0.01 >  0.02 = FALSE
		assertThat(! MathUtils.ge(BigDecimal.valueOf(0.01), BigDecimal.valueOf(0.02))).isTrue();	// 0.01 >= 0.02 = FALSE
		assertThat(! MathUtils.eq(BigDecimal.valueOf(0.01), BigDecimal.valueOf(0.02))).isTrue();	// 0.01 =  0.02 = FALSE
		assertThat(  MathUtils.le(BigDecimal.valueOf(0.01), BigDecimal.valueOf(0.02))).isTrue();	// 0.01 <= 0.02 = TRUE
		assertThat(  MathUtils.lt(BigDecimal.valueOf(0.01), BigDecimal.valueOf(0.02))).isTrue();	// 0.01 <  0.02 = TRUE

		assertThat(  MathUtils.gt(BigDecimal.valueOf(0.02), BigDecimal.valueOf(0.01))).isTrue();	// 0.02 >  0.01 = TRUE
		assertThat(  MathUtils.ge(BigDecimal.valueOf(0.02), BigDecimal.valueOf(0.01))).isTrue();	// 0.02 >= 0.01 = TRUE
		assertThat(! MathUtils.eq(BigDecimal.valueOf(0.02), BigDecimal.valueOf(0.01))).isTrue();	// 0.02 =  0.01 = FALSE
		assertThat(! MathUtils.le(BigDecimal.valueOf(0.02), BigDecimal.valueOf(0.01))).isTrue();	// 0.02 <= 0.01 = FALSE
		assertThat(! MathUtils.lt(BigDecimal.valueOf(0.02), BigDecimal.valueOf(0.01))).isTrue();	// 0.02 <  0.01 = FALSE

		assertThat(  MathUtils.eq(BigDecimal.valueOf(0.01), BigDecimal.valueOf(0.01))).isTrue();	// 0.01 =  0.01 = TRUE
		assertThat(  MathUtils.eq(MathUtils.valueOfPercentage(0.0000), MathUtils.valueOfRate(0.00))).isTrue();	// 0.0000 =  0.000000 = TRUE
	}


	@Test
	public void testMinMax() {

		assertThat(BigDecimal.valueOf(0.01)).isEqualTo(MathUtils.min(BigDecimal.valueOf(0.01), BigDecimal.valueOf(0.02), BigDecimal.valueOf(0.03)));
		assertThat(BigDecimal.valueOf(0.03)).isEqualTo(MathUtils.max(BigDecimal.valueOf(0.01), BigDecimal.valueOf(0.02), BigDecimal.valueOf(0.03)));

		assertThat(BigDecimal.valueOf(1.0)).isEqualTo(MathUtils.min(BigDecimal.valueOf(1.0), BigDecimal.valueOf(2.0), BigDecimal.valueOf(3.0)));
		assertThat(BigDecimal.valueOf(3.0)).isEqualTo(MathUtils.max(BigDecimal.valueOf(1.0), BigDecimal.valueOf(2.0), BigDecimal.valueOf(3.0)));
	}


	@Test
	public void testBigDecimalOperations() {

		// +
		assertThat(BigDecimal.valueOf(0.0002)).isEqualTo(MathUtils.add(BigDecimal.valueOf(0.0001), 	BigDecimal.valueOf(0.0001)));

		// -
		assertThat(BigDecimal.valueOf(0.0).setScale(5)).isEqualTo(MathUtils.sub(BigDecimal.valueOf(0.0001), 	BigDecimal.valueOf(0.0001)));

		// *
		assertThat(BigDecimal.valueOf(2.0).setScale(2)).isEqualTo(MathUtils.mult(BigDecimal.valueOf(1), 		BigDecimal.valueOf(2)));
		assertThat(BigDecimal.valueOf(0.0).setScale(2)).isEqualTo(MathUtils.mult(BigDecimal.valueOf(0.01), 	BigDecimal.valueOf(0.01)));
		assertThat(BigDecimal.valueOf(0.0001).setScale(4)).isEqualTo(MathUtils.mult(BigDecimal.valueOf(0.01), 	BigDecimal.valueOf(0.01), 4));
		assertThat(BigDecimal.valueOf(0.0).setScale(4)).isEqualTo(MathUtils.mult(BigDecimal.valueOf(0.001), 	BigDecimal.valueOf(0.001), 4));

		// /
		assertThat(BigDecimal.valueOf(2.0).setScale(2)).isEqualTo(MathUtils.div(BigDecimal.valueOf(2), 		BigDecimal.valueOf(1)));
		assertThat(BigDecimal.valueOf(0.0).setScale(2)).isEqualTo(MathUtils.div(BigDecimal.valueOf(0.01), 	BigDecimal.valueOf(10)));
		assertThat(BigDecimal.valueOf(0.0001).setScale(4)).isEqualTo(MathUtils.div(BigDecimal.valueOf(0.001), 	BigDecimal.valueOf(10), 4));
		assertThat(BigDecimal.valueOf(0.0).setScale(4)).isEqualTo(MathUtils.div(BigDecimal.valueOf(0.0001), 	BigDecimal.valueOf(10), 4));
	}


	@Test
	public void testOtherOperations() {

		// *
		assertThat(BigDecimal.valueOf(2.0).setScale(2)).isEqualTo(MathUtils.mult(BigDecimal.valueOf(1.0), 	Double.valueOf(2.0)));
		assertThat(BigDecimal.valueOf(0.01).setScale(2)).isEqualTo(MathUtils.mult(BigDecimal.valueOf(0.1), 	Double.valueOf(0.1)));
		assertThat(BigDecimal.valueOf(0.0).setScale(2)).isEqualTo(MathUtils.mult(BigDecimal.valueOf(0.001), 	Double.valueOf(0.001)));

		assertThat(BigDecimal.valueOf(2.0).setScale(2)).isEqualTo(MathUtils.mult(BigDecimal.valueOf(1.0), 	Integer.valueOf(2)));
		assertThat(BigDecimal.valueOf(0.02).setScale(2)).isEqualTo(MathUtils.mult(BigDecimal.valueOf(0.01), 	Integer.valueOf(2)));

		// /
		assertThat(BigDecimal.valueOf(1.0).setScale(2)).isEqualTo(MathUtils.div(BigDecimal.valueOf(0.0001), 	Double.valueOf(0.0001)));

		assertThat(BigDecimal.valueOf(0.5).setScale(2)).isEqualTo(MathUtils.div(BigDecimal.valueOf(1.0), 		Integer.valueOf(2)));
	}


	@Test
	public void testRound() {

		assertThat(MathUtils.round2(null)).isNull();

		assertThat(BigDecimal.valueOf(1.23)).isEqualTo(MathUtils.round2(BigDecimal.valueOf(1.23)));
		assertThat(BigDecimal.valueOf(1.23)).isEqualTo(MathUtils.round2(BigDecimal.valueOf(1.234)));
		assertThat(BigDecimal.valueOf(1.24)).isEqualTo(MathUtils.round2(BigDecimal.valueOf(1.235)));
		assertThat(BigDecimal.valueOf(1.24)).isEqualTo(MathUtils.round2(BigDecimal.valueOf(1.239)));
		assertThat(BigDecimal.valueOf(1.24)).isEqualTo(MathUtils.round2(BigDecimal.valueOf(1.24)));

		assertThat(BigDecimal.valueOf(1.2)).isEqualTo(MathUtils.round(BigDecimal.valueOf(1.24), 1));
		assertThat(BigDecimal.valueOf(1.3)).isEqualTo(MathUtils.round(BigDecimal.valueOf(1.25), 1));
	}

}
