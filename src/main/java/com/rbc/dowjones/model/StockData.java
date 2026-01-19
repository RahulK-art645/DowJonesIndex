package com.rbc.dowjones.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="stock_data",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"stock", "date"})})
public class StockData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Integer quarter;

    @Column(nullable = false)
    private String stock;
    @Column(nullable = false)
    private LocalDate date;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Long volume;

    private Double percentChangePrice;
    private Double percentChangeVolumeOverLastWk;
    private Long previousWeeksVolume;
    private Double nextWeeksOpen;
    private Double nextWeeksClose;
    private Double percentChangeNextWeeksPrice;

    private Integer daysToNextDividend;
    private Double percentReturnNextDividend;

    public long getId() {
        return id;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getPercentChangePrice() {
        return percentChangePrice;
    }

    public void setPercentChangePrice(Double percentChangePrice) {
        this.percentChangePrice = percentChangePrice;
    }

    public Double getPercentChangeVolumeOverLastWk() {
        return percentChangeVolumeOverLastWk;
    }

    public void setPercentChangeVolumeOverLastWk(Double percentChangeVolumeOverLastWk) {
        this.percentChangeVolumeOverLastWk = percentChangeVolumeOverLastWk;
    }

    public Long getPreviousWeeksVolume() {
        return previousWeeksVolume;
    }

    public void setPreviousWeeksVolume(Long previousWeeksVolume) {
        this.previousWeeksVolume = previousWeeksVolume;
    }

    public Double getNextWeeksOpen() {
        return nextWeeksOpen;
    }

    public void setNextWeeksOpen(Double nextWeeksOpen) {
        this.nextWeeksOpen = nextWeeksOpen;
    }

    public Double getNextWeeksClose() {
        return nextWeeksClose;
    }

    public void setNextWeeksClose(Double nextWeeksClose) {
        this.nextWeeksClose = nextWeeksClose;
    }

    public Double getPercentChangeNextWeeksPrice() {
        return percentChangeNextWeeksPrice;
    }

    public void setPercentChangeNextWeeksPrice(Double percentChangeNextWeeksPrice) {
        this.percentChangeNextWeeksPrice = percentChangeNextWeeksPrice;
    }

    public Integer getDaysToNextDividend() {
        return daysToNextDividend;
    }

    public void setDaysToNextDividend(Integer daysToNextDividend) {
        this.daysToNextDividend = daysToNextDividend;
    }

    public Double getPercentReturnNextDividend() {
        return percentReturnNextDividend;
    }

    public void setPercentReturnNextDividend(Double percentReturnNextDividend) {
        this.percentReturnNextDividend = percentReturnNextDividend;
    }
}
