package com.duanegrey.dcuisvc.model;

import com.duanegrey.dcuisvc.interfaces.IRowTemplate;
import com.duanegrey.dcuisvc.util.CConst;

import java.util.ArrayList;
import java.util.List;

public class BalanceRowTemplate implements IRowTemplate {

    private List<CRowDesc> listRowDesc = null;

    public BalanceRowTemplate() {
        listRowDesc = new ArrayList<>();
        listRowDesc.add(new CRowDesc("Assets", CConst.ROWGROUP,"",0.00));
        listRowDesc.add(new CRowDesc("Cash And Cash Equivalents", CConst.ROWDATA,"currentcash",1000.00));
        listRowDesc.add(new CRowDesc("Short Term Investment", CConst.ROWDATA,"shortterminvestments",1000.00));
        listRowDesc.add(new CRowDesc("Net Receivables", CConst.ROWDATA,"receivables",1000.00));
        listRowDesc.add(new CRowDesc("Inventory", CConst.ROWDATA,"inventory",1000.00));
        listRowDesc.add(new CRowDesc("Other Current Assets", CConst.ROWDATA,"othercurrentassets",1000.00));
        listRowDesc.add(new CRowDesc("Total Current Assets", CConst.ROWDATA,"currentassets",1000.00));
        listRowDesc.add(new CRowDesc("Non-Current Assets", CConst.ROWGROUP,"",0.00));
        listRowDesc.add(new CRowDesc("Property, Plant, ...", CConst.ROWDATA,"propertyplantequipment",1000.00));
        listRowDesc.add(new CRowDesc("Long Term Investments", CConst.ROWDATA,"longterminvestments",1000.00));
        listRowDesc.add(new CRowDesc("Goodwill", CConst.ROWDATA,"goodwill",1000.00));
        listRowDesc.add(new CRowDesc("Intangible Assets", CConst.ROWDATA,"intangibleassets",1000.00));
        listRowDesc.add(new CRowDesc("Other Assets", CConst.ROWDATA,"otherassets",1000.00));
        listRowDesc.add(new CRowDesc("Total Assets", CConst.ROWDATA,"totalassets",1000.00));
        listRowDesc.add(new CRowDesc("Liabilities", CConst.ROWGROUP,"",0.00));
        listRowDesc.add(new CRowDesc("Accounts Payable", CConst.ROWDATA,"accountspayable",1000.00));
        listRowDesc.add(new CRowDesc("Other Current Liabilities", CConst.ROWDATA,"othercurrentliabilities",1000.00));
        listRowDesc.add(new CRowDesc("Total Current Liabilities", CConst.ROWDATA,"totalcurrentliabilities",1000.00));
        listRowDesc.add(new CRowDesc("Long Term Debt", CConst.ROWDATA,"longtermdebt",1000.00));
        listRowDesc.add(new CRowDesc("Total Liabilities", CConst.ROWDATA,"totalliabilities",1000.00));
        listRowDesc.add(new CRowDesc("Stockholders' Equity", CConst.ROWGROUP,"",0.00));
        listRowDesc.add(new CRowDesc("Common Stock", CConst.ROWDATA,"commonstock",1000.00));
        listRowDesc.add(new CRowDesc("Retained Earnings", CConst.ROWDATA,"retainedearnings",1000.00));
        listRowDesc.add(new CRowDesc("Capital Surplus", CConst.ROWDATA,"capitalsurplus",1000.00));
        listRowDesc.add(new CRowDesc("Total Stockholders' Equity", CConst.ROWDATA,"shareholderequity",1000.00));
    }

    @Override
    public List<CRowDesc> getRowList(){
        return listRowDesc;
    };
}
