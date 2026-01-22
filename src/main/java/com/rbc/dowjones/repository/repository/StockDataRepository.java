package com.rbc.dowjones.repository.repository;

import java.time.LocalDate;
import java.util.Optional;
import com.rbc.dowjones.repository.model.StockData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StockDataRepository extends JpaRepository<StockData,Long> {


    List<StockData> findByStock(String stock);
    Optional<StockData> findByStockAndDate(String stock, LocalDate date);




}
