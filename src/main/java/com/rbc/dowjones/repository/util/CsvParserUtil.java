package com.rbc.dowjones.repository.util;

import com.rbc.dowjones.repository.exception.CsvProcessingException;
import com.rbc.dowjones.repository.model.StockData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Component
public class CsvParserUtil {

    private static final int EXPECTED_COLUMNS=16;
    private static final List<String> EXPECTED_HEADERS=List.of(
            "quarter","stock",
            "date",
            "open","high","low","close", "volume",
            "percent_change_price","percent_change_volume_over_last_wk",
            "previous_weeks_volume","next_weeks_open","next_weeks_close",
            "percent_change_next_weeks_price","days_to_next_dividend","percent_return_next_dividend");
   private static final DateTimeFormatter ISO_DATE= DateTimeFormatter.ISO_LOCAL_DATE;
   private static final DateTimeFormatter DATE_FORMAT=DateTimeFormatter.ofPattern("M/d/yyy");

   public List<StockData> parse(MultipartFile file){

       log.info("CSV PARSING STARTED | file={}", file.getOriginalFilename());

        List<StockData> records=new ArrayList<>();

        Set<String> csvKeys=new HashSet<>();
        try(BufferedReader reader = new BufferedReader(new
                InputStreamReader(file.getInputStream()));

            CSVParser csvParser=new CSVParser(reader, CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).setTrim(true).build())){
                Map<String, Integer> headerMap=csvParser.getHeaderMap();

                if (headerMap.size() < EXPECTED_COLUMNS){
                    throw new CsvProcessingException("Invalid Csv header. Expected 16 columns but found"+ headerMap.size());

                }

                for(String expected : EXPECTED_HEADERS){
                    if (!headerMap.containsKey(expected)){
                        log.error("CSV HEADER MISSING | header={}",expected);
                        throw new CsvProcessingException("Missing required header" + expected);
                    }
                }


            int linenumber=1;
            for(CSVRecord record : csvParser){
                linenumber++;

                log.debug("Parsing CSV row | line={}", linenumber);
                if (record == null || record.size() ==0){
                    continue;
                }

                if (record.size() != EXPECTED_COLUMNS){
                    throw new CsvProcessingException("Invalid columns count at line"+linenumber+". Expected "+ EXPECTED_COLUMNS + " but found " + record.size());

                }
                StockData data =new StockData();

                data.setQuarter(parseNullableInt(record.get("quarter"), linenumber));


                data.setStock(parseStock(record.get("stock"), linenumber));
                data.setDate(parseDate(record.get("date"), linenumber));



                data.setOpen(parsePositiveBigDecimal(record.get("open"),"open", linenumber));
                data.setHigh(parsePositiveBigDecimal(record.get("high"), "high",linenumber));
                data.setLow(parsePositiveBigDecimal(record.get("low"),"low", linenumber));
                data.setClose(parsePositiveBigDecimal(record.get("close"), "close",linenumber));
                data.setVolume(parsePositiveLong(record.get("volume"),"volume",linenumber));

                data.setPercentChangePrice(parseNullableBigDecimal(record.get("percent_change_price"),linenumber));
                data.setPercentChangeVolumeOverLastWk(parseNullableBigDecimal(record.get("percent_change_volume_over_last_wk"), linenumber));
                data.setPreviousWeeksVolume(parseNullableLong(record.get("previous_weeks_volume"), linenumber));

                data.setNextWeeksOpen(parseNullableBigDecimal(record.get("next_weeks_open"), linenumber));
                data.setNextWeeksClose(parseNullableBigDecimal(record.get("next_weeks_close"), linenumber));
                data.setPercentChangeNextWeeksPrice(parseNullableBigDecimal(record.get("percent_change_next_weeks_price"), linenumber));

                data.setDaysToNextDividend(parseNullableInt(record.get("days_to_next_dividend"), linenumber));
                data.setPercentReturnNextDividend(parseNullableBigDecimal(record.get("percent_return_next_dividend"),linenumber));

                String key=data.getStock()+"_"+data.getDate();
                if (!csvKeys.add(key)){

                    log.error("DUPLICATE CSV RECORD | stock={} | date={} | line={}", data.getStock(), data.getDate(), linenumber);
                    throw new CsvProcessingException("Duplicate record in csv stock" + data.getStock()+" on date "+data.getDate() + " at line"+linenumber);
                }

                records.add(data);
            }

        } catch (CsvProcessingException e) {
            log.error("CSV PARSING FAILED | file={} | reason={}",file.getOriginalFilename(), e.getMessage());
            throw e;

        }catch (Exception e){
            log.error("CSV PARSING FAILED | file={}", file.getOriginalFilename(),e);
            throw new CsvProcessingException("Failed to parse CSV file");
        }
        if (records.isEmpty()){
            log.warn("CSV PARSING COMPLETED | No valid records found | file={}", file.getOriginalFilename());
            throw new CsvProcessingException("No valid records found in CSV file");
        }

