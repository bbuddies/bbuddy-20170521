package com.odde.bbuddy.acceptancetest.steps;

import com.odde.bbuddy.acceptancetest.data.EditableBudget;
import com.odde.bbuddy.acceptancetest.data.Messages;
import com.odde.bbuddy.acceptancetest.data.budget.BudgetRepoForTest;
import com.odde.bbuddy.acceptancetest.driver.UiDriver;
import com.odde.bbuddy.budget.repo.Budget;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.fail;

public class BudgetListSteps {
    @Autowired
    UiDriver driver;

    @Autowired
    BudgetRepoForTest repo;

    @Autowired
    Messages messages;

    @When("^add a budget of month '(.+)' with amount (\\d+)$")
    public void add_a_budget_of_month_with_amount(String month,
                                                  int amount) throws Throwable {
        driver.navigateTo("/budgets/add");
        driver.inputTextByName(month, "month");
        driver.inputTextByName(String.valueOf(amount), "amount");
        driver.clickByText("Save");
    }

    @Then("^list budgets as below$")
    public void list_budgets_as_below(List<EditableBudget> budgets) throws Throwable {
        driver.waitForTextPresent(budgets.get(0).month);
        driver.waitForTextPresent(String.valueOf(budgets.get(0).amount));
    }

    @Given("^exist a budget of month '(.+)' with amount (\\d+)$")
    public void exist_a_budget_of_month_with_amount(String month, int amount) throws Throwable {
        repo.save(new Budget(1, month, amount));
    }

    @Then("^list doesn't include a budget of month '(.+)' with amount (\\d+)$")
    public void list_doesn_t_include_a_budget_of_month_with_amount(String month,
                                                                   int amount) throws Throwable {
        String allTextInPage = driver.getAllTextInPage();
        List<String> allText = Arrays.asList(allTextInPage.split("\\n"));
        for (String str : allText) {
            if (str.equals(month + " " + amount)) {
                fail("There's still a budget... " + month + " " + amount);
            }
        }
    }
}
