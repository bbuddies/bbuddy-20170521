package com.odde.bbuddy.budget.domain;

import com.odde.bbuddy.budget.Budget;
import com.odde.bbuddy.budget.repo.BudgetRepo;
import org.junit.Test;

import static com.odde.bbuddy.common.Formats.parseMonth;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BudgetsTest {
    
    @Test
    public void add_budget_should_save_budget() {
        BudgetRepo mockBudgetRepo = mock(BudgetRepo.class);
        Budgets budgets = new Budgets(mockBudgetRepo);

        Budget budget = new Budget();
        budget.setMonth(parseMonth("2017-05"));
        budget.setAmount(2000);
        budgets.addBudget(budget);

        verify(mockBudgetRepo).save(budget);
    }
    
    @Test
    public void get_all_budgets_should_find_all() {
        BudgetRepo mockBudgetRepo = mock(BudgetRepo.class);
        Budgets budgets = new Budgets(mockBudgetRepo);

        Budget budget = new Budget();
        budget.setMonth(parseMonth("2017-05"));
        budget.setAmount(2000);
        when(mockBudgetRepo.findAll()).thenReturn(asList(budget));

        assertThat(budgets.getAllBudgets()).usingFieldByFieldElementComparator().isEqualTo(asList(budget));
    }

    @Test
    public void add_budget_should_replace_existing_budget() {
        BudgetRepo mockBudgetRepo = mock(BudgetRepo.class);
        Budgets budgets = new Budgets(mockBudgetRepo);

        Budget budget1 = new Budget();
        budget1.setMonth(parseMonth("2017-06"));
        budget1.setAmount(1500);
        budgets.addBudget(budget1);

        //mockBudgetRepo.save(budget1);

        Budget budget2 = new Budget();
        budget2.setMonth(parseMonth("2017-06"));
        budget2.setAmount(2000);
        budgets.addBudget(budget2);

        assertThat(budgets.getAllBudgets()).doesNotContain(budget1);

    }

}