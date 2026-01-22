package com.rbc.dowjones.repository.dto;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Reqest DTO for adding or updating stock data")
public class StockDataRequestDto {


    @Schema(example = "1", description = "Financial quarter (1 to 5)", allowableValues = {"1","2","3", "4", "5"})
    @NotNull(message = "Quarter is mandatory")
    @Min(value=1,message = "Quarter must be between 1 to 5")
    @Max(value = 5, message = "Quarter must be between 1 to 5")
    private Integer quarter;

    @Schema(example = "AAPL", description = "Stock ticker symbol (1 to 10 uppercase letters)")
    @NotBlank(message = "Stock is mandatory")
    @Pattern(regexp = "^[A-Z]{1,10}$",message = "Stock must be 1 to 10 uppercase letters")
    private String stock;

    @Schema(example = "2024-01-01", description = "Trading date")
    @NotNull(message = "Date is mandatory")
    @PastOrPresent(message = "Date can not be in the future")
    private LocalDate date;

    @Schema(example = "130.75", description = "Opening price")
    @NotNull(message = "Open price is mandatory")
    @DecimalMin(value = "0.01", inclusive = true, message = "Opne price must be greater that zero")
    private BigDecimal open;

    @Schema(example = "130.75", description = "Lowest price")
    @NotNull(message = "High price is mandatory")
    @DecimalMin(value = "0.01", inclusive = true, message = "High price must be greater than zero")
    private BigDecimal high;

    @Schema(example = "115.25", description = "Lowest price")
    @NotNull(message = "Low price is mandatory")
    @DecimalMin(value = "0.01",inclusive = true, message = "Low price must be greater than zero")
    private BigDecimal low;

    @Schema(example = "125.40", description = "Closing price")
    @NotNull(message = "Closing price is mandatory")
    @DecimalMin(value = "0.01", inclusive = true, message = "Low price must be greater than zero")
    private BigDecimal close;

    @Schema(example = "1000000", description = "Trading volume")
    @PositiveOrZero(message = "Volume must be zero or positive")
    private Long volume;

    @Schema(example = "-2.45", description = "Percent change in price")
    @DecimalMin(value = "-100.00", message = "percent change price can not be less than -100")
    private BigDecimal percentChangePrice;

    @Schema(example = "5.60", description ="Percent change volume over last week")
    @DecimalMin(value = "-100.00", message = "Percent change volume can not be less than -100")
    private BigDecimal percentChangeVolumeOverLastWk;

    @Schema(example = "900000", description = "Previous week's volume")
    @PositiveOrZero(message = "Previous week volume must be zero or positive")
    private Long previousWeeksVolume;

    @Schema(example = "128.00", description = "Next week's open price")
    @DecimalMin(value = "0.00", message = "Next week open must be positive")
    private BigDecimal nextWeeksOpen;

    @Schema(example = "135.00", description = "Next week's close price")
    @DecimalMin(value = "0.00", message = "Next week close must be positive")
    private BigDecimal nextWeeksClose;

    @Schema(example = "4.25", description = "Percent change next week's price")
    @DecimalMin(value = "-100.00", message = "Percent change next week price can not be less than -100")
    private BigDecimal percentChangeNextWeeksPrice;



    @Schema(example = "30", description = "Days to next dividend")
    @PositiveOrZero(message = "Days to next dividend must be zero or positive")
    private Integer daysToNextDividend;

    @Schema(example = "1.25", description = "Percent retun next dividend")
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
