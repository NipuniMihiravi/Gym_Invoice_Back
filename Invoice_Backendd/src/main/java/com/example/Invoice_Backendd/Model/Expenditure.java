package com.example.Invoice_Backendd.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "expenditures")
public class Expenditure {

    @Id
    private String id;
    private String name;          // expenditure name (select dropdown)
    private Double cost;
    private LocalDate date;
    private String description;

    public Expenditure() {}

    public Expenditure(String name, Double cost, LocalDate date, String description) {
        this.name = name;
        this.cost = cost;
        this.date = date;
        this.description = description;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getCost() { return cost; }
    public void setCost(Double cost) { this.cost = cost; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
