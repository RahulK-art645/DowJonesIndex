package com.rbc.dowjones.repository;

import com.rbc.dowjones.repository.dto.StockDataRequestDto;
import com.rbc.dowjones.repository.dto.StockDataResponseDto;
import com.rbc.dowjones.repository.model.StockData;
import com.rbc.dowjones.repository.repository.StockDataRepository;
import com.rbc.dowjones.repository.service.StockDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class StockDataServiceTest {

    @Mock
    private StockDataRepository repository;

    @InjectMocks
    private StockDataService service;

    @Test
    void addRecord_shouldSaveData(){
        StockDataRequestDto requestDto=new StockDataRequestDto();
        requestDto.setStock("DIS");
        requestDto.setDate(LocalDate.now());
        requestDto.setOpen(BigDecimal.valueOf(10));
        requestDto.setHigh(BigDecimal.valueOf(12));
        requestDto.setLow(BigDecimal.valueOf(9));
        requestDto.setClose(BigDecimal.valueOf(11));

        StockData savedEntity = new StockData();
        savedEntity.setId(1L);
        savedEntity.setStock("DIS");
        when(repository.save(any())).thenReturn(savedEntity);

        StockDataResponseDto saved=service.addRecord(requestDto);

        assertNotNull(saved);
        verify(repository).save(any());

    }

    @Test
    void updateById_shouldUpdateFields(){

        StockData existing=new StockData();

        existing.setId(1L);
        existing.setStock("AAPL");
        existing.setDate(LocalDate.now());

        when(repository.findById(1L)).thenReturn(Optional.of(existing));

        when(repository.save(any())).thenReturn(existing);

        StockDataRequestDto requestDto = new StockDataRequestDto();
        requestDto.setStock("AAPL");
        requestDto.setDate(existing.getDate());
        requestDto.setOpen(BigDecimal.valueOf(20));
        requestDto.setHigh(BigDecimal.valueOf(25));
        requestDto.setClose(BigDecimal.valueOf(22));
        requestDto.setLow(BigDecimal.valueOf(18));

        StockDataResponseDto updated= service.updateById(1L, requestDto);

        assertNotNull(updated);

        verify(repository).save(any());

    }



}
