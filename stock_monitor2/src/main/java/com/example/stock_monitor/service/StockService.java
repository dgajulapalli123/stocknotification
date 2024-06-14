package com.example.stock_monitor.service;

import com.example.stock_monitor.model.Stock;
import com.example.stock_monitor.model.Notification;
import com.example.stock_monitor.repository.NotificationRepository;
import com.example.stock_monitor.repository.StockRepository;
import com.example.stock_monitor.scraper.WebScrapeStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.awt.Toolkit;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private WebScrapeStock webScrapeStock;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private NotificationRepository notificationRepository;

    public Stock saveStock(Stock stock) {
        double currentPrice = webScrapeStock.getStockPrice(stock.getSymbol());
        
        stock.setPrice(currentPrice);
        return stockRepository.save(stock);
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @Scheduled(fixedRate = 15000)
    public void updateStockPricesAndNotify() {
        List<Stock> stocks = stockRepository.findAll();
        for (Stock stock : stocks) {
            double currentPrice = webScrapeStock.getStockPrice(stock.getSymbol());
            stock.setPrice(currentPrice);
            stockRepository.save(stock);

            // Notify clients of the updated stock
            messagingTemplate.convertAndSend("/topic/stocks", stock);

            String actionMessage = "";
            if (stock.getAction().equalsIgnoreCase("buy") && currentPrice <= stock.getThreshold()) {
                actionMessage = "Buy " + stock.getSymbol() + " at price " + stock.getPrice();
            } else if (stock.getAction().equalsIgnoreCase("sell") && currentPrice >= stock.getThreshold()) {
                actionMessage = "Sell " + stock.getSymbol() + " at price " + stock.getPrice();
            }

            if (!actionMessage.isEmpty()) {
                sendNotification(stock, actionMessage);
            }
        }
    }

    private void sendNotification(Stock stock, String message) {
        Notification notification = new Notification(stock.getSymbol(), stock.getPrice(), message);
        notificationRepository.save(notification);
        messagingTemplate.convertAndSend("/topic/notifications", notification);
    }


    public double getStockPrice(String symbol) {
        return webScrapeStock.getStockPrice(symbol);
    }

    public void deleteStockById(Long id) {
        Optional<Stock> stockOptional = stockRepository.findById(id);
        stockOptional.ifPresent(stockRepository::delete);
    }

    public void clearDatabase() {
        stockRepository.deleteAll();
    }

    // Add method to save a new stock with symbol, price, and threshold
    public Stock addStock(String symbol, double price, double threshold) {
        Stock stock = new Stock(symbol, price, threshold);
        return stockRepository.save(stock);
    }
}
