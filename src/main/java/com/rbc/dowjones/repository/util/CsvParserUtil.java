package com.rbc.dowjones.repository.util;

import com.rbc.dowjones.repository.model.StockData;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvParserUtil {

    public List<StockData> parse(MultipartFile file){

        List<StockData> records=new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))){

            String line;

            reader.readLine();

            while((line=reader.readLine()) != null){

                if (line.trim().isEmpty()){
                    continue; // skip emty lines
                }
                String[] columns= line.split(",", -1);

                if(columns.length < 16){
                    continue; // skip Invalid/ incomplete
                }

                StockData data= new StockData();

                data.setQuarter(parseNullableInt(columns[0]));
                data.setStock(columns[1]);
                data.setDate(LocalDate.parse(columns[2]));

                data.setOpen(parseNullableDouble(columns[3]));
                data.setHigh(parseNullableDouble(columns[4]));
                data.setLow(parseNullableDouble(columns[5]));
                data.setClose(parseNullableDouble(columns[6]));
                data.setVolume(parseNullableLong(columns[7]));

                data.setPercentChangePrice(parseNullableDouble(columns[8]));
                data.setPercentChangeVolumeOverLastWk(parseNullableDouble(columns[9]));
                data.setPreviousWeeksVolume(parseNullableLong(columns[10]));

                data.setNextWeeksOpen(parseNullableDouble(columns[11]));
                data.setNextWeeksClose(parseNullableDouble(columns[12]));
                data.setPercentChangeNextWeeksPrice(parseNullableDouble(columns[13]));

                data.setDaysToNextDividend(parseNullableInt(columns[14]));
                data.setPercentReturnNextDividend(parseNullableDouble(columns[15]));

                records.add(data);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse CSV file", e);
        }

        return records;
    }

    // ---------- helper methods ----------



    private Double parseNullableDouble(String v) {
       if(v==null) return null;

       String value = v.replace("$","").trim();
       if(value.isEmpty()) return null;

       try{

           return Double.valueOf(value);
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
