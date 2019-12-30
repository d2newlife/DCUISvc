package com.duanegrey.dcuisvc.model;

import com.duanegrey.dcuisvc.util.CConst;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSeriesGraph {
    Object pointStart;
    List<CSeriesInfo> listSeriesInfo;
    String szTitle;

    public CSeriesGraph() {
    }

    public void setMulti(String szTitle, Object pointStart, List<CSeriesInfo> listSeriesInfo) {
        this.pointStart = pointStart;
        this.listSeriesInfo = listSeriesInfo;
        this.szTitle = szTitle;
    }

    public String getTitle() {
        return szTitle;
    }

    public void setTitle(String szTitle) {
        this.szTitle = szTitle;
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
        if(null != listSeriesInfo && listSeriesInfo.size()>0) {
            CSeriesOutput[] seriesOutput = new CSeriesOutput[listSeriesInfo.size()];
            var index=0;
            for (CSeriesInfo seriesInfo : listSeriesInfo) {
                seriesOutput[index] = seriesInfo.getSeriesOutput();
                index++;
            }
            mapResults.put("status", CConst.SUCCESS);
            mapResults.put("title", szTitle);
            mapResults.put("pointstart", pointStart);
            mapResults.put("dataset", seriesOutput);
        }
        else{
            mapResults.put("status", CConst.INTERNALERR);
            mapResults.put("title", "");
            mapResults.put("pointstart", 0);
            mapResults.put("dataset", new Object[0]);
        }
        return mapResults;
    };
}
