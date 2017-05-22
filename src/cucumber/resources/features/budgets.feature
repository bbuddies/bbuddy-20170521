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

  Scenario: Add a budget which amount is zero
    When add budget as month "2017-05" and amount 0
    Then you will see a message as below "The amount is invalid!"

    Scenario: Sum all budgets between 2 dates
      Given exists the following budgets
        | month   | amount |
        | 2016-11 | 30   |
        | 2017-05 | 31   |
        | 2017-06 | 30   |
        | 2017-08 | 31   |
      When specify startDate "2017-05-07" and endDate "2018-01-03"
      Then you will see the sum 0