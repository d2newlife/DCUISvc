package com.duanegrey.dcuisvc.model;

import java.util.List;

public class CSeriesInfo {
    private String szLabel;
    private Object[] objDataArray;

    public CSeriesInfo(String szLabel, Object[] objDataArray) {
        this.szLabel = szLabel;
        this.objDataArray = objDataArray;
    }

    public String getSzLabel() {
        return szLabel;
    }

    public  Object[] getObjDataList() {
        return objDataArray;
    }

    public CSeriesOutput getSeriesOutput(){
        return new CSeriesOutput(szLabel, objDataArray);
    }
}
