package com.rbc.dowjones.repository.util;

import com.rbc.dowjones.repository.exception.CsvProcessingException;
import com.rbc.dowjones.repository.model.StockData;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.tags.EditorAwareTag;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Slf4j
@Component
public class ExcelParserUtil {

    public List<StockData> parse(MultipartFile file) {

        log.info("XLSX PARSING STARTED | file={}", file.getOriginalFilename());

        List<StockData> records=new ArrayList<>();
        Set<String> uniqueKeys=new HashSet<>();
        try(Workbook workbook = new XSSFWorkbook(file.getInputStream())){

            Sheet sheet=workbook.getSheetAt(0);
            int lineNumber=1;

            for (Row row : sheet){
                lineNumber++;

                if (row.getRowNum() == 0){
                    continue;
                }

                StockData data=new StockData();

                data.setQuarter((int) row.getCell(0).getNumericCellValue());

                data.setStock(row.getCell(1).getStringCellValue());
                data.setDate(row.getCell(2).getLocalDateTimeCellValue().toLocalDate());
                data.setOpen(BigDecimal.valueOf(row.getCell(3).getNumericCellValue()));
                data.setHigh(BigDecimal.valueOf(row.getCell(4).getNumericCellValue()));
                data.setLow(BigDecimal.valueOf(row.getCell(5).getNumericCellValue()));
                data.setClose(BigDecimal.valueOf(row.getCell(6).getNumericCellValue()));
                data.setVolume((long) row.getCell(7).getNumericCellValue());

                data.setPercentChangePrice(BigDecimal.valueOf(row.getCell(8).getNumericCellValue()));
                data.setPercentChangeVolumeOverLastWk(BigDecimal.valueOf(row.getCell(9).getNumericCellValue()));
                data.setPreviousWeeksVolume((long)row.getCell(10).getNumericCellValue());
                data.setNextWeeksOpen(BigDecimal.valueOf(row.getCell(11).getNumericCellValue()));
                data.setNextWeeksClose(BigDecimal.valueOf(row.getCell(12).getNumericCellValue()));
                data.setPercentChangeNextWeeksPrice(BigDecimal.valueOf(row.getCell(13).getNumericCellValue()));
                data.setDaysToNextDividend((int)row.getCell(14).getNumericCellValue());
                data.setPercentReturnNextDividend(BigDecimal.valueOf(row.getCell(15).getNumericCellValue()));

                String key=data.getStock() + "_" + data.getDate();

                if(!uniqueKeys.add(key)){

                    throw new CsvProcessingException("Duplicate record in Excel at line " +lineNumber);
                }
                records.add(data);
            }
        }catch (Exception e){

            log.error("XLSX PARSING FAILED", e);
            throw new CsvProcessingException("Failed to parse XLSX file");
        }
        if (records.isEmpty()){
            throw new CsvProcessingException("No Valid records found in excel file");
        }
        log.info("XLSX PARSING COMPLETED | records={}", records.size());
        return records;

    }

    private String getString(Cell cell, String field, int line){

        if (cell == null || cell.getCellType() == CellType.BLANK){
            throw new CsvProcessingException(field +" is required at line "+line);
        }
        return  cell.getStringCellValue().trim();

    }

    private LocalDate getDate( Cell cell, String field, int line){

        if (cell == null || cell.getCellType() == CellType.BLANK){

            throw new CsvProcessingException(field +" is required at line "+line);

        }
        return cell.getLocalDateTimeCellValue().toLocalDate();

    }

    private BigDecimal getDecimal(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return null;
        }
        return BigDecimal.valueOf(cell.getNumericCellValue());
    }

    private Long getLong(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return null;
        }
        return (long) cell.getNumericCellValue();
    }

    private Integer getInt(Cell cell, String field, int line) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            throw new CsvProcessingException(field + " is required at line " + line);
        }
        return (int) cell.getNumericCellValue();
    }

    private Integer getIntNullable(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return null;
        }
        return (int) cell.getNumericCellValue();
    }
}
