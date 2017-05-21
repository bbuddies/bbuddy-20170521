package com.odde.bbuddy.acceptancetest.steps;

import com.odde.bbuddy.acceptancetest.driver.UiDriver;
import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class BudgetsSteps {

    @Autowired
    UiDriver uiDriver;

    @When("^add budget as month \"([^\"]*)\" and amount (\\d+)$")
    public void add_budget_as_month_and_amount(String month, int amount) throws Throwable {
        uiDriver.navigateTo("/budgets/add");
        uiDriver.inputTextByName(month, "month");
        uiDriver.inputTextByName(String.valueOf(amount), "amount");
        uiDriver.clickByText("Add");
    }

    @Then("^you will see all budgets as below$")
    public void you_will_see_all_budgets_as_below(DataTable arg1) throws Throwable {

    }
}
