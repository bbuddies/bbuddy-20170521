package com.odde.bbuddy.budget.domain;

import com.odde.bbuddy.budget.Budget;
import com.odde.bbuddy.budget.repo.BudgetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.odde.bbuddy.common.Formats.parseDay;

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

    public float getSum(String startTime, String endTime) {
        return (float) budgetRepo.findAll().stream()
                .mapToDouble(budget -> budget.getAmountOfOverlappingDays(new Period(parseDay(startTime), parseDay(endTime))))
                .sum();
    }

}
