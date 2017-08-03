@user
Feature: Budget

  Scenario: Add a budget
    When add a budget of month '2017-08' with amount 1000
    Then list budgets as below
      | month   | amount |
      | 2017-08 | 1000   |

  Scenario: add budget with wrong month
    When add a budget of month '2017/08' with amount 1000
    Then add budget failed with some message

  Scenario: Update a budget
    Given exist a budget of month '2017-10' with amount 10000
    When add a budget of month '2017-10' with amount 3000
    Then list budgets as below
      | month   | amount |
      | 2017-10 | 3000   |
    And list doesn't include a budget of month '2017-10' with amount 10000