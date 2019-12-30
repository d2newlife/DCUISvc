package com.duanegrey.dcuisvc.model;

import java.util.List;

public class CSeriesInfo {
    private String szLabel;
    private List<Object> objDataList;

    public CSeriesInfo(String szLabel, List<Object> objDataList) {
        this.szLabel = szLabel;
        this.objDataList = objDataList;
    }

    public String getSzLabel() {
        return szLabel;
    }

    public List<Object> getObjDataList() {
        return objDataList;
    }

    public Object[] getObjDataListAsArray(){
        return objDataList.toArray();
    }
}
