package com.duanegrey.dcuisvc.model;

import com.duanegrey.dcuisvc.interfaces.IRowTemplate;
import com.duanegrey.dcuisvc.util.CConst;

import java.util.ArrayList;
import java.util.List;

public class CashRowTemplate implements IRowTemplate {
    private List<CRowDesc> listRowDesc = null;

    public CashRowTemplate() {
        listRowDesc = new ArrayList<>();
        listRowDesc.add(new CRowDesc("Net Income", CConst.ROWDATA,"netincome",1000.00));
        listRowDesc.add(new CRowDesc("Depreciation and Amortization", CConst.ROWDATA,"depreciation",1000.00));
    }

    @Override
    public List<CRowDesc> getRowList() {
        return listRowDesc;
    }
}
