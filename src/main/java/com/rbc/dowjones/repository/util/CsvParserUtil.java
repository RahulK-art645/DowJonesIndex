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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CsvParserUtil {

    private static final int EXPECTED_COLUMNS=16;
    private static final List<String> EXPECTED_HEADERS=List.of("quarter","stock", "date",
            "open","high","low","close", "volume",
            "percentChangePrice","percentChangeVolumeOverLastWk",
            "previousWeeksVolume","nextWeeksOpen","nextWeeksClose",
            "percentChangeNextWeeksPrice","daysToNextDividend","percentReturnNextDividend");
    public List<StockData> parse(MultipartFile file){

        List<StockData> records=new ArrayList<>();

        Set<String> csvKeys=new HashSet<>();
        try(BufferedReader reader = new BufferedReader(new
                InputStreamReader(file.getInputStream()))){

            String headerLine=reader.readLine();
            if (headerLine == null){
                throw new CsvProcessingException("CSV file is empty");
            }

            String[] headers=headerLine.split(",", -1);

            if (headers.length < EXPECTED_COLUMNS){
                throw  new CsvProcessingException("Invalid CSV header. Expected 16 columns");
            }
            for (int i=0; i<EXPECTED_HEADERS.size(); i++){
                if (!headers[i].trim().equalsIgnoreCase(EXPECTED_HEADERS.get(i))){
                    throw new CsvProcessingException("Invalid header at position " +i + " Expected: "+EXPECTED_HEADERS.get(i));
                }
            }

            String line;
            int linenumber=1;

            /* ROW PROCESSING*/

            while((line=reader.readLine()) != null){

                linenumber++;
                if (line.trim().isEmpty()){
                    continue; // skip emty lines
                }
                String[] columns= line.split(",", -1);

                if(columns.length < EXPECTED_COLUMNS){
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


                try {
                    data.setDate(LocalDate.parse(columns[2]));

                }catch (Exception e){
                    throw new CsvProcessingException("Invalid date at line"+linenumber);
                }
                data.setOpen(parsePositiveBigDecimal(columns[3],"open", linenumber));
                data.setHigh(parsePositiveBigDecimal(columns[4],"high", linenumber));
                data.setLow(parsePositiveBigDecimal(columns[5],"low", linenumber));
                data.setClose(parsePositiveBigDecimal(columns[6], "close", linenumber));
                data.setVolume(parsePositiveLong(columns[7],"volume",linenumber));

                data.setPercentChangePrice(parseNullableBigDecimal(columns[8]));
                data.setPercentChangeVolumeOverLastWk(parseNullableBigDecimal(columns[9]));
                data.setPreviousWeeksVolume(parseNullableLong(columns[10]));

                data.setNextWeeksOpen(parseNullableBigDecimal(columns[11]));
                data.setNextWeeksClose(parseNullableBigDecimal(columns[12]));
                data.setPercentChangeNextWeeksPrice(parseNullableBigDecimal(columns[13]));

                data.setDaysToNextDividend(parseNullableInt(columns[14]));
                data.setPercentReturnNextDividend(parseNullableBigDecimal(columns[15]));

                String key=data.getStock()+"_"+data.getDate();
                if (!csvKeys.add(key)){
                    throw new CsvProcessingException("Duplicate record in csv stock" + data.getStock()+" on "+data.getDate() + " at line"+linenumber);
                }

                records.add(data);
            }

        } catch (CsvProcessingException e) {
            throw e;

        }catch (Exception e){
            throw new CsvProcessingException("Failed to parse CSV file");
        }
        if (records.isEmpty()){
            throw new CsvProcessingException("No valid records found in CSV file");
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

    private BigDecimal parsePositiveBigDecimal(String v, String field, int line){

        BigDecimal val=parseNullableBigDecimal(v);
        if(val == null || val.compareTo(BigDecimal.ZERO)<=0){

            throw new CsvProcessingException("Invalid" +field +"at line" +line+".Must be > 0");


        }
        return val;

    }

    private Long parsePositiveLong(String v, String field, int line){


        Long val=parseNullableLong(v);
        if (val == null || val<=0){
            throw new CsvProcessingException("Invalid"+field+"at line"+line+ ". Must be >0");
        }
        return val;

    }

}
