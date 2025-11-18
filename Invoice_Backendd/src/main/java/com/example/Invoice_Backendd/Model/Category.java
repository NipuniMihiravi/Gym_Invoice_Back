package com.example.Invoice_Backendd.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "categories")
public class Category {

    @Id
    private String id;

    private String name;

    // Store only Activity IDs
    private List<String> activityIds;

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getActivityIds() { return activityIds; }
    public void setActivityIds(List<String> activityIds) { this.activityIds = activityIds; }
}
