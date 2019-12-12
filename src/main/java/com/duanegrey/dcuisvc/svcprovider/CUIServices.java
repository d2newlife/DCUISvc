package com.duanegrey.dcuisvc.svcprovider;

import com.duanegrey.dcuisvc.config.CAppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class CUIServices {
    private final CAppProperties appProperties;

    @Autowired
    public CUIServices(CAppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/divclarity/v1/chart/{szSymbol}/prices2/range/{szRange}") //Get List of Companies by Sector
    ResponseEntity getCompPricesByRange2(@PathVariable String szSymbol, @PathVariable String szRange, @RequestHeader HttpHeaders requestHeaders) {
        String szCorelID = genLib.getHeaderData(requestHeaders, CConst.CORELID, UUID.randomUUID().toString()); //X-Request-Correlation-ID : Retrieve Correlation ID or Set If Missing
        CAudit auditInfo = new CAudit(szCorelID, "NONE");
        CEntityData entityData;
        Timestamp tsRange = null;
        ArrayList<Object[]> listResults = new ArrayList<Object[]>();

        if(null != szRange) {
            entityData = entityBuilder.buildResponse(CConst.SUCCESS, null);
            tsRange= genLib.changeTimeStamp(szRange);
            CDaoReturn daoReturn = chartDao.getChartPricesByRange2(szSymbol, tsRange, auditInfo);
            List<CLineChart> listChart = (List<CLineChart>)daoReturn.getObjData();
            for(CLineChart lineItem : listChart){
                listResults.add(prepareArray(lineItem));
            }
        }else{
            entityData = entityBuilder.buildResponse(CConst.BADREQUEST, null);
        }
        return (new ResponseEntity<>(listResults, entityData.getHeaders(), entityData.getHttpStatus()));
    }


    private Object[] prepareArray(Long longDate, Double doubleValue){
        Object[] resultArray = new Object[2];
        resultArray[0] = longDate;
        BigDecimal dbValue = new BigDecimal(doubleValue).setScale(2, RoundingMode.HALF_UP);
        resultArray[1] = dbValue;

        return resultArray;
    }
}
