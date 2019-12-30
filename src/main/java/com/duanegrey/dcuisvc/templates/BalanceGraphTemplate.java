package com.duanegrey.dcuisvc.templates;

import com.duanegrey.dcuisvc.interfaces.IGraphTemplate;
import com.duanegrey.dcuisvc.model.CGraphDesc;

import java.util.ArrayList;
import java.util.List;

public class BalanceGraphTemplate implements IGraphTemplate {
    private List<CGraphDesc> listGraphDesc;

    public BalanceGraphTemplate() {
        listGraphDesc = new ArrayList<>();
        listGraphDesc.add(new CGraphDesc("Long Term Debt","longtermdebt", 1000.00, false, null));
    }

    public List<CGraphDesc> getGraphList(){
        return listGraphDesc;
    }
}
