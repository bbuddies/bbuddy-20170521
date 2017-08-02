package com.odde.bbuddy.budget.domain;

import com.odde.bbuddy.budget.repo.Budget;
import com.odde.bbuddy.budget.repo.BudgetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Budgets {
    private final BudgetRepo repo;

    @Autowired
    public Budgets(BudgetRepo repo) {
        this.repo = repo;
    }

    public void save(Budget budget) {
        repo.save(budget);
    }

    public List<Budget> getAll() {
        return repo.findAll();
    }
}
