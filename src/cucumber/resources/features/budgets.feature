@user
Feature: Budgets

  Scenario: Add a budget
    When add budget as month "2017-05" and amount 1000
    Then you will see all budgets as below
      | month   | amount |
      | 2017-05 | 1000   |