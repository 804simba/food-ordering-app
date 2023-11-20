package com.simba.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class MoneyVO {
    private final BigDecimal amount;

    public static final MoneyVO ZERO = new MoneyVO(BigDecimal.ZERO);

    public MoneyVO(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isGreaterThanZero() {
        return this.amount != null && this.amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isGreaterThan(MoneyVO money) {
        return this.amount != null && this.amount.compareTo(money.getAmount()) > 0;
    }

    public MoneyVO add(MoneyVO money) {
        return new MoneyVO(setScale(this.amount.add(money.getAmount())));
    }

    public MoneyVO subtract(MoneyVO money) {
        return new MoneyVO(setScale(this.amount.subtract(money.getAmount())));
    }

    public MoneyVO multiply(int multiplier) {
        return new MoneyVO(setScale(this.amount.multiply(new BigDecimal(multiplier))));
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoneyVO moneyVO = (MoneyVO) o;
        return Objects.equals(amount, moneyVO.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    private BigDecimal setScale(BigDecimal input) {
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }
}
