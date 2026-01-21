package com.rbc.dowjones.repository.service;

import com.rbc.dowjones.repository.dto.StockDataRequestDto;
import com.rbc.dowjones.repository.dto.StockDataResponseDto;
import com.rbc.dowjones.repository.mapper.StockDataMapper;
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


    public List<StockDataResponseDto> getByStock(String stock){

        List<StockData> entities=repository.findByStock(stock);
        return entities.stream().map(StockDataMapper::toResponseDto).toList();
    }

    public StockDataResponseDto addRecord(StockDataRequestDto requestDto){

        StockData entity=StockDataMapper.toEntity(requestDto);
        StockData saved=repository.save(entity);
        return StockDataMapper.toResponseDto(saved);

    }

    /* Update by ID */
    public StockDataResponseDto updateById(Long id,StockDataRequestDto requestDto){

        StockData dbData=repository.findById(id).orElseThrow(()->new RuntimeException("Stock record not found"));
        dbData.setOpen(requestDto.getOpen());
        dbData.setHigh(requestDto.getHigh());
        dbData.setLow(requestDto.getLow());
        dbData.setClose(requestDto.getClose());
        dbData.setVolume(requestDto.getVolume());
        dbData.setQuarter(requestDto.getQuarter());
        dbData.setStock(requestDto.getStock());
        dbData.setDate(requestDto.getDate());
        dbData.setPercentChangePrice(requestDto.getPercentChangePrice());
        dbData.setPercentChangeVolumeOverLastWk(requestDto.getPercentChangeVolumeOverLastWk());
        dbData.setPreviousWeeksVolume(requestDto.getPreviousWeeksVolume());
        dbData.setNextWeeksOpen(requestDto.getNextWeeksOpen());
        dbData.setNextWeeksClose(requestDto.getNextWeeksClose());
        dbData.setPercentChangeNextWeeksPrice(requestDto.getPercentChangeNextWeeksPrice());
        dbData.setDaysToNextDividend(requestDto.getDaysToNextDividend());
        dbData.setPercentReturnNextDividend(requestDto.getPercentReturnNextDividend());

        StockData saved=repository.save(dbData);

        return StockDataMapper.toResponseDto(saved);

    }

    public void deleteById(Long id){

        repository.deleteById(id);
    }

}

