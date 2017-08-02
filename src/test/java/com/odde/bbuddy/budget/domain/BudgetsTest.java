package com.odde.bbuddy.budget.domain;

import com.odde.bbuddy.budget.repo.Budget;
import com.odde.bbuddy.budget.repo.BudgetRepo;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    public void get_all_budgets_from_repo() throws Exception {
        BudgetRepo repo = mock(BudgetRepo.class);
        Budgets budgets = new Budgets(repo);
        Budget budget = new Budget();
        budget.setMonth("2017-10");
        budget.setAmount(1000);
        List<Budget> budgetList = Arrays.asList(budget);
        when(repo.findAll()).thenReturn(budgetList);

        List<Budget> allBudgets = budgets.getAll();

        assertEquals(budgetList, allBudgets);
    }
}