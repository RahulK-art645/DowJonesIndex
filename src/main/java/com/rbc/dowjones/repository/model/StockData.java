package com.rbc.dowjones.repository.model;

import javax.persistence.*;
import java.time.LocalDate;

import java.math.BigDecimal;
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
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private Long volume;

    private BigDecimal percentChangePrice;
    private BigDecimal percentChangeVolumeOverLastWk;
    private Long previousWeeksVolume;
    private BigDecimal nextWeeksOpen;
    private BigDecimal nextWeeksClose;
    private BigDecimal percentChangeNextWeeksPrice;

    private Integer daysToNextDividend;
    private BigDecimal percentReturnNextDividend;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
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

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getPercentChangePrice() {
        return percentChangePrice;
    }

    public void setPercentChangePrice(BigDecimal percentChangePrice) {
        this.percentChangePrice = percentChangePrice;
    }

    public BigDecimal getPercentChangeVolumeOverLastWk() {
        return percentChangeVolumeOverLastWk;
    }

    public void setPercentChangeVolumeOverLastWk(BigDecimal percentChangeVolumeOverLastWk) {
        this.percentChangeVolumeOverLastWk = percentChangeVolumeOverLastWk;
    }

    public Long getPreviousWeeksVolume() {
        return previousWeeksVolume;
    }

    public void setPreviousWeeksVolume(Long previousWeeksVolume) {
        this.previousWeeksVolume = previousWeeksVolume;
    }

    public BigDecimal getNextWeeksOpen() {
        return nextWeeksOpen;
    }

    public void setNextWeeksOpen(BigDecimal nextWeeksOpen) {
        this.nextWeeksOpen = nextWeeksOpen;
    }

    public BigDecimal getNextWeeksClose() {
        return nextWeeksClose;
    }

    public void setNextWeeksClose(BigDecimal nextWeeksClose) {
        this.nextWeeksClose = nextWeeksClose;
    }

    public BigDecimal getPercentChangeNextWeeksPrice() {
        return percentChangeNextWeeksPrice;
    }

    public void setPercentChangeNextWeeksPrice(BigDecimal percentChangeNextWeeksPrice) {
        this.percentChangeNextWeeksPrice = percentChangeNextWeeksPrice;
    }

    public Integer getDaysToNextDividend() {
        return daysToNextDividend;
    }

    public void setDaysToNextDividend(Integer daysToNextDividend) {
        this.daysToNextDividend = daysToNextDividend;
    }

    public BigDecimal getPercentReturnNextDividend() {
        return percentReturnNextDividend;
    }

    public void setPercentReturnNextDividend(BigDecimal percentReturnNextDividend) {
        this.percentReturnNextDividend = percentReturnNextDividend;
    }
}
