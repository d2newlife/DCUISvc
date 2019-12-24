package com.duanegrey.dcuisvc.model;

public class CRowDesc {
    private String rowHeader;
    private String rowClassification;
    private String jsonDataKey;
    private Double divideBy;

    public CRowDesc(String rowHeader, String rowClassification, String jsonDataKey, Double divideBy) {
        this.rowHeader = rowHeader;
        this.rowClassification = rowClassification;
        this.jsonDataKey = jsonDataKey;
        this.divideBy = divideBy;
    }
}
