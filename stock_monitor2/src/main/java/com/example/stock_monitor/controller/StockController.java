package com.example.stock_monitor.controller;

import com.example.stock_monitor.model.Stock;
import com.example.stock_monitor.service.StockService;
import com.example.stock_monitor.scraper.WebScrapeStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



import java.util.List;

@Controller
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private WebScrapeStock webScrapeStock;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("stock", new Stock());
        return "index";
    }

    @PostMapping("/addStock")
    public String addStock(@ModelAttribute Stock stock) {
        stockService.saveStock(stock);
        return "redirect:/";
    }

    @GetMapping("/stocks")
    public String viewStocks(Model model) {
        List<Stock> stocks = stockService.getAllStocks();
        model.addAttribute("stocks", stocks);
        return "stocks";
    }

    @PostMapping("/delete/{id}")
    public String deleteStock(@PathVariable Long id) {
        stockService.deleteStockById(id);
        return "redirect:/stocks";
    }

    @GetMapping("/get-stock-price")
    @ResponseBody
    public String getStockPrice(@RequestParam String symbol) {
        double stockPrice = WebScrapeStock.getStockPrice(symbol);
        if (stockPrice != 0.0) {
            return "Stock price for " + symbol + " is " + stockPrice;
        } else {
            return "Stock price for " + symbol + " not available.";
        }
        
    }
}
