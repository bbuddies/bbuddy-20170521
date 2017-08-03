@user @budget
Feature: Budget

  Scenario: Add a budget
    When add a budget of month '2017-08' with amount 1000
    Then list budgets as below
      | month   | amount |
      | 2017-08 | 1000   |

  Scenario: add budget with wrong format month and 0 amount
    When add a budget of month '2017/08' with amount 0
    Then there is an error message for invalid date month
    And there is an error message for number amount should be larger than or equal to 1

  Scenario: Add a budget with existing month
    Given exist a budget of month '2017-10' with amount 10000
    When add a budget of month '2017-10' with amount 3000
    Then list budgets as below
      | month   | amount |
      | 2017-10 | 3000   |
    And list doesn't include a budget of month '2017-10' with amount 10000