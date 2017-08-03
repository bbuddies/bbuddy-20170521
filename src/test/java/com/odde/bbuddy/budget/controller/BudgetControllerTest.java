package com.odde.bbuddy.budget.controller;

import com.odde.bbuddy.budget.domain.Budgets;
import com.odde.bbuddy.budget.repo.Budget;
import com.odde.bbuddy.budget.view.BudgetInView;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;
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
    public void start_and_end_in_same_month() throws Exception {
        List<Budget> existingBudgets = asList(new Budget("2017-12", 3100));

        assertEquals(valueOf(1000), getTotalBudgetFromDateRange("2017-12-01", "2017-12-10", existingBudgets));
    }

    @Test
    public void start_and_end_in_two_continuous_months() throws Exception {
        List<Budget> existingBudgets = asList(
                new Budget("2017-03", 3100),
                new Budget("2017-04", 3000));

        assertEquals(valueOf(3100), getTotalBudgetFromDateRange("2017-03-16", "2017-04-15", existingBudgets));
    }

    @Test
    public void start_and_end_in_multiple_continuous_months() throws Exception {
        List<Budget> existingBudgets = asList(
                new Budget("2017-03", 3100),
                new Budget("2017-04", 3000),
                new Budget("2017-05", 3100),
                new Budget("2017-06", 3000));

        assertEquals(valueOf(8100), getTotalBudgetFromDateRange("2017-03-22", "2017-06-10", existingBudgets));
    }

    @Test
    public void start_not_in_budget_month() throws Exception {
        List<Budget> existingBudgets = asList(
                new Budget("2017-03", 3100));

        assertEquals(valueOf(1000), getTotalBudgetFromDateRange("2017-02-22", "2017-03-10", existingBudgets));
    }

    @Test
    public void end_not_in_budget_month() throws Exception {
        List<Budget> existingBudgets = asList(
                new Budget("2017-03", 3100));

        assertEquals(valueOf(1000), getTotalBudgetFromDateRange("2017-03-22", "2017-04-01", existingBudgets));
    }
    
    @Test
    public void some_month_not_have_budget() throws ParseException {
        List<Budget> existingBudgets = asList(
                new Budget("2017-03", 3100),
                new Budget("2017-05", 3100));

        assertEquals(valueOf(1500), getTotalBudgetFromDateRange("2017-03-22", "2017-05-05", existingBudgets));
    }

    @Test
    public void search_budget_when_no_date_in_date_range() throws Exception {
        BigDecimal total = getTotalBudgetFromDateRange("2017-11-01", "2017-11-10", asList(new Budget("2017-02", 2800),
                                             new Budget("2018-02", 2800),
                                             new Budget("2019-02", 2800),
                                             new Budget("2020-02", 2800),
                                             new Budget("2017-03", 3000),
                                             new Budget("2017-12", 3100)));
        assertEquals(valueOf(0), total);
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
        return asList(budget);
    }

    private BigDecimal getTotalBudgetFromDateRange(String startDate,
                                                   String endDate, List<Budget> budgets) throws ParseException {
        return controller.getBudgetInDate(startDate, endDate, budgets);
    }
}
