package com.odde.bbuddy.budget.controller;

import com.odde.bbuddy.budget.domain.Budgets;
import com.odde.bbuddy.budget.repo.Budget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

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
        return getModelAndView("budgets/index");
    }

    @GetMapping
    public ModelAndView index() {
        ModelAndView modelAndView = getModelAndView("budgets/index");

        modelAndView.getModel()
                    .put("budgets", budgets.getAll());
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
