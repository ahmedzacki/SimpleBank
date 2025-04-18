package com.ahmed.simpleBank.controller;

/**
 *
 * DatabaseRequestResult is a Data Transfer Object (DTO) that wraps an integer row count. Without this DTO, if a service
 * service method returns a row count as a plain int, the response body is not a valid JSON:
 *  14
 *  But if the service method returns an instance of this DTO instead, the response is valid JSON:
 *  { "rowCount": 14}
 */
public class DatabaseRequestResult {

    private int rowCount;

    public DatabaseRequestResult() {}

    public DatabaseRequestResult(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }
}
