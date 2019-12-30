package com.duanegrey.dcuisvc.model;

public class CGraphDesc {
    private String graphLabel;
    private String jsonDataKey;
    private boolean bConvertIfNeg;
    private double divideBy;
    private String calcName;

    public CGraphDesc(String graphLabel, String jsonDataKey, double divideBy, boolean bConvertIfNeg, String calcName) {
        this.graphLabel = graphLabel;
        this.jsonDataKey = jsonDataKey;
        this.bConvertIfNeg = bConvertIfNeg;
        this.calcName = calcName;
        this.divideBy = divideBy;
    }

    public String getGraphLabel() {
        return graphLabel;
    }

    public String getJsonDataKey() {
        return jsonDataKey;
    }

    public boolean isbConvertIfNeg() {
        return bConvertIfNeg;
    }

    public String getCalcName() {
        return calcName;
    }

    public double getDivideBy() {
        return divideBy;
    }
}
