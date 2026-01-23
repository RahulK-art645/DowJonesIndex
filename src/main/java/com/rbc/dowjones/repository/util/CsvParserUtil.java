package com.rbc.dowjones.repository.util;

import com.rbc.dowjones.repository.exception.CsvProcessingException;
import com.rbc.dowjones.repository.model.StockData;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvParserUtil {

    public List<StockData> parse(MultipartFile file){

        List<StockData> records=new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))){

            String line;
            int linenumber=1;

            reader.readLine();

            while((line=reader.readLine()) != null){

                linenumber++;
                if (line.trim().isEmpty()){
                    continue; // skip emty lines
                }
                String[] columns= line.split(",", -1);

                if(columns.length < 16){
                    throw new CsvProcessingException("Invalida column count at line"+linenumber);// skip Invalid/ incomplete
                }

                StockData data= new StockData();


                data.setQuarter(parseNullableInt(columns[0]));
                String stock=columns[1].trim();
                if (stock.isEmpty()){
                    throw new CsvProcessingException("Stock ticker is missing at line"+ linenumber);
                }

                if (!stock.matches("^[A-Z]+$")){
                    throw new CsvProcessingException("Invalid stock ticker (only uppercase allowed), Number not allowed at line"+linenumber);
                }
                data.setStock(stock);

                data.setDate(LocalDate.parse(columns[2]));

                data.setOpen(parseNullableBigDecimal(columns[3]));
                data.setHigh(parseNullableBigDecimal(columns[4]));
                data.setLow(parseNullableBigDecimal(columns[5]));
                data.setClose(parseNullableBigDecimal(columns[6]));
                data.setVolume(parseNullableLong(columns[7]));

                data.setPercentChangePrice(parseNullableBigDecimal(columns[8]));
                data.setPercentChangeVolumeOverLastWk(parseNullableBigDecimal(columns[9]));
                data.setPreviousWeeksVolume(parseNullableLong(columns[10]));

                data.setNextWeeksOpen(parseNullableBigDecimal(columns[11]));
                data.setNextWeeksClose(parseNullableBigDecimal(columns[12]));
                data.setPercentChangeNextWeeksPrice(parseNullableBigDecimal(columns[13]));

                data.setDaysToNextDividend(parseNullableInt(columns[14]));
                data.setPercentReturnNextDividend(parseNullableBigDecimal(columns[15]));

                records.add(data);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse CSV file", e);
        }

        return records;
    }

    // ---------- helper methods ----------



    private BigDecimal parseNullableBigDecimal(String v) {
       if(v==null) return null;

       String value = v.replace("$","").trim();
       if(value.isEmpty()) return null;

       try{

           return new BigDecimal(value);
       }catch(NumberFormatException e){
           return null;
       }
    }

    private Long parseNullableLong(String v) {
        if (v==null) return null;

        String value=v.trim();
        if(value.isEmpty()) return null;

        try{
            return Long.valueOf(value);
        }catch (NumberFormatException e)
        {
            return null;
        }
    }

    private Integer parseNullableInt(String v) {
        if (v == null) return null;

        String value = v.trim();

        if(value.isEmpty()) return null;

        try{

            return Integer.valueOf(value);
        }catch(NumberFormatException e){
            return null;
        }
    }


}
