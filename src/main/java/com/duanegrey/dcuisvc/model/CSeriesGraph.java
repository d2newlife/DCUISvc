package com.duanegrey.dcuisvc.model;

import com.duanegrey.dcuisvc.util.CConst;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSeriesGraph {
    Object objStartMonth;
    Object objStartDay;
    Object objStartYear;
    List<CSeriesInfo> listSeriesInfo;
    String szTitle;
    String[] XAxisCategories;

    public CSeriesGraph() {
    }

    public void setTitle(String szTitle) {
        this.szTitle = szTitle;
    }

    public void setPointStart(Object objStartMonth,Object objStartDay,Object objStartYear) {
        this.objStartMonth = objStartMonth;
        this.objStartDay = objStartDay;
        this.objStartYear = objStartYear;
    }

    public void setXAxisCategories(String[] XAxisCategories) {
        this.XAxisCategories = XAxisCategories;
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
            mapResults.put("startmonth", objStartMonth);
            mapResults.put("startday", objStartDay);
            mapResults.put("startyear", objStartYear);
            mapResults.put("dataset", seriesOutput);
            mapResults.put("xcategory",XAxisCategories);
        }
        else{
            mapResults.put("status", CConst.INTERNALERR);
            mapResults.put("title", "");
            mapResults.put("startmonth", 0);
            mapResults.put("startday", 0);
            mapResults.put("startyear", 0);
            mapResults.put("pointstart", 0);
            mapResults.put("dataset", new Object[0]);
            mapResults.put("xcategory",new String[0]);
        }
        return mapResults;
    };
}
