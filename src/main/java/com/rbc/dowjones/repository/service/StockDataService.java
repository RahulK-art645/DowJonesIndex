package com.rbc.dowjones.repository.service;

import com.rbc.dowjones.repository.dto.BulkUploadResponseDto;
import com.rbc.dowjones.repository.dto.StockDataRequestDto;
import com.rbc.dowjones.repository.dto.StockDataResponseDto;
import com.rbc.dowjones.repository.exception.BadRequestException;
import com.rbc.dowjones.repository.exception.CsvProcessingException;
import com.rbc.dowjones.repository.exception.ResourceNotFoundException;
import com.rbc.dowjones.repository.mapper.StockDataMapper;
import com.rbc.dowjones.repository.model.StockData;
import com.rbc.dowjones.repository.model.UploadedFile;
import com.rbc.dowjones.repository.repository.StockDataRepository;
import com.rbc.dowjones.repository.repository.UploadedFileRepository;
import com.rbc.dowjones.repository.util.CsvParserUtil;
import com.rbc.dowjones.repository.util.FileHashUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StockDataService {

    private final UploadedFileRepository uploadedFileRepository;
    private final StockDataRepository repository;

    private final CsvParserUtil csvParserUtil;

    public StockDataService(StockDataRepository repository, CsvParserUtil csvParserUtil, UploadedFileRepository uploadedFileRepository){

        this.repository=repository;
        this.csvParserUtil=csvParserUtil;

        this.uploadedFileRepository=uploadedFileRepository;
    }

    //Bulk upload
    @Transactional
    public BulkUploadResponseDto uploadBulkData(MultipartFile file){

        if (file == null || file.isEmpty()){
            throw new BadRequestException("Uploaded file is empty");
        }
        String fileHash= FileHashUtil.generateHash(file);
        if (uploadedFileRepository.existsByFileHash(fileHash)){
            throw new BadRequestException("This CSV file was already uploaded. Duplicate upload is not allowed");
        }
        List<StockData> records;
        try{
            records=csvParserUtil.parse(file);

        }catch (Exception e){
            throw new CsvProcessingException("Invalid CSV format or data issue");
        }
        if (records.isEmpty()){
            throw new BadRequestException("No records found in CSV file");
        }


        int inserted=0;
        int updated=0;
        int alreadyExists=0;
        int deleted=0;

        Set<String> csvKeys= new HashSet<>();
        for (StockData r : records){
            String key=r.getStock() + "_" + r.getDate();
            csvKeys.add(key);

            String result=upsertStockData(r);

            if ("INSERTED".equals(result)) {
                inserted++;
            } else if ("UPDATED".equals(result)) {
                updated++;
            } else if ("ALREADY_EXISTS".equals(result)) {
                alreadyExists++;
            }
        }


        String stock= records.get(0).getStock();
        List<StockData> dbRecords=repository.findByStock(stock);
        for (StockData dbData : dbRecords){
            String dbKey = dbData.getStock() + "_" + dbData.getDate();
            if (!csvKeys.contains(dbKey)){
                repository.delete(dbData);
                deleted++;
            }
        }

        UploadedFile uploadedFile=new UploadedFile();
        uploadedFile.setFileHash(fileHash);
        uploadedFileRepository.save(uploadedFile);

        BulkUploadResponseDto response=new BulkUploadResponseDto();
        response.setTotalRecords(records.size());
        response.setInsertRecords(inserted);
        response.setUpdatedRecords(updated);

        response.setAlreadyExistingRecords(alreadyExists);

        response.setDeletedRecords(deleted);
        response.setMessage("Bulk upload processed successfully..");

        return response;
    }

    public String upsertStockData(StockData stockData){

        if(!stockData.getStock().matches("^[A-Z]+$")){
            throw new BadRequestException("Stock ticket must be in uppercase only.");
        }
        //Null check
        if (stockData.getStock() == null || stockData.getDate() == null){
            throw new BadRequestException("Stock, date, and quarter must not be null");
        }

        //Future date check
        if (stockData.getDate().isAfter(LocalDate.now())){

            throw new BadRequestException("Future dates are not allowed");
        }

        //price validation
        if (stockData.getOpen() == null  ||
                stockData.getClose() == null ||
                stockData.getHigh() == null ||
        stockData.getLow() == null){

            throw new BadRequestException("Price field must not be null");
        }

        Optional<StockData> existing=repository.findByStockAndDate(stockData.getStock(),
                stockData.getDate());


        if(existing.isPresent()) {

            StockData dbData = existing.get();

            if (isSame(dbData,stockData)){
                return "ALREADY_EXISTS";
            }
            copyUpdatableField(dbData, stockData);

            repository.save(dbData);
            return "UPDATED";
        }

            repository.save(stockData);
            return "INSERTED";

        }
        private void  copyUpdatableField(StockData dbData, StockData newData){

            dbData.setQuarter(newData.getQuarter());
            dbData.setOpen(newData.getOpen());
            dbData.setHigh(newData.getHigh());
            dbData.setLow(newData.getLow());
            dbData.setClose(newData.getClose());
            dbData.setVolume(newData.getVolume());
            dbData.setPercentChangePrice(newData.getPercentChangePrice());
            dbData.setPercentChangeVolumeOverLastWk(newData.getPercentChangeVolumeOverLastWk());
            dbData.setPreviousWeeksVolume(newData.getPreviousWeeksVolume());
            dbData.setNextWeeksOpen(newData.getNextWeeksOpen());
            dbData.setNextWeeksClose(newData.getNextWeeksClose());
            dbData.setPercentChangeNextWeeksPrice(newData.getPercentChangeNextWeeksPrice());
            dbData.setDaysToNextDividend(newData.getDaysToNextDividend());
            dbData.setPercentReturnNextDividend(newData.getPercentReturnNextDividend());
        }

        private boolean isSame(StockData db, StockData csv){
        return Objects.equals(db.getOpen(), csv.getOpen()) &&
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

        if (stock == null || stock.isBlank()){
            throw new BadRequestException("Please enter stock ticker");
        }
        if (!stock.matches("^[A-Z]+$")){
            throw new BadRequestException("Sorry, only uppercase letters allowed. Lowercase or numbers not allowed");
        }
        List<StockData> entities=repository.findByStock(stock);
        if (entities.isEmpty()){
            throw new ResourceNotFoundException("Stock not found for ticker :"+ stock);
        }
        return entities.stream().map(StockDataMapper::toResponseDto).toList();
    }

    public StockDataResponseDto addRecord(StockDataRequestDto requestDto){


        if (repository.findByStockAndDate(requestDto.getStock(), requestDto.getDate()).isPresent()){
            throw new BadRequestException("Stock data already exists for given stock and date");
        }
        StockData entity=StockDataMapper.toEntity(requestDto);
        StockData saved=repository.save(entity);
        return StockDataMapper.toResponseDto(saved);

    }

    /* Update by ID */
    public StockDataResponseDto updateById(String  id,StockDataRequestDto requestDto){

        Long stockId;
        try{
            stockId=Long.parseLong(id);
        }catch (NumberFormatException ex){
            throw new BadRequestException("Sorry, character are not allowed here. Please enter a valid number");
        }
        if (stockId <=0){
            throw new BadRequestException("Invalid ID");
        }
        //Fetch existing
        StockData dbData=repository.findById(stockId).orElseThrow(()->
                new ResourceNotFoundException("Stock data not found"));

        //Unique field should not change
        if (!dbData.getStock().equals(requestDto.getStock())){
            throw new BadRequestException("Stock field is immutable and cannot be changed");
        }
        if(!dbData.getDate().equals(requestDto.getDate())){
            throw new BadRequestException("Date field is immutable and cannot be changed");
        }
        // price validation
        if (requestDto.getOpen().compareTo(BigDecimal.ZERO) <=0 || requestDto.getClose()
                .compareTo(BigDecimal.ZERO) <=0 ||requestDto.getHigh().
                compareTo(BigDecimal.ZERO) <=0 ||

        requestDto.getLow().compareTo(BigDecimal.ZERO) <=0){

            throw new BadRequestException("price values must be greater than zero");
        }

        copyUpdatableField(dbData,StockDataMapper.toEntity(requestDto));
        StockData saved=repository.save(dbData);

        return StockDataMapper.toResponseDto(saved);

    }

    public void deleteById(Long id){

        if (id == null || id <= 0){
            throw new BadRequestException("Invalid ID");
        }
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Stock data not found");
        }
        repository.deleteById(id);
    }

    public StockDataResponseDto getById(String id){

        Long stockId;

        try{
            stockId=Long.parseLong(id);
        }catch (NumberFormatException ex){
            throw new BadRequestException("Sorry, character are not allowed here. Please enter a valid number");
        }
        if (stockId <=0){
            throw new BadRequestException("Invalid id format");
        }
        StockData data=repository.findById(stockId).orElseThrow(()->
                new ResourceNotFoundException("Stock data not found"));

      return  StockDataMapper.toResponseDto(data);


    }

}

