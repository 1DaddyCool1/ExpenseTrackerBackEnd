package com.webService.service.impl;

import com.webService.model.AppUser;
import com.webService.model.Expense;
import com.webService.repository.ExpenseRepository;
import com.webService.service.AppUserService;
import com.webService.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Profile("db")
public class ExpenseServiceImplDB implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final AppUserService appUserService;

    @Autowired
    public ExpenseServiceImplDB(ExpenseRepository expenseRepository, AppUserService appUserService) {
        this.expenseRepository = expenseRepository;
        this.appUserService = appUserService;
    }

    @Override
    public List<Expense> getAllUserExpenses(Long userId) {
        return new ArrayList<>(
                expenseRepository.findByUserIdOrderByDateDesc(userId)
        );
    }

    @Override
    public List<Expense> getExpenseByDate(String date, Long userId) {
        return expenseRepository.findByUserIdOrderByDateDesc(userId)
                .stream()
                .filter(expense -> expense.getDate()
                        .equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<Expense> getExpenseByCategoryAndMonth(String category, String month, Long userId) {
        return expenseRepository.findByUserIdOrderByDateDesc(userId)
                .stream()
                .filter(e -> e.getCategory().equalsIgnoreCase(category) && e.getDate().startsWith(month))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllExpenseCategories(Long userId) {
        return expenseRepository.findByUserIdOrderByDateDesc(userId)
                .stream()
                .map(Expense::getCategory)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Expense> getExpenseById(Long id, Long userId) {
        return expenseRepository.findByIdAndUserId(id, userId);
    }

    @Override
    public Expense addExpense(Expense expense, Long userId) {
        Optional<AppUser> userOptional = appUserService.findUserById(userId);
        if (userOptional.isPresent()) {
            AppUser appUser = userOptional.get();
            expense.setUser(appUser);
            return expenseRepository.save(expense);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public boolean updateExpense(Expense updatedExpense, Long userId) {
        Optional<Expense> expenseOptional = expenseRepository.findByIdAndUserId(updatedExpense.getId(), userId);
        if(expenseOptional.isPresent()) {
            updatedExpense.setUser(expenseOptional.get().getUser());
            expenseRepository.save(updatedExpense);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteExpense(Long id,  Long userId) {
        if (expenseRepository.findByIdAndUserId(id, userId).isPresent()) {
            expenseRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
