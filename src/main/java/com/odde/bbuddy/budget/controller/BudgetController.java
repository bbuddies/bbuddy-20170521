package com.odde.bbuddy.budget.controller;

import com.odde.bbuddy.budget.domain.Budgets;
import com.odde.bbuddy.budget.domain.Period;
import com.odde.bbuddy.budget.repo.Budget;
import com.odde.bbuddy.budget.view.BudgetInView;
import com.odde.bbuddy.common.Formats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("budgets")
public class BudgetController {

    private final Budgets budgets;

    @Autowired
    public BudgetController(Budgets budgets) {
        this.budgets = budgets;
    }

    @GetMapping("add")
    public String add() {
        return "budgets/add";
    }

    @PostMapping("add")
    public ModelAndView save(Budget budget) {
        Map<String, String> errMSg = checkBudgetErr(budget);
        if (!errMSg.isEmpty()) {
            return modelAndViewWithError(errMSg);
        }

        budgets.save(budget);
        return getModelAndView("redirect:/budgets");
    }

    @PostMapping
    public ModelAndView search(String startDate,
                               String endDate) throws ParseException {
        ModelAndView modelAndView = getIndex();

        List<Budget> budgets = this.budgets.getAll();
        BigDecimal total = getBudgetInDate(startDate, endDate, budgets);

        modelAndView.getModel()
                    .put("total", total.toString());
        modelAndView.getModel()
                    .put("startDate", startDate);
        modelAndView.getModel()
                    .put("endDate", endDate);
        modelAndView.getModel()
                    .put("DateRange", startDate + "~" + endDate);
        return modelAndView;
    }

    public BigDecimal getBudgetInDate(String startDate,
                                      String endDate,
                                      List<Budget> budgets) throws ParseException {
        Date start = Formats.parseDate(startDate);
        Date end = Formats.parseDate(endDate);

        return getTotalAmountOfPeriod(budgets, new Period(start, end));
    }

    private BigDecimal getTotalAmountOfPeriod(List<Budget> budgets, Period period) throws ParseException {
        return BigDecimal.valueOf(budgets.stream()
                .mapToDouble(budget -> budget.overlappingAmount(period).doubleValue())
                .sum()).setScale(0);
    }

    @GetMapping
    public ModelAndView index() {
        ModelAndView modelAndView = getIndex();

        return modelAndView;
    }

    private ModelAndView getIndex() {
        ModelAndView modelAndView = getModelAndView("budgets/index");

        List<Budget> budgets = this.budgets.getAll();

        List<BudgetInView> budgetsInView = new ArrayList<>();
        budgets.forEach(budget -> {
            BudgetInView budgetInView = new BudgetInView(budget);
            budgetsInView.add(budgetInView);
        });

        modelAndView.getModel()
                    .put("budgets", budgetsInView);
        return modelAndView;
    }

    private Map<String, String> checkBudgetErr(Budget budget) {
        Map<String, String> errMSg = new HashMap<>();
        String month = budget.getMonth();
        Integer amount = budget.getAmount();

        // YYYY-MM
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
        format1.setLenient(false);
        try {
            format1.parse(month);
        } catch (ParseException e) {
            errMSg.put("monthErrMsg", "month should be a valid date");
        }

        if (amount <= 0) {
            errMSg.put("amountErrMsg", "amount should be larger than or equal to 1");
        }
        return errMSg;
    }

    private ModelAndView getModelAndView(String viewName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    private ModelAndView modelAndViewWithError(Map<String, String> errMSg) {
        ModelAndView modelAndView = getModelAndView("budgets/add");
        errMSg.forEach((k, v) -> modelAndView.getModel()
                                             .put(k, v));
        return modelAndView;
    }

}
