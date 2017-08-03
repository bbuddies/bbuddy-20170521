package com.odde.bbuddy.budget.repo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "budgets")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Budget {

    @Id
    @GeneratedValue
    private long id;

    @NotBlank
    private String month;

    @NotNull
    private Integer amount;

    public Budget(String month,
                  Integer amount) {
        this.month = month;
        this.amount = amount;
    }
}
