package com.odde.bbuddy.budget.controller;

import com.odde.bbuddy.budget.domain.Budgets;
import com.odde.bbuddy.budget.repo.Budget;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BudgetControllerTest {
    @Test
    public void save_budget() throws Exception {
        Budget budget = new Budget();
        Budgets budgets = mock(Budgets.class);
        BudgetController controller = new BudgetController(budgets);

        controller.save(budget);

        verify(budgets).save(budget);
    }

    @Test
    public void get_budgets_list() throws Exception {
        List<Budget> budgetList = Arrays.asList(new Budget());
        Budgets budgets = mock(Budgets.class);
        when(budgets.getAll()).thenReturn(budgetList);
        BudgetController controller = new BudgetController(budgets);

        ModelAndView result = controller.index();

        assertEquals("budgets/index", result.getViewName());
        assertEquals(budgetList, result.getModel().get("budgets"));
    }
}
