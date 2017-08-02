package com.odde.bbuddy.acceptancetest.steps;

import com.odde.bbuddy.acceptancetest.data.Budget;
import com.odde.bbuddy.acceptancetest.driver.UiDriver;
import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BudgetListSteps {
    @Autowired
    UiDriver driver;

    @When("^add a budget of month '(.+)' with amount (\\d+)$")
    public void add_a_budget_of_month_with_amount(String month, int amount) throws Throwable {
        driver.navigateTo("/budgets/add");
        driver.inputTextByName(month, "month");
        driver.inputTextByName(String.valueOf(amount), "amount");
        driver.clickByText("Save");
    }

    @Then("^list budgets as below$")
    public void list_budgets_as_below(List<Budget> budgets) throws Throwable {
        driver.waitForTextPresent(budgets.get(0).month);
        driver.waitForTextPresent(budgets.get(0).amount);
    }

}
