package com.odde.bbuddy.budget.domain;

import com.odde.bbuddy.budget.Budget;
import com.odde.bbuddy.budget.repo.BudgetRepo;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static com.odde.bbuddy.common.Formats.parseMonth;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BudgetsTest {
    BudgetRepo mockBudgetRepo = mock(BudgetRepo.class);
    Budgets budgets = new Budgets(mockBudgetRepo);
    Budget budget = budget();

    
    @Test
    public void add_budget_should_save_budget() {
        budgets.addBudget(budget);

        verify(mockBudgetRepo).save(budget);
    }

    @Test
    public void get_all_budgets_should_find_all() {
        givenExistsBudget(budget);

        assertThat(budgets.getAllBudgets()).usingFieldByFieldElementComparator().isEqualTo(asList(budget));
    }

    @Test
    public void add_budget_should_replace_existing_budget() {
        givenExistsBudget(budget(1, "2017-06", 1500));

        budgets.addBudget(budget("2017-06", 2000));

        verifySaveBudgetCalledWithId(1);
    }

    private void verifySaveBudgetCalledWithId(int expected) {
        ArgumentCaptor<Budget> captor = ArgumentCaptor.forClass(Budget.class);
        verify(mockBudgetRepo).save(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(expected);
    }

    private Budget budget(String month, int amount) {
        Budget budget2 = new Budget();
        budget2.setMonth(parseMonth(month));
        budget2.setAmount(amount);
        return budget2;
    }

    private void givenExistsBudget(Budget budget) {
        when(mockBudgetRepo.findAll()).thenReturn(asList(budget));
    }

    private Budget budget(int id, String month, int amount) {
        Budget budget1 = new Budget();
        budget1.setId(id);
        budget1.setMonth(parseMonth(month));
        budget1.setAmount(amount);
        return budget1;
    }

    private Budget budget() {
        Budget budget = new Budget();
        budget.setMonth(parseMonth("2017-05"));
        budget.setAmount(2000);
        return budget;
    }

    @Test
    public void sum_budget_should_calculate_correct (){

    }
}