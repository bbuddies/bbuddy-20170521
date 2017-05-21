package com.odde.bbuddy.budget.domain;

import com.odde.bbuddy.budget.Budget;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Budgets {
    private List<Budget> allBudgets = new ArrayList<>();

    public void addBudget(Budget budget) {
        allBudgets.add(budget);
    }

    public List<Budget> getAllBudgets() {
        return allBudgets;
    }
}
