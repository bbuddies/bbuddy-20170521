package com.odde.bbuddy.budget.controller;

import com.odde.bbuddy.budget.domain.Budgets;
import com.odde.bbuddy.budget.repo.Budget;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class BudgetControllerTest {
    Budgets budgets = mock(Budgets.class);
    BudgetController controller = new BudgetController(budgets);

    @Test
    public void save_budget() throws Exception {
        Budget budget = saveBudget("2017-08", 1000);

        controller.save(budget);

        verify(budgets).save(budget);
    }

    @Test
    public void save_budget_with_wrong_format_month() throws Exception {
        String wrongMonth = "2017/08";
        Budget budget = saveBudget(wrongMonth, 0);

        ModelAndView mav = controller.save(budget);

        assertTrue(mav.getModel().containsKey("monthErrMsg"));
        assertTrue(mav.getModel().containsKey("amountErrMsg"));

        verify(budgets, times(0)).save(budget);
    }

    private Budget saveBudget(String month,
                              int amount) {
        Budget budget = new Budget();
        budget.setMonth(month);
        budget.setAmount(amount);
        return budget;
    }

    @Test
    public void get_budgets_list() throws Exception {
        List<Budget> budgetList = Arrays.asList(new Budget());
        when(budgets.getAll()).thenReturn(budgetList);

        ModelAndView result = controller.index();

        assertEquals("budgets/index", result.getViewName());
        assertEquals(budgetList,
                     result.getModel()
                           .get("budgets"));
    }
}
