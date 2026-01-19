package com.rbc.dowjones.service;

import com.rbc.dowjones.model.StockData;
import com.rbc.dowjones.repository.StockDataRepository;
import com.rbc.dowjones.util.CsvParserUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

        repository.saveAll(records);
    }

    public List<StockData> getByStock(String stock){

        return repository.findByStock(stock);
    }

    public StockData addRecord(StockData stockData){

        return repository.save(stockData);

    }

}
