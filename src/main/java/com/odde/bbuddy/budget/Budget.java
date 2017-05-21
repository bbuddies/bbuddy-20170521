package com.odde.bbuddy.budget;

import com.odde.bbuddy.common.formatter.Month;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class Budget {

    @Month
    private LocalDate month;
    private int amount;
}
