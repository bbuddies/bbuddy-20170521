package com.odde.bbuddy.budget;

import com.odde.bbuddy.budget.domain.Period;
import com.odde.bbuddy.common.formatter.Month;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

@Setter
@Getter
@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue
    private long id;

    @Month
    private LocalDate month;
    private int amount;

    private float getDailyBudget() {
        return (float) getAmount() / getMonth().lengthOfMonth();
    }

    private Period getPeriod() {
        return new Period(month.with(firstDayOfMonth()), month.with(lastDayOfMonth()));
    }

    public float getAmountOfOverlappingDays(Period period) {
        return getDailyBudget() * period.getOverlappingDayCount(getPeriod());
    }
}
