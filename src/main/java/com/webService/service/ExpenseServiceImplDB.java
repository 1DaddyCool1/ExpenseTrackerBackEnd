package com.webService.service;

import com.webService.model.Expense;
import com.webService.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Profile("db")
public class ExpenseServiceImplDB implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseServiceImplDB(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<Expense> getExpenseByDate(String date) {
        return expenseRepository.findAll()
                .stream()
                .filter(expense -> expense.getDate().equalsIgnoreCase(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<Expense> getExpenseByCategoryAndMonth(String category, String month) {
        return expenseRepository.findAll()
                .stream()
                .filter(e -> e.getCategory().equalsIgnoreCase(category) && e.getDate().startsWith(month))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllExpenseCategories() {
        return expenseRepository.findAll()
                .stream()
                .map(Expense::getCategory)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }

    @Override
    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public boolean updateExpense(Expense updatedExpense) {
        if(expenseRepository.existsById(updatedExpense.getId())) {
            expenseRepository.save(updatedExpense);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteExpense(Long id) {
        if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
