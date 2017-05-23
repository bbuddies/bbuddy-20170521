package com.odde.bbuddy.budget.domain;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class Period {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Period(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getOverlappingDayCount(Period another) {
        LocalDate overlappingEndDate = endDate.isBefore(another.endDate) ? endDate : another.endDate;
        LocalDate overlappingStartDate = startDate.isAfter(another.startDate) ? startDate : another.startDate;

        return dayCount(overlappingStartDate, overlappingEndDate);
    }

    private long dayCount(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate))
            return 0;
        return DAYS.between(startDate, endDate) + 1;
    }
}
