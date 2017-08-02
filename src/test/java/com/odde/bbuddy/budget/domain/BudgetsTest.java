package com.odde.bbuddy.budget.domain;

import com.odde.bbuddy.budget.repo.Budget;
import com.odde.bbuddy.budget.repo.BudgetRepo;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BudgetsTest {
    @Test
    public void save_by_repo() throws Exception {
        BudgetRepo repo = mock(BudgetRepo.class);
        Budgets budgets = new Budgets(repo);
        Budget budget = new Budget();
        budget.setMonth("2017-10");
        budget.setAmount(1000);

        budgets.save(budget);

        verify(repo).save(budget);
    }
}