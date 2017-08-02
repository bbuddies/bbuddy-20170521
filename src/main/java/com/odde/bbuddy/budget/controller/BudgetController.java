package com.odde.bbuddy.budget.controller;

import com.odde.bbuddy.budget.domain.Budgets;
import com.odde.bbuddy.budget.repo.Budget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("budgets")
public class BudgetController {

    private final Budgets budgets;

    @Autowired
    public BudgetController(Budgets budgets) {
        this.budgets = budgets;
    }

    @GetMapping("add")
    public String add(){
        return "budgets/add";
    }

    @PostMapping("add")
    public String save(Budget budget){
        budgets.save(budget);
        return "redirect:/budgets";
    }

    @GetMapping
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("budgets/index");
        modelAndView.getModel().put("budgets", budgets.getAll());
        return modelAndView;
    }
}
