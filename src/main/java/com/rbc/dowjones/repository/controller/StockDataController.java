package com.rbc.dowjones.repository.controller;

import com.rbc.dowjones.repository.dto.BulkUploadResponseDto;
import com.rbc.dowjones.repository.dto.StockDataRequestDto;
import com.rbc.dowjones.repository.dto.StockDataResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestPart;
import com.rbc.dowjones.repository.model.StockData;
import com.rbc.dowjones.repository.service.StockDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<BulkUploadResponseDto> uploadBulkData(@RequestPart("file") MultipartFile file){

        BulkUploadResponseDto respponse=stockDataService.uploadBulkData(file);
        return ResponseEntity.ok(respponse);
    }

   /*Get data by Stock Ticker */
    @Operation(summary = "Get stock data by stock ticker")
    @GetMapping("/{stock}")
    public ResponseEntity<List<StockDataResponseDto>> getByStock(@PathVariable String stock){

        List<StockDataResponseDto> responseDtos= stockDataService.getByStock(stock);
        return ResponseEntity.ok(responseDtos);
    }

    /* Add Single stock record */
    @PostMapping(value = "/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add single stock record",
            operationId = "addStockData",
    requestBody= @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,content = @Content(
            mediaType = "application/json", schema = @Schema(implementation = StockDataRequestDto.class)
    )))

    public ResponseEntity<StockDataResponseDto> addStockData(@Valid @RequestBody StockDataRequestDto requestDto){

        StockDataResponseDto saved= stockDataService.addRecord(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
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

    @GetMapping("/id{id}")
    @Operation(summary = "Get Stock Data By Id")
    public ResponseEntity<StockDataResponseDto> getStockDataById(@PathVariable Long id){

        StockDataResponseDto response= stockDataService.getById(id);
        return ResponseEntity.ok(response);

    }


}
