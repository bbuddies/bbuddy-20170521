package com.odde.bbuddy.budget.repo;

import com.odde.bbuddy.budget.domain.Period;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;

import static com.odde.bbuddy.common.Formats.parseDate;
import static java.math.BigDecimal.valueOf;

@Entity
@Table(name = "budgets")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Budget {

    @Id
    @GeneratedValue
    private long id;

    @NotBlank
    private String month;

    @NotNull
    private Integer amount;

    public Budget(String month,
                  Integer amount) {
        this.month = month;
        this.amount = amount;
    }

    private Calendar monthCalendar() throws ParseException {
        Calendar month = Calendar.getInstance();
        month.setTime(parseDate(this.month + "-01"));
        return month;
    }

    private int dayCount() throws ParseException {
        return monthCalendar().getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private BigDecimal dailyAmount() throws ParseException {
        return valueOf(amount * 1d / dayCount());
    }

    public BigDecimal overlappingAmount(Period period) {
        try {
            return valueOf(period.overlappingDayCount(monthCalendar())).multiply(dailyAmount());
        } catch (ParseException e) {
            throw new IllegalStateException();
        }
    }
}
