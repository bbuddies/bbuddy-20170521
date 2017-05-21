package com.odde.bbuddy.budget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BudgetsController {

    @GetMapping("/budgets/add")
    public String add() {
        return "/budgets/add";
    }
}
