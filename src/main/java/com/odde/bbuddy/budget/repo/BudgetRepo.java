package com.odde.bbuddy.budget.repo;

import com.odde.bbuddy.budget.Budget;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface BudgetRepo extends Repository<Budget, Long> {
    void save(Budget budget);

    List<Budget> findAll();
}
