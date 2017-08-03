package com.odde.bbuddy.budget.repo;

import org.springframework.data.repository.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface BudgetRepo extends Repository<Budget, Long> {
    void save(Budget budget);

    List<Budget> findAll();

    Budget findByMonth(String month);
}
