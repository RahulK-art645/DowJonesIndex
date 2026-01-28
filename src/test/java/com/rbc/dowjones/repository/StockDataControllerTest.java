package com.rbc.dowjones.repository;

import com.rbc.dowjones.repository.controller.StockDataController;
import com.rbc.dowjones.repository.dto.StockDataResponseDto;
import com.rbc.dowjones.repository.service.StockDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StockDataController.class)
public class StockDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockDataService service;

    @Test
    void addStock_shouldReturn200() throws Exception{

        StockDataResponseDto responseDto=new StockDataResponseDto();
        responseDto.setStock("CSCO");

        when(service.addRecord(any())).thenReturn(responseDto);

        mockMvc.perform(post("/api/stock-data/add").contentType(MediaType.
                APPLICATION_JSON).content("""
    {
    "quarter":2
    "stock":"IBM",
    "date":"2024-01-22",
    "open": "29.80",
    "high": "87.70",
    "close": "3.60",
    "low": 7.60,
    "volume": 1000,
    "percentChangePrice":-5.20,
    "percentChangeVolumeOverLastWk":-2.10,
    "previousWeeksVolume":950,
    "nextWeeksOpen":12.20,
    "nextWeeksClose":2.90,
    "daysToNextDividend":4.90,
    "percentReturnNextDividend":5.60
    }
    """)).andExpect(status().isOk());

    }

    @Test
    void addStock_shouldFailValidation() throws Exception{


        mockMvc.perform(post("/api/stock-data/add").contentType(MediaType.
                APPLICATION_JSON).content("{}")).andExpect(status().isBadRequest());

    }


}
