package com.rcibanque.rcidirect.services.core.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import com.rcibanque.rcidirect.services.core.utils.MathUtils;

public class PriceTest {


	@Test
	public void getFromTest() {

		Price p = null;


		// From excl. vat
		p = Price.getFromExclVat(null, 0.2);
		assertThat(p).isNull();

		p = Price.getFromExclVat(BigDecimal.valueOf(1.0), 0.2);
		assertEquals(p.getExclVat(), 1.0);
		assertEquals(p.getInclVat(), 1.2);


		// From incl. vat
		p = Price.getFromInclVat(null, 0.2);
		assertThat(p).isNull();

		p = Price.getFromInclVat(BigDecimal.valueOf(1.2), 0.2);
		assertEquals(p.getExclVat(), 1.0);
		assertEquals(p.getInclVat(), 1.2);


		// From price
		Price p2 = Price.getFromPrice(null);
		assertThat(p2).isNull();

		p2 = Price.getFromPrice(p);
		assertEquals(p2.getExclVat(), 1.0);
		assertEquals(p2.getInclVat(), 1.2);
	}


	@Test
	public void isNullTest() {

		Price p = null;
		assertThat(Price.isNull(p)).isTrue();

		p = Price.getZero();
		assertThat(Price.isNull(p)).isFalse();

		p.setExclVat(null);
		p.setInclVat(MathUtils.ZERO);
		assertThat(Price.isNull(p)).isTrue();

		p.setExclVat(MathUtils.ZERO);
		p.setInclVat(null);
		assertThat(Price.isNull(p)).isTrue();

		p.setExclVat(null);
		p.setInclVat(null);
		assertThat(Price.isNull(p)).isTrue();
	}


	@Test
	public void gtZeroTest() {

		Price p = null;
		assertThat(Price.gtZero(p)).isFalse();

		p = Price.getZero();
		assertThat(Price.gtZero(p)).isFalse();

		p.add(new Price(BigDecimal.valueOf(1.0), BigDecimal.valueOf(1.0)));
		assertThat(Price.gtZero(p)).isTrue();

		p.subtract(new Price(BigDecimal.valueOf(2.0), BigDecimal.valueOf(2.0)));
		assertThat(Price.gtZero(p)).isFalse();
	}


	@Test
	public void operationsTest() {

		Price p1 = Price.getFromExclVat(BigDecimal.valueOf(1.0), 0.2);
		Price p2 = Price.getFromExclVat(BigDecimal.valueOf(1.0), 0.2);

		Price p = Price.getFromPrice(p1);

		p.add(null);
		assertEquals(p.getExclVat(), 1.0);
		assertEquals(p.getInclVat(), 1.2);

		p.add(p2);
		assertEquals(p.getExclVat(), 2.0);
		assertEquals(p.getInclVat(), 2.4);

		p.subtract(null);
		assertEquals(p.getExclVat(), 2.0);
		assertEquals(p.getInclVat(), 2.4);

		p.subtract(p2);
		assertEquals(p.getExclVat(), 1.0);
		assertEquals(p.getInclVat(), 1.2);
	}


	private static final void assertEquals(BigDecimal pActual, double pExpected) {
		assertThat(pActual.doubleValue()).isCloseTo(pExpected, Offset.offset(0.000001));
	}

}
