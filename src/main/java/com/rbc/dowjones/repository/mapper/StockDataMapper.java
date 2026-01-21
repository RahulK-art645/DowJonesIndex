package com.rbc.dowjones.repository.mapper;

import com.rbc.dowjones.repository.dto.StockDataRequestDto;
import com.rbc.dowjones.repository.dto.StockDataResponseDto;
import com.rbc.dowjones.repository.model.StockData;

public class StockDataMapper {

    public static StockDataResponseDto toResponseDto(StockData entity) {

        if(entity==null){
            return null;
        }
        StockDataResponseDto dto = new StockDataResponseDto();

        dto.setQuarter(entity.getQuarter());
        dto.setStock(entity.getStock());
        dto.setDate(entity.getDate());

        dto.setOpen(entity.getOpen());
        dto.setHigh(entity.getHigh());
        dto.setLow(entity.getLow());
        dto.setClose(entity.getClose());
        dto.setVolume(entity.getVolume());

        dto.setPercentChangePrice(entity.getPercentChangePrice());
        dto.setPercentChangeVolumeOverLastWk(entity.getPercentChangeVolumeOverLastWk());

        dto.setNextWeeksOpen(entity.getNextWeeksOpen());
        dto.setNextWeeksClose(entity.getNextWeeksClose());
        dto.setPercentChangeNextWeeksPrice(entity.getPercentChangeNextWeeksPrice());

        dto.setDaysToNextDividend(entity.getDaysToNextDividend());
        dto.setPercentReturnNextDividend(entity.getPercentReturnNextDividend());

        return dto;
    }

    public static StockData toEntity(StockDataRequestDto dto) {

        StockData entity = new StockData();

        entity.setQuarter(dto.getQuarter());
        entity.setStock(dto.getStock());
        entity.setDate(dto.getDate());
        entity.setOpen(dto.getOpen());
        entity.setHigh(dto.getHigh());
        entity.setLow(dto.getLow());
        entity.setClose(dto.getClose());
        entity.setVolume(dto.getVolume());

        entity.setPercentChangePrice(dto.getPercentChangePrice());
        entity.setPercentChangeVolumeOverLastWk(dto.getPercentChangeVolumeOverLastWk());
        entity.setPreviousWeeksVolume(dto.getPreviousWeeksVolume());
        entity.setNextWeeksOpen(dto.getNextWeeksOpen());
        entity.setNextWeeksClose(dto.getNextWeeksClose());
        entity.setPercentChangeNextWeeksPrice(dto.getPercentChangeNextWeeksPrice());
        entity.setDaysToNextDividend(dto.getDaysToNextDividend());
        entity.setPercentReturnNextDividend(dto.getPercentReturnNextDividend());

        return entity;
    }
}
