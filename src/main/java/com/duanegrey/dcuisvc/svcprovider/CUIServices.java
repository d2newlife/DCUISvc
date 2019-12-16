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
    @GetMapping("/divclarity/v1/uidata/{szSymbol}/balancesheet/{szType}")
    ResponseEntity getBalanceSheetData(@PathVariable String szSymbol, @PathVariable String szType, @RequestHeader HttpHeaders requestHeaders) {
        String szCorelID = genLib.getHeaderData(requestHeaders, CConst.CORELID, UUID.randomUUID().toString()); //X-Request-Correlation-ID : Retrieve Correlation ID or Set If Missing
        CAudit auditInfo = new CAudit(szCorelID, "NONE");
        CEntityData entityData;
        Timestamp tsRange = null;
        ArrayList<Object[]> listResults = new ArrayList<Object[]>();
        JsonNode returnJSON = JsonNodeFactory.instance.arrayNode();;
        String[] arrayKeys = {"symbol","companyname","sector","ttmDividendRate"};

        if(null != szSymbol && szSymbol.length() >0 && null != szType && szType.length()>0) {
            String szURL = buildURL(CConst.BALANCESHEET_DATA, szSymbol, null, null); //Build URL
            szURL = szURL.replace("<type>",szType);
            JsonNode responseJson = callAPI(szURL);//Call API & GetResponse
            if (null != responseJson) { //Loop Through the Response
                JsonNode balanceNode = responseJson.path("balancesheet");
                if (!balanceNode.isMissingNode()) {
                    if (balanceNode.isArray()) {
                        /*
                        for (JsonNode objNode : stockNode) {
                            listResults.add(prepareArray(objNode, arrayKeys));
                        }*/
                    }
                    entityData = entityBuilder.buildResponse(CConst.SUCCESS, null);
                } else {
                    entityData = entityBuilder.buildResponse(CConst.INTERNALERR, null);
                }
            } else {
                entityData = entityBuilder.buildResponse(CConst.INTERNALERR, null);
            }
        }else{
            entityData = entityBuilder.buildResponse(CConst.BADREQUEST, null);
        }
        return (new ResponseEntity<>(listResults, entityData.getHeaders(), entityData.getHttpStatus()));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/divclarity/v1/uidata/sectable/{szSector}")
    ResponseEntity getSecData(@PathVariable String szSector, @RequestHeader HttpHeaders requestHeaders) {
        String szCorelID = genLib.getHeaderData(requestHeaders, CConst.CORELID, UUID.randomUUID().toString()); //X-Request-Correlation-ID : Retrieve Correlation ID or Set If Missing
        CAudit auditInfo = new CAudit(szCorelID, "NONE");
        CEntityData entityData;
        Timestamp tsRange = null;
        ArrayList<Object[]> listResults = new ArrayList<Object[]>();
        JsonNode returnJSON = JsonNodeFactory.instance.arrayNode();;
        String[] arrayKeys = {"symbol","companyname","sector","ttmDividendRate"};

        if(null != szSector && szSector.length() >0) {
            String szURL = buildURL(CConst.SECTOR_DATA, null, null, szSector); //Build URL
            JsonNode responseJson = callAPI(szURL);//Call API & GetResponse
            if (null != responseJson) { //Loop Through the Response
                JsonNode stockNode = responseJson.path("stocks");
                if (!stockNode.isMissingNode()) {
                    if (stockNode.isArray()) {
                        for (JsonNode objNode : stockNode) {
                            listResults.add(prepareArray(objNode, arrayKeys));
                        }
                    }
                    entityData = entityBuilder.buildResponse(CConst.SUCCESS, null);
                } else {
                    entityData = entityBuilder.buildResponse(CConst.INTERNALERR, null);
                }
            } else {
                entityData = entityBuilder.buildResponse(CConst.INTERNALERR, null);
            }
        }else{
            entityData = entityBuilder.buildResponse(CConst.BADREQUEST, null);
        }
        return (new ResponseEntity<>(listResults, entityData.getHeaders(), entityData.getHttpStatus()));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/divclarity/v1/uidata/{szSymbol}/divtable/range/{szRange}")
    ResponseEntity getDivData(@PathVariable String szSymbol, @PathVariable String szRange, @RequestHeader HttpHeaders requestHeaders) {
        String szCorelID = genLib.getHeaderData(requestHeaders, CConst.CORELID, UUID.randomUUID().toString()); //X-Request-Correlation-ID : Retrieve Correlation ID or Set If Missing
        CAudit auditInfo = new CAudit(szCorelID, "NONE");
        CEntityData entityData;
        Timestamp tsRange = null;
        ArrayList<Object[]> listResults = new ArrayList<Object[]>();
        JsonNode returnJSON = JsonNodeFactory.instance.arrayNode();;
        String[] arrayKeys = {"exdate","amount","paymentdate","declareddate","recorddate"};

        if(null != szSymbol && szSymbol.length() >0 && null != szRange && szRange.length()>0) {
            String szURL = buildURL(CConst.DIV_DATA, szSymbol, szRange, null); //Build URL
            JsonNode responseJson = callAPI(szURL);//Call API & GetResponse
            if (null != responseJson) { //Loop Through the Response
                JsonNode stockNode = responseJson.path("dividends");
                if (!stockNode.isMissingNode()) {
                    if (stockNode.isArray()) {
                        for (JsonNode objNode : stockNode) {
                            listResults.add(prepareArray(objNode, arrayKeys));
                        }
                    }
                    entityData = entityBuilder.buildResponse(CConst.SUCCESS, null);
                } else {
                    entityData = entityBuilder.buildResponse(CConst.INTERNALERR, null);
                }
            } else {
                entityData = entityBuilder.buildResponse(CConst.INTERNALERR, null);
            }
        }else{
            entityData = entityBuilder.buildResponse(CConst.BADREQUEST, null);
        }
        return (new ResponseEntity<>(listResults, entityData.getHeaders(), entityData.getHttpStatus()));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/divclarity/v1/uidata/cccdata")
    ResponseEntity getCCCData(@RequestHeader HttpHeaders requestHeaders) {
        String szCorelID = genLib.getHeaderData(requestHeaders, CConst.CORELID, UUID.randomUUID().toString()); //X-Request-Correlation-ID : Retrieve Correlation ID or Set If Missing
        CAudit auditInfo = new CAudit(szCorelID, "NONE");
        CEntityData entityData;
        Timestamp tsRange = null;
        ArrayList<Object[]> listResults = new ArrayList<Object[]>();
        JsonNode returnJSON = JsonNodeFactory.instance.arrayNode();;
        String[] arrayKeys = {"symbol","companyname","ccctype","ttmDividendRate"};

        String szURL = buildURL(CConst.CCC_DATA,null,null, null); //Build URL
        JsonNode responseJson = callAPI(szURL);//Call API & GetResponse
        if(null != responseJson) { //Loop Through the Response
            JsonNode stockNode = responseJson.path("stocks");
            if (!stockNode.isMissingNode()) {
                if (stockNode.isArray()) {
                    for(JsonNode objNode : stockNode) {
                        listResults.add(prepareArray(objNode,arrayKeys));
                    }
                }
                entityData = entityBuilder.buildResponse(CConst.SUCCESS, null);
            }else{
                entityData = entityBuilder.buildResponse(CConst.INTERNALERR, null);
            }
        }else{
            entityData = entityBuilder.buildResponse(CConst.INTERNALERR, null);
        }
        return (new ResponseEntity<>(listResults, entityData.getHeaders(), entityData.getHttpStatus()));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/divclarity/v1/uidata/{szSymbol}/divseries/range/{szRange}")
    ResponseEntity getDivSeriesByRange(@PathVariable String szSymbol, @PathVariable String szRange, @RequestHeader HttpHeaders requestHeaders) {
        String szCorelID = genLib.getHeaderData(requestHeaders, CConst.CORELID, UUID.randomUUID().toString()); //X-Request-Correlation-ID : Retrieve Correlation ID or Set If Missing
        CAudit auditInfo = new CAudit(szCorelID, "NONE");
        CEntityData entityData;
        Timestamp tsRange = null;
        ArrayList<Object[]> listResults = new ArrayList<Object[]>();
        JsonNode returnJSON = JsonNodeFactory.instance.arrayNode();;

        if(null != szRange && szRange.length() >0 && null != szSymbol && szSymbol.length()>0) {
            entityData = entityBuilder.buildResponse(CConst.SUCCESS, null);
            tsRange= genLib.changeTimeStamp(szRange);
            String szURL = buildURL(CConst.STOCK_DIVSERIES,szSymbol,szRange,null); //Build URL
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

    @CrossOrigin(origins = "*")
    @GetMapping("/divclarity/v1/uidata/{szSymbol}/priceseries/range/{szRange}")
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
            String szURL = buildURL(CConst.STOCK_PRICESERIES,szSymbol,szRange,null); //Build URL
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

    private Object[] prepareArray(JsonNode objNode, String[] arrayKeys){
        Object[] resultArray = null;
        int index;
        if(null != objNode) {
            resultArray = new Object[arrayKeys.length];
            for(index = 0; index < arrayKeys.length; index++){
                resultArray[index] = objNode.path(arrayKeys[index]).asText();
            }
        }else{
            resultArray = new Object[0];
        }
        return resultArray;
    }

    private String buildURL(String szURL, String szSymbol, String szRange, String szSector){
        String finalURL = null;
        finalURL = szURL.replace("<host>",appProperties.getDataHost()).replace("<port>",appProperties.getDataPort());
        if(null != szSymbol && szSymbol.length()>0){
            finalURL = finalURL.replace("<symbol>",szSymbol);
        }
        if(null != szRange && szRange.length()>0){
            finalURL = finalURL.replace("<range>",szRange);
        }
        if(null != szSector && szSector.length()>0){
            finalURL = finalURL.replace("<sector>",szSector);
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
