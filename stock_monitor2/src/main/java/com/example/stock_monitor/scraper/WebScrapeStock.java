package com.example.stock_monitor.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;
import java.util.*;
import java.util.concurrent.*;

@Component
public class WebScrapeStock {
    public static double getStockPrice(String stockSymbol){
        try {
            double cur=0;
            while (cur>5000 ||  cur==0) {
                String stockPrice = sgetStockPrice(stockSymbol);
                //
                String sanitizedPrice = stockPrice.replace(",", "");
                cur=Double.parseDouble(sanitizedPrice);

            }
            System.out.println("The current price of " + stockSymbol + " is: " + cur);
            return cur;
        } catch (IOException e) {
            System.out.println("An error occurred while fetching the stock price: " + e.getMessage());
        }
        return 0;
    }
    public static void task() {
        getStockPrice("TSLA");
    }
    public static void task2() {
        System.out.println("Task 2 has completed");
    }
    public static void main(String[] args) {
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(WebScrapeStock::task, 0, 15, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(WebScrapeStock::task2, 0, 5, TimeUnit.SECONDS);

    }

    private static final int MAX_RETRIES = 3;
    private static final int RETRY_DELAY_MS = 2000;

    public static String sgetStockPrice(String stockSymbol) throws IOException {
        String url = "https://finance.yahoo.com/quote/" + stockSymbol;
        int attempts = 0;
        while (attempts < MAX_RETRIES) {
            try {
                Document doc = Jsoup.connect(url).get();
                String selector = String.format("fin-streamer[data-symbol='%s'][data-field='regularMarketPrice']", stockSymbol);
                Element priceElement = doc.selectFirst(selector);

                if (priceElement == null) {
                    System.out.println("Price element not found for stock symbol: " + stockSymbol);
                    return null;
                }

                String stockPrice = priceElement.text();
                if (stockPrice != null && !stockPrice.isEmpty()) {
                    return stockPrice;
                } else {
                    System.out.println("Stock price is empty for stock symbol: " + stockSymbol);
                    return null;
                }
            } catch (IOException e) {
                System.out.println("Error fetching stock price, attempt " + (attempts + 1));
                e.printStackTrace();
                attempts++;
                try {
                    Thread.sleep(RETRY_DELAY_MS);
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    throw new IOException("Thread was interrupted", interruptedException);
                }
            }
        }
        return null;
    }
}