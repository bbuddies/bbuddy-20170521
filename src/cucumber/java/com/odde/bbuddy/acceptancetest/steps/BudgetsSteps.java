package com.odde.bbuddy.acceptancetest.steps;

import com.odde.bbuddy.acceptancetest.data.Budget;
import com.odde.bbuddy.acceptancetest.driver.UiDriver;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
    public void you_will_see_all_budgets_as_below(List<Budget> budgets) throws Throwable {
        Budget budget = budgets.get(0);
        assertThat(uiDriver.getAllTextInPage()).contains(budget.getAmount());
        assertThat(uiDriver.getAllTextInPage()).contains(budget.getMonth());
    }

    @Given("^exists the following budget$")
    public void existsTheFollowingBudget(List<Budget> budgets) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
        Budget budget = budgets.get(0);
        //2017-05,1000
        add_budget_as_month_and_amount(budget.getMonth(), Integer.parseInt(budget.getAmount()));

    }


    @Then("^you will not see the existing following budget$")
    public void youWillNotSeeTheExistingFollowingBudget(List<Budget> budgets) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Budget budget = budgets.get(0);
        assertThat(uiDriver.getAllTextInPage()).doesNotContain(budget.getAmount());
    }


    @Then("^you will see a message as below \"([^\"]*)\"$")
    public void youWillSeeAMessageAsBelow(String arg0) throws Throwable {
        assertThat(uiDriver.getAllTextInPage()).contains(arg0);
    }

    @Given("^exists the following budgets$")
    public void existsTheFollowingBudgets(List<Budget> budgets) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        for (Budget budget: budgets
             ) {
            add_budget_as_month_and_amount(budget.getMonth(), Integer.parseInt(budget.getAmount()));
        }
    }

    @When("^specify startDate \"([^\"]*)\" and endDate \"([^\"]*)\"$")
    public void specifyStartDateAndEndDate(String startDate, String endDate) throws Throwable {
        uiDriver.navigateTo("/budgets/sum");
        uiDriver.inputTextByName(startDate, "startDate");
        uiDriver.inputTextByName(endDate, "endDate");
        uiDriver.clickByText("sum");
    }

    @Then("^you will see the sum (\\d+)$")
    public void youWillSeeTheSum(int arg0) throws Throwable {
        assertThat(uiDriver.getAllTextInPage()).contains(Integer.toString(arg0));
    }
}
