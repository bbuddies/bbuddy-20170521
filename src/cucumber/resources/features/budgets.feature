@user @budget
Feature: Budgets

  Scenario: Add a budget
    When add budget as month "2017-05" and amount 1000
    Then you will see all budgets as below
      | month   | amount |
      | 2017-05 | 1000   |

  Scenario: Replace a budget
    Given exists the following budget
      | month   | amount |
      | 2017-06 | 1500   |
    When add budget as month "2017-06" and amount 2000
    Then you will not see the existing following budget
      | month   | amount |
      | 2017-06 | 1500   |
    Then you will see all budgets as below
      | month   | amount |
      | 2017-06 | 2000   |