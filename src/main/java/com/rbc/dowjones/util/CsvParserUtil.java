package com.rbc.dowjones.util;

import com.rbc.dowjones.model.StockData;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.BufferedInputStream;
import java.io.InputStreamReader;
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

                data.setStock(columns[0]);
                data.setDate(columns[1]);
                data.setOpen(Double.parseDouble(columns[2]));
                data.setClose(Double.parseDouble(columns[5]));
                data.setVolume(Long.parseLong(columns[6]));
                records.add(data);
            }

        }catch(Exception e){

            throw new RuntimeException("Failed to parse CSV file", e);
        }
        return records;

    }
}
