package com.odde.bbuddy.budget.controller;

import com.odde.bbuddy.budget.Budget;
import com.odde.bbuddy.budget.domain.Budgets;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.logging.SimpleFormatter;

import static com.odde.bbuddy.common.Formats.DAY;

@Controller
public class BudgetsController {

    private final Budgets budgets;

    public BudgetsController(Budgets budgets) {
        this.budgets = budgets;
    }

    @GetMapping("/budgets/add")
    public String add() {
        return "/budgets/add";
    }

    @PostMapping("/budgets/add")
    public ModelAndView submitAdd(@ModelAttribute Budget budget) {
        budgets.addBudget(budget);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/budgets/index");
        modelAndView.addObject("budgets", budgets.getAllBudgets());
        return modelAndView;
    }

    @GetMapping("/budgets/sum")
    public String sum() {return  "/budgets/sum";}

    @PostMapping("/budgets/sum")
    public ModelAndView getSum(String startDate, String endDate) throws ParseException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/budgets/sum");
        modelAndView.addObject("sum",budgets.getSum(startDate,endDate));
        return modelAndView;
    }
}
