package org.javamoney.moneta.function;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

import org.javamoney.moneta.Money;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ExtractorMinorPartOperatorTest {

	private MonetaryOperator operator;

	@BeforeMethod
	public void setup() {
		operator = new ExtractorMinorPartOperator();
	}

	@Test
	public void shouldReturnPositiveValue() {
		CurrencyUnit currency = Monetary.getCurrency("EUR");
		MonetaryAmount money = Money.parse("EUR 2.35");
		MonetaryAmount result = operator.apply(money);
		assertEquals(result.getCurrency(), currency);
		assertEquals(result.getNumber().doubleValue(), 0.35);

	}


	@Test
	public void shouldReturnNegativeValue() {
		CurrencyUnit currency = Monetary.getCurrency("BHD");
		MonetaryAmount money = Money.parse("BHD -1.345");
		MonetaryAmount result = operator.apply(money);
		assertEquals(result.getCurrency(), currency);
		assertEquals(result.getNumber().doubleValue(), -0.345);

	}

	@Test(expectedExceptions = NullPointerException.class)
	public void shouldReturnErroWhenIsNull() {
		operator.apply(null);
		fail();
	}
}
