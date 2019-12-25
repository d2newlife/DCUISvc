package com.duanegrey.dcuisvc.model;

public class CRowDesc {
    private String rowHeader;
    private String rowClassification;
    private String jsonDataKey;
    private double divideBy;

    public CRowDesc(String rowHeader, String rowClassification, String jsonDataKey, Double divideBy) {
        this.rowHeader = rowHeader;
        this.rowClassification = rowClassification;
        this.jsonDataKey = jsonDataKey;
        this.divideBy = divideBy;
    }

    public String getRowHeader() {
        return rowHeader;
    }

    public String getRowClassification() {
        return rowClassification;
    }

    public String getJsonDataKey() {
        return jsonDataKey;
    }

    public double getDivideBy() {
        return divideBy;
    }
}
