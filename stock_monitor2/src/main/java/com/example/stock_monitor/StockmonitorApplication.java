package com.example.stock_monitor;

import com.example.stock_monitor.model.Stock;
import com.example.stock_monitor.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = "com.example.stock_monitor.repository")
@EntityScan("com.example.stock_monitor.model")

public class StockmonitorApplication {

    @Autowired
    private StockService stockService;

    public static void main(String[] args) {
        SpringApplication.run(StockmonitorApplication.class, args);
    }



    @PostConstruct
    public void init() {
        // Clear the database
        stockService.clearDatabase();

    }



}
