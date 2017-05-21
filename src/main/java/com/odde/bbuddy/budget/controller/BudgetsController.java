package com.odde.bbuddy.budget.controller;

import com.odde.bbuddy.budget.Budget;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@Controller
public class BudgetsController {

    @GetMapping("/budgets/add")
    public String add() {
        return "/budgets/add";
    }

    @PostMapping("/budgets/add")
    public ModelAndView submitAdd(@ModelAttribute Budget budget) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/budgets/index");
        modelAndView.addObject("budgets", Arrays.asList(budget));
        return modelAndView;
    }
}
