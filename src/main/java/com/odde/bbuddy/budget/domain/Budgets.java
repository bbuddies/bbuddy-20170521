package com.odde.bbuddy.budget.domain;

import com.odde.bbuddy.budget.Budget;
import com.odde.bbuddy.budget.repo.BudgetRepo;
import com.odde.bbuddy.common.utils.MyMonths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.List;

import com.odde.bbuddy.common.Formats;

@Component
public class Budgets {
    private final BudgetRepo budgetRepo;

    @Autowired
    public Budgets(BudgetRepo budgetRepo) {
        this.budgetRepo = budgetRepo;
    }

    public void addBudget(Budget budget) {
        for (Budget b : this.budgetRepo.findAll()) {
            if (b.getMonth().equals(budget.getMonth())) {

                budget.setId(b.getId());
            }
        }
        budgetRepo.save(budget);
    }

    public List<Budget> getAllBudgets() {
        return budgetRepo.findAll();
    }

    public float getSum(String startTime, String endTime) throws ParseException {
        float sum = 0f;
        List<Budget> budgetList = this.budgetRepo.findAll();
        for (Budget budget : budgetList) {

            int targetMonth = budget.getMonth().getMonthValue();

            float amount = budget.getAmount();
            //4 在起始月结束之间，且开始月份等于结束月份
            if (targetMonth == getMonthByStringFormat(startTime) && targetMonth == getMonthByStringFormat(endTime)) {
                int month = getMonthByStringFormat(endTime);
                sum = sum + (getMonthDayByStringFormat(endTime) - getMonthDayByStringFormat(startTime) + 1) / amount * MyMonths.MONTH_DAYS[month - 1];
            } //1 等于起始月份，
             else if (targetMonth == getMonthByStringFormat(startTime)) {
                int month = getMonthByStringFormat(startTime);
                int monthDay = getMonthDayByStringFormat(startTime);
                sum = sum + (MyMonths.MONTH_DAYS[month - 1] - monthDay + 1) / amount *  MyMonths.MONTH_DAYS[month - 1];
            } //2 等于结束月份，
             else if (targetMonth == getMonthByStringFormat(endTime)) {
                int month = getMonthByStringFormat(endTime);
                int monthDay = getMonthDayByStringFormat(endTime);
                sum = sum + monthDay / amount * MyMonths.MONTH_DAYS[month - 1];
            } //3 在起始月结束之间 且开始月份不等于结束月份
             else if (targetMonth > getMonthByStringFormat(startTime) && targetMonth < getMonthByStringFormat(endTime)) {
                sum = sum + amount;
            };
        }
        return sum;
    }

    private int getMonthByStringFormat(String time) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        return Integer.parseInt(formatter.format(Formats.parseDayToDate(time)));
    }

    private int getMonthDayByStringFormat(String time) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        return Integer.parseInt(formatter.format(Formats.parseDayToDate(time)));
    }

}
