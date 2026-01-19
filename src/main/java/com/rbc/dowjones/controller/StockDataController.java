package com.rbc.dowjones.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestPart;
import com.rbc.dowjones.model.StockData;
import com.rbc.dowjones.service.StockDataService;
import org.springframework.http.MediaType;
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

    @Operation(summary = "Upload csv file")
    @ApiResponses({@ApiResponse( responseCode= "200", description="File uploaded successfully")})
    @PostMapping(value="/bulk-insert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadBulkData(@RequestPart("file") MultipartFile file){

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
