package com.duanegrey.dcuisvc.templates;

import com.duanegrey.dcuisvc.interfaces.IGraphTemplate;
import com.duanegrey.dcuisvc.model.CGraphDesc;
import com.duanegrey.dcuisvc.util.CConst;

import java.util.ArrayList;
import java.util.List;

public class CashGraphTemplate implements IGraphTemplate {
    private List<CGraphDesc> listGraphDesc;
    private String szTitle;

    public CashGraphTemplate() {
        szTitle = "Cash Flow Data";
        listGraphDesc = new ArrayList<>();
        listGraphDesc.add(new CGraphDesc("Operating Cash Flow","cashflow", 1000.00, false, null));
        listGraphDesc.add(new CGraphDesc("Net Income","netincome", 1000.00, false, null));
        listGraphDesc.add(new CGraphDesc("Free Cash Flow",null, 1000.00, false, CConst.FCF));
        listGraphDesc.add(new CGraphDesc("Dividends Paid","dividendspaid", 1000.00, true, null));
    }
    public String getTitle(){return szTitle;};

    public List<CGraphDesc> getGraphList(){
        return listGraphDesc;
    }
}
