package com.example.stock_monitor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;
    private Double price;
    private String action;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    // Default constructor (required by JPA)
    public Notification() {
        this.timestamp = new Date(); // Initialize timestamp with current time
    }

    // Constructor with arguments
    public Notification(String symbol, Double price, String action) {
        this.symbol = symbol;
        this.price = price;
        this.action = action;
        this.timestamp = new Date(); // Initialize timestamp with current time
    }

    // Getters and setters
    // Ensure appropriate annotations (@Column, @Temporal) for fields like 'timestamp'

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
