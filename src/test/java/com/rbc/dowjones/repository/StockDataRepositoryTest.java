package com.rbc.dowjones.repository;

import com.rbc.dowjones.repository.model.StockData;
import com.rbc.dowjones.repository.repository.StockDataRepository;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StockDataRepositoryTest {

    @Autowired
    private StockDataRepository repository;

    @Test
    void findByStock_shouldReturnRecords(){

        StockData data=new StockData();
        data.setStock("CSCO");
        data.setDate(LocalDate.of(2020, 1, 1));
        data.setOpen(BigDecimal.valueOf(10));
        data.setHigh(BigDecimal.valueOf(13));
        data.setLow(BigDecimal.valueOf(9));
        data.setClose(BigDecimal.valueOf(11));
        data.setVolume(1000L);

        repository.save(data);

        List<StockData> result=repository.findByStock("CSCO");

        assertThat(result).isNotEmpty();
    }
}
