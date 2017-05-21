package com.odde.bbuddy.budget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BudgetsController {

    @GetMapping("/budgets/add")
    public String add() {
        return "/budgets/add";
    }

    @PostMapping("/budgets/add")
    public String submitAdd() {
        return "/budgets/index";
    }
}
