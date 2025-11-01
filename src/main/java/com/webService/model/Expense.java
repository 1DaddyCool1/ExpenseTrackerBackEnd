package com.webService.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

@Data
@Entity
public class Expense {
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("expenseType")
    private int expenseType;
    @JsonProperty("date")
    private String date;
    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("category")
    private String category;
    @JsonProperty("account")
    private String account;
    @JsonProperty("note")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private AppUser user;
}
