package com.duanegrey.dcuisvc.model;

import com.duanegrey.dcuisvc.interfaces.IRowTemplate;
import com.duanegrey.dcuisvc.util.CConst;

import java.util.ArrayList;
import java.util.List;

public class IncomeRowTemplate implements IRowTemplate {

    private List<CRowDesc> listRowDesc = null;

    public IncomeRowTemplate() {
        listRowDesc = new ArrayList<>();
        listRowDesc.add(new CRowDesc("Total Revenue", CConst.ROWDATA,"totalrevenue",1000.00));
        listRowDesc.add(new CRowDesc("Cost of Revenue", CConst.ROWDATA,"costofrevenue",1000.00));
        listRowDesc.add(new CRowDesc("Gross Profit", CConst.ROWDATA,"grossprofit",1000.00));
        listRowDesc.add(new CRowDesc("Operating Expense", CConst.ROWGROUP,"",0.0));
        listRowDesc.add(new CRowDesc("Research and Development", CConst.ROWDATA,"researchanddevelopment",1000.00));
        listRowDesc.add(new CRowDesc("Selling General and Administrative", CConst.ROWDATA,"sellinggeneralandadmin",1000.00));
        listRowDesc.add(new CRowDesc("Total Operating Expense", CConst.ROWDATA,"operatingexpense",1000.00));
    }

    @Override
    public List<CRowDesc> getRowList() {
        return listRowDesc;
    }
}
