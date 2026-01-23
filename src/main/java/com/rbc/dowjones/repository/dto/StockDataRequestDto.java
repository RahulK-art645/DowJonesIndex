package com.rbc.dowjones.repository.dto;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;


public class StockDataRequestDto {

    @NotNull(message = "Quarter is mandatory")
    @Min(value=1,message = "Quarter must be between 1 to 4")
    @Max(value = 4, message = "Quarter must be between 1 to 4")
    private Integer quarter;

    @NotBlank(message = "Stock is mandatory")
    @Pattern(regexp = "^[A-Z]{1,10}$",message = "Stock must be 1 to 10 uppercase letters")
    private String stock;


    @NotNull(message = "Date is mandatory")
    @PastOrPresent(message = "Date can not be in the future")
    private LocalDate date;


    @NotNull(message = "Open price is mandatory")
    @DecimalMin(value = "0.01", inclusive = true, message = "Opne price must be greater that zero")
    private BigDecimal open;


    @NotNull(message = "High price is mandatory")
    @DecimalMin(value = "0.01", inclusive = true, message = "High price must be greater than zero")
    private BigDecimal high;


    @NotNull(message = "Low price is mandatory")
    @DecimalMin(value = "0.01",inclusive = true, message = "Low price must be greater than zero")
    private BigDecimal low;


    @NotNull(message = "Closing price is mandatory")
    @DecimalMin(value = "0.01", inclusive = true, message = "Low price must be greater than zero")
    private BigDecimal close;


    @PositiveOrZero(message = "Volume must be zero or positive")
    private Long volume;


    @DecimalMin(value = "-100.00", message = "percent change price can not be less than -100")
    private BigDecimal percentChangePrice;


    @DecimalMin(value = "-100.00", message = "Percent change volume can not be less than -100")
    private BigDecimal percentChangeVolumeOverLastWk;

    @NotNull(message = "previous week volume must be required")
    @PositiveOrZero(message = "Previous week volume must be zero or positive")
    private Long previousWeeksVolume;

    @DecimalMin(value = "0.00", message = "Next week open must be positive")
    private BigDecimal nextWeeksOpen;

    @DecimalMin(value = "0.00", message = "Next week close must be positive")
    private BigDecimal nextWeeksClose;

    @DecimalMin(value = "-100.00", message = "Percent change next week price can not be less than -100")
    private BigDecimal percentChangeNextWeeksPrice;



    @NotNull(message = "Days to Next dividend must be required")
    @PositiveOrZero(message = "Days to next dividend must be zero or positive")
    private Integer daysToNextDividend;

    @DecimalMin(value = "0.00", message = "Percent retun next dividend must be positive")
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
