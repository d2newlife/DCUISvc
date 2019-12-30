package com.duanegrey.dcuisvc.templates;

import com.duanegrey.dcuisvc.interfaces.IGraphTemplate;
import com.duanegrey.dcuisvc.model.CGraphDesc;

import java.util.ArrayList;
import java.util.List;

public class BalanceGraphTemplate implements IGraphTemplate {
    private List<CGraphDesc> listGraphDesc;
    private String szTitle;

    public BalanceGraphTemplate() {
        szTitle = "Balance Sheet Data";
        listGraphDesc = new ArrayList<>();
        listGraphDesc.add(new CGraphDesc("Long Term Debt","longtermdebt", 1000.00, false, null));
        listGraphDesc.add(new CGraphDesc("Cash And Cash Equivalents","currentcash", 1000.00, false, null));
        listGraphDesc.add(new CGraphDesc("Total Liabilities","totalliabilities", 1000.00, false, null));
        listGraphDesc.add(new CGraphDesc("Total Stockholders' Equity","shareholderequity", 1000.00, false, null));
    }

    public String getTitle(){return szTitle;};

    public List<CGraphDesc> getGraphList(){
        return listGraphDesc;
    }
}
