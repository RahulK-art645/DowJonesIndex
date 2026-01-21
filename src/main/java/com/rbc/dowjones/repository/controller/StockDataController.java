package com.rbc.dowjones.repository.controller;

import com.rbc.dowjones.repository.dto.StockDataRequestDto;
import com.rbc.dowjones.repository.dto.StockDataResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestPart;
import com.rbc.dowjones.repository.model.StockData;
import com.rbc.dowjones.repository.service.StockDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/stock-data")
public class StockDataController {

    private final StockDataService stockDataService;

    public StockDataController(StockDataService stockDataService){

        this.stockDataService=stockDataService;

    }

    @Operation(summary = "Upload csv file for bulk stock data insert")
    @ApiResponses({@ApiResponse( responseCode= "200", description="File uploaded successfully")})
    @PostMapping(value="/bulk-insert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadBulkData(@RequestPart("file") MultipartFile file){

        stockDataService.uploadBulkData(file);
        return ResponseEntity.ok("Bulk data uploaded successfully..");
    }

   /*Get data by Stock Ticker */
    @Operation(summary = "Get stock data by stock ticker")
    @GetMapping("/{stock}")
    public ResponseEntity<List<StockDataResponseDto>> getByStock(@PathVariable String stock){

        List<StockDataResponseDto> responseDtos= stockDataService.getByStock(stock);
        return ResponseEntity.ok(responseDtos);
    }

    /* Add Single stock record */
    @Operation(summary = "Add single stock record")
    @PostMapping("/add")
    public ResponseEntity<StockDataResponseDto> addStockData(@Valid @RequestBody StockDataRequestDto requestDto){

        StockDataResponseDto saved= stockDataService.addRecord(requestDto);
        return ResponseEntity.ok(saved);
    }

    @Operation(summary = "Update stock data by ID")
    @PutMapping("/{id}")
    public ResponseEntity<StockDataResponseDto> updateStockData(@PathVariable Long id, @RequestBody StockDataRequestDto requestDto){

        StockDataResponseDto updated=stockDataService.updateById(id, requestDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete stock data by ID")
    public ResponseEntity<String> deleteStockData(@PathVariable Long id){

        stockDataService.deleteById(id);
        return ResponseEntity.ok("Stock data deleted successfully..");

    }


}
