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

    @Column(nullable = false)
    private String stock;
    @Column(nullable = false)
    private LocalDate date;
    private Double open;
    private Double close;
    private Long volume;

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
}
