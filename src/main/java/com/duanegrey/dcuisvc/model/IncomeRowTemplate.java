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

        listRowDesc.add(new CRowDesc("Operating Income / Loss", CConst.ROWDATA,"operatingincome",1000.00));
        listRowDesc.add(new CRowDesc("Other Income/Expenses Net", CConst.ROWDATA,"otherincomeexpensenet",1000.00));
        listRowDesc.add(new CRowDesc("Interest Expense On Debt", CConst.ROWDATA,"interestincome",1000.00));
        listRowDesc.add(new CRowDesc("Pre-Tax Income", CConst.ROWDATA,"pretaxincome",1000.00));
        listRowDesc.add(new CRowDesc("Income Tax Expenses", CConst.ROWDATA,"incometax",1000.00));
        listRowDesc.add(new CRowDesc("Net Income", CConst.ROWDATA,"netincome",1000.00));
        listRowDesc.add(new CRowDesc("EBIT", CConst.ROWDATA,"ebit",1000.00));
    }

    @Override
    public List<CRowDesc> getRowList() {
        return listRowDesc;
    }
}
