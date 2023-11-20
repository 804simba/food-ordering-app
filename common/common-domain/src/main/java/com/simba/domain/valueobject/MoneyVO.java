package com.simba.domain.valueobject;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Getter
public class MoneyVO {
    private final BigDecimal amount;

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
        return new MoneyVO(this.amount.add(money.getAmount()));
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
