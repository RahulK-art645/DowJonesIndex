package com.rbc.dowjones.repository.dto;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class StockDataRequestDto {


    @PositiveOrZero
    private Integer quarter;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{1,10}$",message = "Stock must be 1 to 10 uppercase letters")
    private String stock;

    @NotNull
    @PastOrPresent
    private LocalDate date;

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true)
    private BigDecimal open;

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true)
    private BigDecimal high;

    @NotNull
    @DecimalMin(value = "0.01",inclusive = true)
    private BigDecimal low;

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true)
    private BigDecimal close;

    @PositiveOrZero
    private Long volume;

    @DecimalMin(value = "-100.00")
    private BigDecimal percentChangePrice;

    @DecimalMin(value = "=100.00")
    private BigDecimal percentChangeVolumeOverLastWk;

    @PositiveOrZero
    private Long previousWeeksVolume;

    @DecimalMin(value = "0.00")
    private BigDecimal nextWeeksOpen;

    @DecimalMin(value = "0.00")
    private BigDecimal nextWeeksClose;

    @DecimalMin(value = "-100.00")
    private BigDecimal percentChangeNextWeeksPrice;



    @PositiveOrZero
    private Integer daysToNextDividend;

    @DecimalMin(value = "0.00")
    private BigDecimal percentReturnNextDividend;


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
