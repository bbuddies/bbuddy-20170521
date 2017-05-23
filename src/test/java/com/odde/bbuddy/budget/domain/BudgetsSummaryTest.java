package com.odde.bbuddy.budget.domain;

import com.odde.bbuddy.budget.Budget;
import com.odde.bbuddy.budget.repo.BudgetRepo;
import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;

import java.text.ParseException;
import java.util.List;

import static com.odde.bbuddy.common.Formats.parseMonth;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BudgetsSummaryTest {

    BudgetRepo stubBudgetRepo = mock(BudgetRepo.class);
    Budgets budgets = new Budgets(stubBudgetRepo);

    @Test
    public void no_overlap() throws ParseException {
        givenExistingBudgets(budget("2017-05", 31));

        assertThat(budgets.getSum("2017-04-10", "2017-04-20")).isEqualTo(0);
    }

    @Test
    public void in_one_month() throws ParseException {
        givenExistingBudgets(budget("2017-05", 31));

        assertThat(budgets.getSum("2017-05-10", "2017-05-20")).isEqualTo(11);
    }
    
    @Test
    public void in_two_months() throws ParseException {
        givenExistingBudgets(budget("2017-05", 31), budget("2017-06", 30));

        assertThat(budgets.getSum("2017-05-10", "2017-06-15")).isEqualTo(37);
    }
    
    @Test
    public void in_three_months() throws ParseException {
        givenExistingBudgets(budget("2017-05", 31), budget("2017-06", 30), budget("2017-07", 31));

        assertThat(budgets.getSum("2017-05-10", "2017-07-15")).isEqualTo(67);
    }
    
    @Test
    public void any_months() throws ParseException {
        givenExistingBudgets(budget("2017-05", 31), budget("2017-07", 31), budget("2017-08", 31));

        assertThat(budgets.getSum("2017-05-10", "2017-07-15")).isEqualTo(37);
    }
    
    @Test
    public void acceptance_test() throws ParseException {
        givenExistingBudgets(budget("2017-05", 310), budget("2017-07", 310), budget("2017-08", 310));

        assertThat(budgets.getSum("2017-05-10", "2017-07-15")).isEqualTo(370);
    }

    private Budget budget(String month, int amount) {
        Budget budget = new Budget();
        budget.setMonth(parseMonth(month));
        budget.setAmount(amount);
        return budget;
    }

    private OngoingStubbing<List<Budget>> givenExistingBudgets(Budget... budgets) {
        return when(stubBudgetRepo.findAll()).thenReturn(asList(budgets));
    }

}
