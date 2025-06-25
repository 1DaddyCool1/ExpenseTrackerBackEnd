package com.webService.service.impl;

import com.webService.model.Expense;
import com.webService.service.ExpenseService;
import com.webService.utils.ExpenseDataLoader;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@Profile("json")
public class ExpenseServiceImplJson implements ExpenseService {

    private static final AtomicLong idCount = new AtomicLong();

    @Override
    public List<Expense> getExpenseByDate(String date) {
        return ExpenseDataLoader.getExpenses().stream()
                .filter(e -> e.getDate().equalsIgnoreCase(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<Expense> getExpenseByCategoryAndMonth(String category, String month) {
        return ExpenseDataLoader.getExpenses().stream()
                .filter(e -> e.getCategory().equalsIgnoreCase(category)
                        && e.getDate().startsWith(month))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllExpenseCategories() {
        return ExpenseDataLoader.getExpenses().stream()
                .map(Expense::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Expense> getExpenseById(Long id) {
        return ExpenseDataLoader.getExpenses().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    @Override
    public Expense addExpense(Expense expense) {
        expense.setId(idCount.incrementAndGet());
        ExpenseDataLoader.getExpenses().add(expense);
        return expense;
    }

    @Override
    public boolean updateExpense(Expense updatedExpense) {
        Optional<Expense> existingExpense = getExpenseById(updatedExpense.getId());
        if (existingExpense.isPresent()) {
            ExpenseDataLoader.getExpenses().remove(existingExpense.get());
            ExpenseDataLoader.getExpenses().add(updatedExpense);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteExpense(Long id) {
        Optional<Expense> existingExpense = getExpenseById(id);
        if (existingExpense.isPresent()) {
            ExpenseDataLoader.getExpenses().remove(existingExpense.get());
            return true;
        }
        return false;
    }
}
