package com.odde.bbuddy.budget.controller;

import com.odde.bbuddy.budget.domain.Budgets;
import com.odde.bbuddy.budget.repo.Budget;
import com.odde.bbuddy.budget.view.BudgetInView;
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

    @GetMapping("search")
    public String search() {
        return "budgets/search";
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

    @PostMapping("search")
    public ModelAndView search(String startDate,
                               String endDate) throws ParseException {
        ModelAndView modelAndView = getModelAndView("budgets/search");

        BigDecimal total = new BigDecimal(0);
        List<Budget> budgets = this.budgets.getAll();

        for (Budget budget : budgets) {
            total = getBudgetInDate(total, startDate, endDate, budget);
        }

        total.setScale(0, BigDecimal.ROUND_HALF_UP);
        modelAndView.getModel()
                    .put("total", total.toString());
        modelAndView.getModel()
                    .put("DateRange", startDate + "~" + endDate);
        return modelAndView;
    }

    public BigDecimal getBudgetInDate(BigDecimal total,
                                      String startDate,
                                      String endDate,
                                      Budget budget) throws ParseException {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        format1.setLenient(false);
        Calendar c = Calendar.getInstance();

        String month = budget.getMonth() + "-01"; // 2017-12
        Integer amount = budget.getAmount();

        Date monthStart = format1.parse(month);
        c.setTime(monthStart);

        Date start = format1.parse(startDate);
        Date end = format1.parse(endDate);

        int lastDate = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        BigDecimal avgAmount = BigDecimal.valueOf(amount * 1d / lastDate);

        for (int i = 0; i < lastDate; i++) {
            Date date = c.getTime();

            if (start.equals(date) || end.equals(date) || (start.before(date) && date.before(end))) {
                total = total.add(avgAmount);
            }
            c.add(Calendar.DATE, 1);
        }

        return total;
    }

    @GetMapping
    public ModelAndView index() {
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
