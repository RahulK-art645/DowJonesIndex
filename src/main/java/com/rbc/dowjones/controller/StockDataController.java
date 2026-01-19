package com.rbc.dowjones.controller;

import com.rbc.dowjones.model.StockData;
import com.rbc.dowjones.service.StockDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@RequestMapping("/api/stock-data")
public class StockDataController {

    private final StockDataService stockDataService;

    public StockDataController(StockDataService stockDataService){

        this.stockDataService=stockDataService;

    }

    @PostMapping("/bulk-insert")
    public ResponseEntity<String> uploadBulkData(@RequestParam("file") MultipartFile file){

        stockDataService.uploadBulkData(file);
        return ResponseEntity.ok("Bulk data uploaded successfully..");
    }

    @GetMapping("/{stock}")
    public List<StockData> byStock(@PathVariable String stock){

        return stockDataService.getByStock(stock);
    }

    public StockData add(@RequestBody StockData data){

        return stockDataService.addRecord(data);
    }


}
