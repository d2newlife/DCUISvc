package com.duanegrey.dcuisvc.model;

import java.util.List;

public class CSeriesGraph {
    Object pointStart;
    List<CSeriesInfo> listSeriesInfo;

    public CSeriesGraph(Object pointStart, List<CSeriesInfo> listSeriesInfo) {
        this.pointStart = pointStart;
        this.listSeriesInfo = listSeriesInfo;
    }

    public Object getPointStart() {
        return pointStart;
    }

    public void setPointStart(Object pointStart) {
        this.pointStart = pointStart;
    }

    public List<CSeriesInfo> getListSeriesInfo() {
        return listSeriesInfo;
    }

    public void setListSeriesInfo(List<CSeriesInfo> listSeriesInfo) {
        this.listSeriesInfo = listSeriesInfo;
    }

    //Ned to Build Array
}
