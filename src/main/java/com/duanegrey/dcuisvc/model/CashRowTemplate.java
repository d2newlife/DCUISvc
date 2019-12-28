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
        listRowDesc.add(new CRowDesc("Changes In Inventories", CConst.ROWDATA,"changesininventories",1000.00));
        listRowDesc.add(new CRowDesc("Operating Cash Flow", CConst.ROWDATA,"cashflow",1000.00));
        listRowDesc.add(new CRowDesc("Total Capital Expenditures", CConst.ROWDATA,"capitalexpenditures",1000.00));
        listRowDesc.add(new CRowDesc("Purchase / Sale Investments", CConst.ROWDATA,"investments",1000.00));
        listRowDesc.add(new CRowDesc("Other Investing Activities", CConst.ROWDATA,"investingactivityother",1000.00));
        listRowDesc.add(new CRowDesc("Net Cash From Investing ..", CConst.ROWDATA,"totalinvestingcashflows",1000.00));
        listRowDesc.add(new CRowDesc("Dividends Paid", CConst.ROWDATA,"dividendspaid",1000.00));
        listRowDesc.add(new CRowDesc("Other Financing Activities", CConst.ROWDATA,"otherfinancingcashflows",1000.00));
        listRowDesc.add(new CRowDesc("Net Cash From Financing ...", CConst.ROWDATA,"cashflowfinancing",1000.00));
    }

    @Override
    public List<CRowDesc> getRowList() {
        return listRowDesc;
    }
}
