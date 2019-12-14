package com.duanegrey.dcuisvc.svcprovider;

import com.duanegrey.dcuisvc.config.CAppProperties;
import com.duanegrey.dcuisvc.model.utility.CAudit;
import com.duanegrey.dcuisvc.model.utility.CEntityData;
import com.duanegrey.dcuisvc.util.CConst;
import com.duanegrey.dcuisvc.util.CEntityBuilder;
import com.duanegrey.dcuisvc.util.CGenLib;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class CUIServices {
    private final CAppProperties appProperties;
    private final RestTemplate restTemplate;
    private CEntityBuilder entityBuilder;
    private CGenLib genLib;

    @Autowired
    public CUIServices(CAppProperties appProperties,RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.appProperties = appProperties;
        entityBuilder = new CEntityBuilder();
        genLib = new CGenLib();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/divclarity/v1/uidata/{szSymbol}/priceseries/range/{szRange}") //Get List of Companies by Sector
    ResponseEntity getCompPricesByRange2(@PathVariable String szSymbol, @PathVariable String szRange, @RequestHeader HttpHeaders requestHeaders) {
        String szCorelID = genLib.getHeaderData(requestHeaders, CConst.CORELID, UUID.randomUUID().toString()); //X-Request-Correlation-ID : Retrieve Correlation ID or Set If Missing
        CAudit auditInfo = new CAudit(szCorelID, "NONE");
        CEntityData entityData;
        Timestamp tsRange = null;
        ArrayList<Object[]> listResults = new ArrayList<Object[]>();
        JsonNode returnJSON = JsonNodeFactory.instance.arrayNode();;

        if(null != szRange && szRange.length() >0 && null != szSymbol && szSymbol.length()>0) {
            entityData = entityBuilder.buildResponse(CConst.SUCCESS, null);
            tsRange= genLib.changeTimeStamp(szRange);
            String szURL = buildURL(CConst.STOCK_PRICESERIES,szSymbol,szRange); //Build URL
            JsonNode responseJson = callAPI(szURL);//Call API & GetResponse
            if(null != responseJson) { //Loop Through the Response
                returnJSON =responseJson;
            }else
            {
                entityData = entityBuilder.buildResponse(CConst.INTERNALERR, null);
            }
        }else{
            entityData = entityBuilder.buildResponse(CConst.BADREQUEST, null);
        }
        return (new ResponseEntity<>(returnJSON, entityData.getHeaders(), entityData.getHttpStatus()));
    }

    private Object[] prepareArray(Long longDate, Double doubleValue){ //Will have to convert JSON values to long and double
        Object[] resultArray = new Object[2];
        resultArray[0] = longDate;
        BigDecimal dbValue = new BigDecimal(doubleValue).setScale(2, RoundingMode.HALF_UP);
        resultArray[1] = dbValue;

        return resultArray;
    }

    private String buildURL(String szURL, String szSymbol, String szRange){
        String finalURL = null;
        finalURL = szURL.replace("<host>",appProperties.getDataHost()).replace("<port>",appProperties.getDataPort());
        if(null != szSymbol && szSymbol.length()>0){
            finalURL = finalURL.replace("<symbol>",szSymbol);
        }
        if(null != szRange && szRange.length()>0){
            finalURL = finalURL.replace("<range>",szRange);
        }
        return  finalURL;
    }

    private JsonNode callAPI(String szURL) {
        JsonNode jsonResponse = null;
        try {
            //BuildURL
            HttpHeaders headers = new HttpHeaders();
            headers.set("accept", "application/json");
            HttpEntity requestEntity = new HttpEntity<>(null, headers);
            ResponseEntity<JsonNode> response = restTemplate.exchange(szURL, HttpMethod.GET, requestEntity, JsonNode.class);
            jsonResponse = response.getBody();
        } catch (Exception Excep) {
            System.out.println("ERORR processing request"); //TODO FiX This LAter
        }
        return jsonResponse;
    }
}
