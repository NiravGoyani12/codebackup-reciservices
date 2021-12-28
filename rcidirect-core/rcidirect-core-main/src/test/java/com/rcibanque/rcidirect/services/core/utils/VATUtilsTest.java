package com.rcibanque.rcidirect.services.core.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import com.rcibanque.rcidirect.services.core.domain.Price;

public class VATUtilsTest {

	@Test
	public void testInclExcl() {

		// Incl -> Excl
		assertThat(MathUtils.valueOfAmount(0.0)).isEqualTo(VATUtils.getExclVat(BigDecimal.valueOf(0.0), 0.0));
		assertThat(MathUtils.valueOfAmount(8.33)).isEqualTo(VATUtils.getExclVat(BigDecimal.valueOf(10.0), 0.2));

		// Excl -> Incl
		assertThat(MathUtils.valueOfAmount(0.0)).isEqualTo(VATUtils.getInclVat(BigDecimal.valueOf(0.0), 0.0));
		assertThat(MathUtils.valueOfAmount(12.0)).isEqualTo(VATUtils.getInclVat(BigDecimal.valueOf(10.0), 0.2));

	}

	@Test
	public void testVAT() {

		assertThat(VATUtils.getVat(null)).isNull();;

		assertThat(MathUtils.valueOfAmount(0.0)).isEqualTo(VATUtils.getVat(Price.getFromExclVat(BigDecimal.valueOf(0.0), 0.0)));
		assertThat(MathUtils.valueOfAmount(2.0)).isEqualTo(VATUtils.getVat(Price.getFromExclVat(BigDecimal.valueOf(10.0), 0.2)));
	}


	@Test
	public void testAverageVATRateWithNoPrice() {

		// Arrange
		Price price = null;

		// Act
		Double vatRate = VATUtils.getAverageVatRate(price);

		// Assert
		assertThat(vatRate).isEqualTo(0.00);
	}


	@Test
	public void testAverageVATRateWithZeroPrice() {

		// Arrange
		Price price = Price.getZero();

		// Act
		Double vatRate = VATUtils.getAverageVatRate(price);

		// Assert
		assertThat(vatRate).isEqualTo(0.00);
	}


	@Test
	public void testAverageVATRateWithPriceList() {

		// Arrange
		Price price = new Price(BigDecimal.valueOf(300.00), BigDecimal.valueOf(320.00)); // 100.00 with 20.00 % VAT + 200.00 with 0.00 % VAT

		// Act
		Double vatRate = VATUtils.getAverageVatRate(price);

		// Assert
		assertThat(vatRate).isEqualTo(0.0666, Offset.offset(0.0001)); // VAT = 6.66 %
	}

}
