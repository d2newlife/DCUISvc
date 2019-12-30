package com.duanegrey.dcuisvc.templates;

import com.duanegrey.dcuisvc.interfaces.IGraphTemplate;
import com.duanegrey.dcuisvc.model.CGraphDesc;
import com.duanegrey.dcuisvc.model.CRowDesc;

import java.util.List;

public class CashGraphTemplate implements IGraphTemplate {
    private List<CGraphDesc> listGraphDesc = null;

    public List<CGraphDesc> getGraphList(){
        return listGraphDesc;
    };
}
