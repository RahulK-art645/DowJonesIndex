package com.rbc.dowjones.repository;

import com.rbc.dowjones.repository.model.StockData;
import com.rbc.dowjones.repository.repository.StockDataRepository;
import com.rbc.dowjones.repository.service.StockDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;


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
        StockData data=new StockData();
        data.setStock("DIS");

        when(repository.save(any())).thenReturn(data);

        StockData saved=service.addRecord(data);

        assertNotNull(saved);
        verify(repository).save(data);

    }

    @Test
    void updateById_shouldUpdateFields(){

        StockData existing=new StockData();

        existing.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(existing));

        when(repository.save(any())).thenReturn(existing);

        StockData updated = service.updateById(1L, existing);

        assertNotNull(updated);


    }



}
