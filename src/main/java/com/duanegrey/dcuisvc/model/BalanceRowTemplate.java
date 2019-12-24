package com.duanegrey.dcuisvc.model;

import com.duanegrey.dcuisvc.interfaces.IRowTemplate;
import com.duanegrey.dcuisvc.util.CConst;

import java.util.ArrayList;
import java.util.List;

public class BalanceRowTemplate implements IRowTemplate {

    List<CRowDesc> listRowDesc = null;

    public BalanceRowTemplate() {
        listRowDesc = new ArrayList<>();
        listRowDesc.add(new CRowDesc("Assets", CConst.ROWGROUP,"",0.00));
        listRowDesc.add(new CRowDesc("Cash And Cash Equivalents", CConst.ROWDATA,"currentcash",1000.00));
        listRowDesc.add(new CRowDesc("Short Term Investment", CConst.ROWDATA,"shortterminvestments",1000.00));
        listRowDesc.add(new CRowDesc("Long Term Investments", CConst.ROWDATA,"longterminvestments",1000.00));
        listRowDesc.add(new CRowDesc("Net Receivables", CConst.ROWDATA,"receivables",1000.00));
    }

    public List<CRowDesc> getRowList(){
        return listRowDesc;
    };
}
