package com.rbc.dowjones.repository.dto;

public class BulkUploadResponseDto {

    private int totalRecords;
    private int insertRecords;
    private int updatedRecords;
    private int alreadyExistingRecords;

    private String message;

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getInsertRecords() {
        return insertRecords;
    }

    public void setInsertRecords(int insertRecords) {
        this.insertRecords = insertRecords;
    }

    public int getUpdatedRecords() {
        return updatedRecords;
    }

    public void setUpdatedRecords(int updatedRecords) {
        this.updatedRecords = updatedRecords;
    }

    public int getAlreadyExistingRecords() {
        return alreadyExistingRecords;
    }

    public void setAlreadyExistingRecords(int alreadyExistingRecords) {
        this.alreadyExistingRecords = alreadyExistingRecords;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
