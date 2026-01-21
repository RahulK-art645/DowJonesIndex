package com.rbc.dowjones.repository.service;

import com.rbc.dowjones.repository.dto.BulkUploadResponseDto;
import com.rbc.dowjones.repository.dto.StockDataRequestDto;
import com.rbc.dowjones.repository.dto.StockDataResponseDto;
import com.rbc.dowjones.repository.mapper.StockDataMapper;
import com.rbc.dowjones.repository.model.StockData;
import com.rbc.dowjones.repository.repository.StockDataRepository;
import com.rbc.dowjones.repository.util.CsvParserUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
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
    public BulkUploadResponseDto uploadBulkData(MultipartFile file){

        List<StockData> records=csvParserUtil.parse(file);

        int inserted=0;
        int updated=0;
        int alreadyExists=0;

        for(StockData stockData : records){
            String result = upsertStockData(stockData);

            switch (result){

                case "INSERTED" -> inserted++;
                case "UPDATED" -> updated++;
                case "Already_Exists" ->alreadyExists++;

            }

        }
        BulkUploadResponseDto response=new BulkUploadResponseDto();
        response.setTotalRecords(records.size());
        response.setInsertRecords(inserted);
        response.setUpdatedRecords(updated);

        response.setAlreadyExistingRecords(alreadyExists);

        response.setMessage("Bulk upload processed successfully..");

        return response;
    }

    public String upsertStockData(StockData stockData){

        Optional<StockData> existing=repository.findByStockAndDate(stockData.getStock(),stockData.getDate());

        if(existing.isPresent()) {

            StockData dbData = existing.get();
            if (isSame(dbData,stockData)){
                return "ALREADY_EXISTS";
            }
            dbData.setStock(stockData.getStock());
            dbData.setQuarter(stockData.getQuarter());
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
            return "UPDATED";
        }else{

            repository.save(stockData);
            return "INSERTED";
        }
        }

        private boolean isSame(StockData db, StockData csv){
        return Objects.equals(db.getOpen(), csv.getOpen()) &&
                Objects.equals(db.getStock(),csv.getStock()) &&
                Objects.equals(db.getQuarter(), csv.getQuarter()) &&
                Objects.equals(db.getClose(), csv.getClose()) &&
                Objects.equals(db.getHigh(), csv.getHigh()) &&
                Objects.equals(db.getLow(), csv.getLow()) &&
                Objects.equals(db.getVolume(), csv.getVolume()) &&
                Objects.equals(db.getPercentChangePrice(), csv.getPercentChangePrice()) &&
                Objects.equals(db.getPercentChangeVolumeOverLastWk(), csv.getPercentChangeVolumeOverLastWk()) &&
                Objects.equals(db.getPreviousWeeksVolume(), csv.getPreviousWeeksVolume()) &&
                Objects.equals(db.getNextWeeksOpen(), csv.getNextWeeksOpen()) &&
                Objects.equals(db.getNextWeeksClose(), csv.getNextWeeksClose()) &&
                Objects.equals(db.getPercentChangeNextWeeksPrice(), csv.getPercentChangeNextWeeksPrice()) &&
                Objects.equals(db.getDaysToNextDividend(), csv.getDaysToNextDividend()) &&
                Objects.equals(db.getPercentReturnNextDividend(), csv.getPercentReturnNextDividend());


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

