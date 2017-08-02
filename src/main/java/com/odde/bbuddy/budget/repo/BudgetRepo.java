package com.odde.bbuddy.budget.repo;

import org.springframework.data.repository.Repository;

import javax.transaction.Transactional;

@Transactional
public interface BudgetRepo extends Repository<Budget, Long> {
    void save(Budget budget);
}
