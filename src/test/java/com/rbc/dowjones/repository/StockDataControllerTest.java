package com.rbc.dowjones.repository;

import com.rbc.dowjones.repository.controller.StockDataController;
import com.rbc.dowjones.repository.service.StockDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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
        mockMvc.perform(post("/api/stock-data/add").contentType(MediaType.APPLICATION_JSON).content("""
    {
    "stock":"CSCO",
    "date":"2020-01-01",
    
    
    }
    """)).andExpect(status().isOk());

    }

    void addStock_shouldFailValidation() throws Exception{


        mockMvc.perform(post("/api/stock-data/add").contentType(MediaType.APPLICATION_JSON).content("{}")).andExpect(status().isBadRequest());

    }


}
