package com.rbc.dowjones.repository;

import com.rbc.dowjones.model.StockData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StockDataRepository extends JpaRepository<StockData,Long> {

    List<StockData> findByStock(String stock);


}
