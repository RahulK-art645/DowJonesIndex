package com.rbc.dowjones.repository.controller;

import com.rbc.dowjones.repository.dto.BulkUploadResponseDto;
import com.rbc.dowjones.repository.dto.CommonResponse;
import com.rbc.dowjones.repository.dto.StockDataRequestDto;
import com.rbc.dowjones.repository.dto.StockDataResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestPart;
import com.rbc.dowjones.repository.service.StockDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/api/stock-data")
@Validated
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
    public ResponseEntity<CommonResponse<List<StockDataResponseDto>>> getByStock(@PathVariable @Pattern(regexp = "^[A-Z]{1,10}$",message = "Invalid stock symbol") String stock){

        List<StockDataResponseDto> responseDtos= stockDataService.getByStock(stock);
        CommonResponse<List<StockDataResponseDto>> response=new CommonResponse<>("Stock data fetched successfully for ticker: "+stock,responseDtos);
        return ResponseEntity.ok(response);
    }

    /* Add Single stock record */
    @Operation(summary = "Add single stock record")
    @PostMapping(value = "/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<StockDataResponseDto>> addStockData(@Valid @RequestBody StockDataRequestDto requestDto){

        StockDataResponseDto saved= stockDataService.addRecord(requestDto);

        CommonResponse<StockDataResponseDto> response=new CommonResponse<>("Stock data added successfully", saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Update stock data by ID")
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<StockDataResponseDto>> updateStockData(@PathVariable String id, @Valid @RequestBody StockDataRequestDto requestDto){

        StockDataResponseDto updated=stockDataService.updateById(id, requestDto);

        CommonResponse<StockDataResponseDto> response=new CommonResponse<>("Stock data updated successfully based on ID.", updated);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete stock data by ID")
    public ResponseEntity<String> deleteStockData(@PathVariable Long id){

        stockDataService.deleteById(id);
        return ResponseEntity.ok("Stock data deleted successfully..");

    }

    @GetMapping("/id{id}")
    @Operation(summary = "Get Stock Data By ID")
    public ResponseEntity<CommonResponse<StockDataResponseDto>> getStockDataById(@PathVariable String id){

        StockDataResponseDto response= stockDataService.getById(id);

         CommonResponse<StockDataResponseDto> response1= new CommonResponse<>
                 ("Stock data fetched successfully based on ID", response);

        return ResponseEntity.ok(response1);

    }


}
