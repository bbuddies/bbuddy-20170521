package com.odde.bbuddy.budget.domain;

import com.odde.bbuddy.budget.repo.Budget;
import com.odde.bbuddy.budget.repo.BudgetRepo;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BudgetsTest {
    BudgetRepo repo = mock(BudgetRepo.class);
    Budgets budgets = new Budgets(repo);

    @Test
    public void save_by_repo() throws Exception {
        Budget budget = budget("2017-10", 1000);

        budgets.save(budget);

        verify(repo).save(budget);
    }

    @Test
    public void get_all_budgets_from_repo() throws Exception {
        List<Budget> budgetList = givenBudgets(
                budget("2017-10", 1000),
                budget("2017-11", 2000));

        List<Budget> allBudgets = budgets.getAll();

        assertEquals(budgetList, allBudgets);
    }

    @Test
    public void update_by_repo() {
        String month = "2017-10";
        int oldAmount = 10000;
        Budget oldBudget = budget(month, oldAmount);
        when(repo.findByMonth(month)).thenReturn(oldBudget);
        int newAmount = 4000;
        Budget newBudget = budget(month, newAmount);

        budgets.save(newBudget);

        verify(repo).findByMonth(month);
        verify(repo).save(oldBudget);
        assertThat(oldBudget.getAmount()).isEqualTo(newAmount);
    }

    private List<Budget> givenBudgets(Budget... budget) {
        List<Budget> budgetList = Arrays.asList(budget);
        when(repo.findAll()).thenReturn(budgetList);
        return budgetList;
    }

    private Budget budget(String month, int amount) {
        Budget budget = new Budget();
        budget.setMonth(month);
        budget.setAmount(amount);
        return budget;
    }
}
