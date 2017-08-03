package com.odde.bbuddy.budget.controller;

import com.odde.bbuddy.budget.domain.Budgets;
import com.odde.bbuddy.budget.repo.Budget;
import com.odde.bbuddy.budget.view.BudgetInView;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.text.ParseException;
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

        assertTrue(mav.getModel()
                      .containsKey("monthErrMsg"));
        assertTrue(mav.getModel()
                      .containsKey("amountErrMsg"));

        verify(budgets, never()).save(budget);
    }

    @Test
    public void search_budget() throws Exception {
        BigDecimal total = getTotalBudget("2017-12-01", "2017-12-10");
        assertEquals(BigDecimal.valueOf(1000.0), total);
    }

    @Test
    public void search_budget_out_of_date() throws Exception {
        BigDecimal total = getTotalBudget("2017-11-01", "2017-11-10");

        assertEquals(BigDecimal.valueOf(0), total);
    }

    @Test
    @Ignore
    public void get_budgets_list() throws Exception {

        when(budgets.getAll()).thenReturn(budgets(1000, "2017-10"));
        BudgetInView budgetsInView = budgetsInView("TWD 1,000.00", "2017-10");

        ModelAndView result = controller.index();

        assertEquals("budgets/index", result.getViewName());
        BudgetInView actualBudgetInView = ((List<BudgetInView>) result.getModel()
                                                                      .get("budgets")).get(0);
        assertEquals(budgetsInView.getAmount(), actualBudgetInView.getAmount());
        assertEquals(budgetsInView.getMonth(), actualBudgetInView.getMonth());
    }

    private Budget saveBudget(String month,
                              int amount) {
        Budget budget = new Budget();
        budget.setMonth(month);
        budget.setAmount(amount);
        return budget;
    }

    private BudgetInView budgetsInView(String amount,
                                       String month) {
        Budget b = new Budget();
        b.setAmount(Integer.valueOf(amount));
        b.setMonth(month);

        BudgetInView budgetInView = new BudgetInView(b);

        return budgetInView;
    }

    private List<Budget> budgets(int amount,
                                 String month) {
        Budget budget = new Budget();
        budget.setAmount(amount);
        budget.setMonth(month);
        return Arrays.asList(budget);
    }

    private BigDecimal getTotalBudget(String startDate,
                                      String endDate) throws ParseException {
        Budget budget = new Budget();
        budget.setMonth("2017-12");
        budget.setAmount(3100);

        BigDecimal total = BigDecimal.ZERO;
        return controller.getBudgetInDate(total, startDate, endDate, budget);
    }
}
