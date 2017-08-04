package com.odde.bbuddy.budget.domain;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static java.util.stream.IntStream.range;

public class Period {
    private final Date start;
    private final Date end;

    public Period(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    private boolean contains(Date date) {
        return start.equals(date) || end.equals(date) || (start.before(date) && date.before(end));
    }

    public long overlappingDayCount(Calendar monthOfBudget) throws ParseException {
        return range(0, monthOfBudget.getActualMaximum(Calendar.DAY_OF_MONTH))
                .mapToObj(i -> toDate(monthOfBudget))
                .filter(this::contains)
                .count();
    }

    private Date toDate(Calendar monthOfBudget) {
        Date date = monthOfBudget.getTime();
        monthOfBudget.add(Calendar.DATE, 1);
        return date;
    }
}
