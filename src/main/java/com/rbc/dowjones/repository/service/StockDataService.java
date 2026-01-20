package com.rbc.dowjones.repository.service;

import com.rbc.dowjones.repository.model.StockData;
import com.rbc.dowjones.repository.repository.StockDataRepository;
import com.rbc.dowjones.repository.util.CsvParserUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class StockDataService {

    private final StockDataRepository repository;

    private final CsvParserUtil csvParserUtil;

    public StockDataService(StockDataRepository repository, CsvParserUtil csvParserUtil){

        this.repository=repository;
        this.csvParserUtil=csvParserUtil;

    }

    //Bulk upload
    public void uploadBulkData(MultipartFile file){

        List<StockData> records=csvParserUtil.parse(file);

        for(StockData stockData : records){

            upsertStockData(stockData);
        }
    }

    public void upsertStockData(StockData stockData){

        Optional<StockData> existing=repository.findByStockAndDate(stockData.getStock(),stockData.getDate());

        if(existing.isPresent()) {

            StockData dbData = existing.get();
            dbData.setOpen(stockData.getOpen());
            dbData.setHigh(stockData.getHigh());
            dbData.setLow(stockData.getLow());
            dbData.setPercentChangePrice(stockData.getPercentChangePrice());
            dbData.setClose(stockData.getClose());
            dbData.setVolume(stockData.getVolume());
            dbData.setPercentChangeVolumeOverLastWk(stockData.getPercentChangeVolumeOverLastWk());
            dbData.setPreviousWeeksVolume(stockData.getPreviousWeeksVolume());
            dbData.setNextWeeksOpen(stockData.getNextWeeksOpen());
            dbData.setNextWeeksClose(stockData.getNextWeeksClose());
            dbData.setPercentChangeNextWeeksPrice(stockData.getPercentChangeNextWeeksPrice());
            dbData.setDaysToNextDividend(stockData.getDaysToNextDividend());
            dbData.setPercentReturnNextDividend(stockData.getPercentReturnNextDividend());


            repository.save(dbData);
        }else{

            repository.save(stockData);
        }
        }


    public List<StockData> getByStock(String stock){

        return repository.findByStock(stock);
    }

    public StockData addRecord(StockData stockData){

        return repository.save(stockData);

    }

    /* Update by ID */
    public StockData updateById(Long id,StockData stockData){

        StockData dbData=repository.findById(id).orElseThrow(()->new RuntimeException("Stock record not found"));
        dbData.setOpen(stockData.getOpen());
        dbData.setHigh(stockData.getHigh());
        dbData.setLow(stockData.getLow());
        dbData.setClose(stockData.getClose());
        dbData.setVolume(stockData.getVolume());
        dbData.setQuarter(stockData.getQuarter());
        dbData.setStock(stockData.getStock());
        dbData.setDate(stockData.getDate());
        dbData.setPercentChangePrice(stockData.getPercentChangePrice());
        dbData.setPercentChangeVolumeOverLastWk(stockData.getPercentChangeVolumeOverLastWk());
        dbData.setPreviousWeeksVolume(stockData.getPreviousWeeksVolume());
        dbData.setNextWeeksOpen(stockData.getNextWeeksOpen());
        dbData.setNextWeeksClose(stockData.getNextWeeksClose());
        dbData.setPercentChangeNextWeeksPrice(stockData.getPercentChangeNextWeeksPrice());
        dbData.setDaysToNextDividend(stockData.getDaysToNextDividend());
        dbData.setPercentReturnNextDividend(stockData.getPercentReturnNextDividend());

        return repository.save(dbData);

    }

    public void deleteById(Long id){

        repository.deleteById(id);
    }

}