        log.info("CSV PARSING COMPLETED | totalRecords={}", records.size());
        return records;
    }

    private String parseStock(String v, int line){

       if (v==null || v.trim().isEmpty()){
           throw new CsvProcessingException("Stock ticker missing at line"+line);
       }
       if (!v.matches("^[A-Z]+$")){
           throw new CsvProcessingException("Invalid stock ticker at line"+line+ "(Only uppercase letter allowed)");
       }
return v.trim();
    }

    private LocalDate parseDate(String v, int line){

       try{
           return LocalDate.parse(v.trim(),DATE_FORMAT);
       }catch (Exception e){
           throw new CsvProcessingException("Invalid date at line"+line);
       }
    }

    // ---------- helper methods ----------

    private BigDecimal parsePositiveBigDecimal(String v, String field, int line) {

        if (v == null || v.trim().isEmpty()) {
            throw new CsvProcessingException(field + " is empty at line " + line);
        }
        try {
            BigDecimal val = new BigDecimal(v.replace("$", "").trim());
            if (val.compareTo(BigDecimal.ZERO) <= 0) {
                throw new CsvProcessingException(field + "must be >0 at line" + line);
            }
            return val;
        } catch (CsvProcessingException e) {
            throw e;
        } catch (Exception e) {
            throw new CsvProcessingException("Invalid " + field + "at line " + line);
        }
    }

        private Long parsePositiveLong(String v, String field,int line){

            if (v == null || v.trim().isEmpty()) {
                throw new CsvProcessingException(field + " is empty at line " + line);
            }

            try {
                Long val = Long.valueOf(v.trim());
                if (val <= 0) {
                    throw new CsvProcessingException(field + "must be >0 at line" + line);
                }
                return val;

            } catch (CsvProcessingException e) {
                throw e;
            } catch (Exception e) {
                throw new CsvProcessingException("Invalid " + field + " value at line" + line);
            }
        }



    private BigDecimal parseNullableBigDecimal(String v, int line) {
       if(v==null || v.trim().isEmpty()) {
           return null;
       }

       try{
           return new BigDecimal(v.replace("$","").trim());
       }catch(Exception e){
           throw new CsvProcessingException("Invalid numeric value at line"+line);
       }
    }

    private Long parseNullableLong(String v, int line) {
        if (v==null || v.trim().isEmpty()) {
            return null;
        }
            try{
                return Long.valueOf(v.trim());
            } catch (Exception e){
                throw new CsvProcessingException("Invalid long value at line"+line);
            }

        }


    private Integer parseNullableInt(String v, int line) {
        if (v == null || v.trim().isEmpty()){

            return null;
        }

        try{

            return Integer.valueOf(v.trim());
        }catch(Exception e){

           throw new CsvProcessingException("Invalid interger at line"+ line);
        }
    }




    }




