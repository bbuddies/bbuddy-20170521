package com.odde.bbuddy.budget.controller;

import com.odde.bbuddy.budget.Budget;
import com.odde.bbuddy.budget.domain.Budgets;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

    @PostMapping("/budgets/sum")
    public ModelAndView getAmountSum(@ModelAttribute String startTime,String endTime){


       return null;
    }

}
