package com.webService.controller;

import com.webService.model.AppUser;
import com.webService.model.Expense;
import com.webService.service.AppUserService;
import com.webService.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ExpenseController {
    private final ExpenseService expenseService;
    private final AppUserService appUserService;

    @Autowired
    public ExpenseController(ExpenseService expenseService, AppUserService appUserService) {
        this.expenseService = expenseService;
        this.appUserService = appUserService;
    }

    @GetMapping("/expenses")
    public ResponseEntity<List<Expense>> getAllUserExpenses(Authentication authentication) {
        String username = authentication.getName();
        AppUser appUser = appUserService.findByUsername(username);
        List<Expense> userExpenses = expenseService.getAllUserExpenses(appUser.getId());
        if(userExpenses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userExpenses, HttpStatus.OK);
    }

    @GetMapping("/expenses/categories")
    public ResponseEntity<List<String>> getAllExpenseCategories(Authentication authentication) {
        String username = authentication.getName();
        AppUser appUser = appUserService.findByUsername(username);
        List<String> categories = expenseService.getAllExpenseCategories(appUser.getId());
        if(categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/expenses/day/{date}")
    public ResponseEntity<List<Expense>> getExpensesByDay(@PathVariable String date, Authentication authentication) {
        String username = authentication.getName();
        AppUser appUser = appUserService.findByUsername(username);
        List<Expense> dates = expenseService.getExpenseByDate(date, appUser.getId());
        if(dates.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(dates, HttpStatus.OK);
    }

    @GetMapping("/expenses/category/{category}/month")
    public ResponseEntity<List<Expense>> getExpensesByCategoryAndMonth(@PathVariable String category, @RequestParam String month, Authentication authentication) {
        String username = authentication.getName();
        AppUser appUser = appUserService.findByUsername(username);
        List<Expense> expenses = expenseService.getExpenseByCategoryAndMonth(category, month, appUser.getId());
        if(expenses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping("/expenses/{id}")
    public ResponseEntity<Optional<Expense>> getExpenseById(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        AppUser appUser = appUserService.findByUsername(username);
        if(expenseService.getExpenseById(id, appUser.getId()).isPresent()) {
            return new ResponseEntity<>(expenseService.getExpenseById(id, appUser.getId()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/expenses")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense, Authentication authentication) {
        String username = authentication.getName();
        AppUser appUser = appUserService.findByUsername(username);
        Expense expenseSaved = expenseService.addExpense(expense, appUser.getId());
        return new ResponseEntity<>(expenseSaved, HttpStatus.CREATED);
    }

    @PutMapping("/expenses/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense expense, Authentication authentication) {
        String username = authentication.getName();
        AppUser appUser = appUserService.findByUsername(username);
        expense.setId(id);
        boolean isUpdated = expenseService.updateExpense(expense, appUser.getId());
        if(isUpdated) {
            return new ResponseEntity<>(expense, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("expenses/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        AppUser appUser = appUserService.findByUsername(username);
        boolean isDeleted = expenseService.deleteExpense(id, appUser.getId());
        if(isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
