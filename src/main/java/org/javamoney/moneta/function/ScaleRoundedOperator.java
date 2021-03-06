package org.javamoney.moneta.function;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

import org.javamoney.moneta.RoundedMoney;

/**
 * <p>This implementation uses a scale and {@link RoundingMode} to does the rounding operations. The implementation will use the <b>scale</b>, in other words, the number of digits to the right of the decimal point</p>
 * <p>The derived class will implements the {@link RoundedMoney} with this rounding monetary operator</p>
 *  <pre>
 *   {@code
 *
 *     MonetaryOperator monetaryOperator = ScaleRoundedOperator.of(scale, RoundingMode.HALF_EVEN);
 *     CurrencyUnit real = Monetary.getCurrency("BRL");
 *     MonetaryAmount money = Money.of(BigDecimal.valueOf(35.34567), real);
 *     MonetaryAmount result = monetaryOperator.apply(money); // BRL 35.3457
 *
 *    }
* </pre>
 * <p>Case the parameter in {@link MonetaryOperator#apply(MonetaryAmount)} be null, the apply will return a {@link NullPointerException}</p>
 * @author Otavio Santana
 * @see {@link ScaleRoundedOperator#of(MathContext)}
 * @see {@link RoundedMoney}
 * @see {@link MonetaryOperator}
 * @see {@link BigDecimal#scale()}
 */
final class ScaleRoundedOperator implements MonetaryOperator {

	private final int scale;

	private final RoundingMode roundingMode;

	private ScaleRoundedOperator(int scale, RoundingMode roundingMode) {
		this.scale = scale;
		this.roundingMode = roundingMode;
	}

	/**
	 * Creates the rounded Operator from scale and roundingMode
	 * @param mathContext
	 * @return the {@link MonetaryOperator} using the scale and {@link roundingMode} used in parameter
	 * @throws NullPointerException when the {@link MathContext} is null
	 * @see {@linkplain RoundingMode}
	 */
	public static ScaleRoundedOperator of(int scale, RoundingMode roundingMode) {

		Objects.requireNonNull(roundingMode);

		if(RoundingMode.UNNECESSARY.equals(roundingMode)) {
		   throw new IllegalStateException("To create the ScaleRoundedOperator you cannot use the RoundingMode.UNNECESSARY");
		}
		return new ScaleRoundedOperator(scale, roundingMode);
	}

	@Override
	public MonetaryAmount apply(MonetaryAmount amount) {
		RoundedMoney roundedMoney = RoundedMoney.from(Objects.requireNonNull(amount));
		BigDecimal numberValue = roundedMoney.getNumber().numberValue(BigDecimal.class);
		BigDecimal numberRounded = numberValue.setScale(scale, roundingMode);
		return RoundedMoney.of(numberRounded, roundedMoney.getCurrency(), this);
	}

	public int getScale() {
		return scale;
	}

	public RoundingMode getRoundingMode() {
		return roundingMode;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(ScaleRoundedOperator.class.getName()).append('{')
		.append("scale:").append(Integer.toString(scale)).append(',')
		.append("roundingMode:").append(roundingMode).append('}');
		return sb.toString();
	}

}
