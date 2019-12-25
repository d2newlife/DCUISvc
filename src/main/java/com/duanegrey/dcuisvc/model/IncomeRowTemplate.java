package com.duanegrey.dcuisvc.model;

import com.duanegrey.dcuisvc.interfaces.IRowTemplate;

import java.util.ArrayList;
import java.util.List;

public class IncomeRowTemplate implements IRowTemplate {

    private List<CRowDesc> listRowDesc = null;

    public IncomeRowTemplate() {
        listRowDesc = new ArrayList<>();
    }

    @Override
    public List<CRowDesc> getRowList() {
        return null;
    }
}
