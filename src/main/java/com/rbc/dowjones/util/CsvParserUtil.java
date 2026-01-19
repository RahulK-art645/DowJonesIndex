package com.rbc.dowjones.util;

import com.rbc.dowjones.model.StockData;
import org.hibernate.annotations.Columns;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

                String[] columns= line.split(",");

                StockData data= new StockData();

                data.setQuarter(Integer.parseInt(columns[0]));
                data.setStock(columns[1]);
                data.setDate(parseDate(columns[2]));

                data.setOpen(parseDouble(columns[3]));
                data.setHigh(parseDouble(columns[4]));
                data.setLow(parseDouble(columns[5]));
                data.setClose(parseDouble(columns[6]));
                data.setVolume(Long.parseLong(columns[7]));

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

    private Double parseDouble(String v) {
        return Double.parseDouble(v.replace("$", ""));
    }

    private Double parseNullableDouble(String v) {
        return (v == null || v.isEmpty()) ? null : parseDouble(v);
    }

    private Long parseNullableLong(String v) {
        return (v == null || v.isEmpty()) ? null : Long.parseLong(v);
    }

    private Integer parseNullableInt(String v) {
        return (v == null || v.isEmpty()) ? null : Integer.parseInt(v);
    }

    private LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        return LocalDate.parse(date, formatter);
    }
}
