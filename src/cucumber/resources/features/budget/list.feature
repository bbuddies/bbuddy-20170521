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