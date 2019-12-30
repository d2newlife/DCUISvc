package com.duanegrey.dcuisvc.templates;

import com.duanegrey.dcuisvc.interfaces.IGraphTemplate;
import com.duanegrey.dcuisvc.model.CGraphDesc;

import java.util.ArrayList;
import java.util.List;

public class IncomeGraphTemplate implements IGraphTemplate {
    private List<CGraphDesc> listGraphDesc;
    private String szTitle;

    public IncomeGraphTemplate() {
        szTitle = "Income Data";
        listGraphDesc = new ArrayList<>();
        listGraphDesc.add(new CGraphDesc("Net Income","netincome", 1000.00, false, null));
        listGraphDesc.add(new CGraphDesc("Gross Profit","grossprofit", 1000.00, false, null));
        listGraphDesc.add(new CGraphDesc("EBIT","ebit", 1000.00, false, null));
    }
    public String getTitle(){return szTitle;};

    public List<CGraphDesc> getGraphList(){
        return listGraphDesc;
    }
}
