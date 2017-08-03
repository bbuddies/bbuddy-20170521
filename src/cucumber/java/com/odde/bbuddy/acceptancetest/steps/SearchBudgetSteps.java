package com.odde.bbuddy.acceptancetest.steps;

import com.odde.bbuddy.acceptancetest.data.EditableBudget;
import com.odde.bbuddy.acceptancetest.data.Messages;
import com.odde.bbuddy.acceptancetest.data.budget.BudgetRepoForTest;
import com.odde.bbuddy.acceptancetest.driver.UiDriver;
import com.odde.bbuddy.budget.repo.Budget;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.fail;

public class SearchBudgetSteps {

    @Autowired
    UiDriver driver;

    @Autowired
    BudgetRepoForTest repo;

    @Given("^Then list budgets as below$")
    public void then_list_budgets_as_below(List<EditableBudget> budgets) throws Throwable {
        int i = 0;
        for (EditableBudget budget : budgets) {
            repo.save(new Budget(i++, budget.month, budget.amount));
        }
    }

    @When("^search budget between '(.+)' and '(.+)'$")
    public void search_budget_between_and(String startDate,
                                          String endDate) throws Throwable {
        driver.navigateTo("/budgets/search");
        driver.inputTextByName(startDate, "startDate");
        driver.inputTextByName(endDate, "endDate");
        driver.clickByText("Search");
    }

    @Then("^total budget is (\\d+)$")
    public void total_budget_is(int total) throws Throwable {
        driver.waitForTextPresent(String.valueOf(total));
    }
}
