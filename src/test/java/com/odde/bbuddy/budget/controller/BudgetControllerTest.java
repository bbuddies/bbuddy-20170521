package com.odde.bbuddy.budget.controller;

import com.odde.bbuddy.budget.domain.Budgets;
import com.odde.bbuddy.budget.repo.Budget;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BudgetControllerTest {
    @Test
    public void save_budget() throws Exception {
        Budget budget = new Budget();
        Budgets budgets = mock(Budgets.class);
        BudgetController controller = new BudgetController(budgets);

        controller.save(budget);

        verify(budgets).save(budget);
    }
}
