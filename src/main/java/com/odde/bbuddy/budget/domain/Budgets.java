package com.odde.bbuddy.budget.domain;

import com.odde.bbuddy.budget.Budget;
import com.odde.bbuddy.budget.repo.BudgetRepo;
import com.odde.bbuddy.common.utils.MyMouths;
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

    public int getSum(String startTime, String endTime) throws ParseException {
        int sum = 0;
        List<Budget> budgetList = this.budgetRepo.findAll();
        for (Budget budget : budgetList) {
            int ret = compaireMoth(budget, startTime, endTime);
            int moth = getMothByStringFormat(budget.getMonth().toString());
            int mothDay = budget.getMonth().getDayOfMonth();
            int amount = budget.getAmount();
            if (ret == 1) {
                sum = sum + (MyMouths.MothDays[moth] - mothDay + 1) / MyMouths.MothDays[moth] * amount;
            } else if (ret == 2) {
                sum = sum + mothDay / MyMouths.MothDays[moth] * amount;
            } else if (ret == 3) {
                sum = sum + amount;
            } else if (ret == 4) {
                sum = sum + (getMothDayByStringFormat(endTime) - getMothDayByStringFormat(startTime) + 1) / MyMouths.MothDays[moth] * amount;
            }
        }
        return sum;
    }


    /**
     * @param budget    用来比较的月份
     * @param startTime 起始月份
     * @param endTime   结束月份
     * @return 0 不在时间之内，1 等于起始月份，2 等于结束月份， 3 在起始月结束之间 且开始月份不等于结束月份,4 在起始月结束之间，且开始月份等于结束月份
     */
    private int compaireMoth(Budget budget, String startTime, String endTime) throws ParseException {
        int tagetmoth = budget.getMonth().getMonthValue();
        if (tagetmoth == getMothByStringFormat(startTime) && tagetmoth == getMothByStringFormat(endTime)) {
            return 4;
        } else if (tagetmoth == getMothByStringFormat(startTime)) {
            return 1;
        } else if (tagetmoth == getMothByStringFormat(endTime)) {
            return 2;
        } else if (tagetmoth > getMothByStringFormat(startTime) && tagetmoth < getMothByStringFormat(endTime)) {
            return 3;
        } else {
            return 0;
        }
    }

    private int getMothByStringFormat(String time) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        return Integer.parseInt(formatter.format(Formats.parseDayToDate(time)));
    }

    private int getMothDayByStringFormat(String time) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        return Integer.parseInt(formatter.format(Formats.parseDayToDate(time)));
    }

}
