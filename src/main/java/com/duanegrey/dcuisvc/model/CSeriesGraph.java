package com.duanegrey.dcuisvc.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, Object> buildOutput(){//Build Final Data Structure for HighCharts
        Map<String, Object> mapResults = new HashMap<>();
        if(null != listSeriesInfo) {
            CSeriesOutput[] seriesOutput = new CSeriesOutput[listSeriesInfo.size()];
            var index=0;
            for (CSeriesInfo seriesInfo : listSeriesInfo) {
                seriesOutput[index] = seriesInfo.getSeriesOutput();
                index++;
            }
            mapResults.put("pointstart", pointStart);
            mapResults.put("dataset", seriesOutput);
        }
        return mapResults;
    };
}
