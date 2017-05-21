package com.odde.bbuddy.budget.controller;

import com.odde.bbuddy.budget.Budget;
import com.odde.bbuddy.budget.domain.Budgets;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import static com.odde.bbuddy.common.Formats.parseMonth;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BudgetsControllerTest {

    Budgets mockBudgets = mock(Budgets.class);
    BudgetsController controller = new BudgetsController(mockBudgets);
    Budget budget = new Budget() {{ setMonth(parseMonth("2017-05")); setAmount(1000);}};

    @Test
    public void submit_add_should_add_budget() {
        controller.submitAdd(budget);

        verify(mockBudgets).addBudget(budget);
    }
    
    @Test
    public void submit_add_should_go_to_index() {
        assertThat(controller.submitAdd(budget).getViewName()).isEqualTo("/budgets/index");
    }
    
    @Test
    public void submit_add_should_get_all_budgets() {
        when(mockBudgets.getAllBudgets()).thenReturn(asList(budget));

        ModelAndView modelAndView = controller.submitAdd(new Budget());

        assertThat(modelAndView.getModel().get("budgets")).isEqualToComparingFieldByField(asList(budget));
    }

}