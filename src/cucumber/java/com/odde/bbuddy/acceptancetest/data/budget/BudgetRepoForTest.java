package com.odde.bbuddy.acceptancetest.data.budget;

import com.odde.bbuddy.budget.repo.Budget;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BudgetRepoForTest extends Repository<Budget, Long> {
    void save(Budget budget);
}
