package com.webService;

import com.webService.model.Expense;
import com.webService.utils.ExpenseDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        List<Expense> expensesList = ExpenseDataLoader.getExpenses();
//        expensesList.forEach(System.out::println);
//    }
}