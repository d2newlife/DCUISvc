package com.duanegrey.dcuisvc.svcprovider;

import com.duanegrey.dcuisvc.config.CAppProperties;
import com.duanegrey.dcuisvc.interfaces.IGraphTemplate;
import com.duanegrey.dcuisvc.interfaces.IRowTemplate;
import com.duanegrey.dcuisvc.model.CGraphDesc;
import com.duanegrey.dcuisvc.model.CSeriesGraph;
import com.duanegrey.dcuisvc.model.CSeriesInfo;
import com.duanegrey.dcuisvc.templates.*;
import com.duanegrey.dcuisvc.model.CRowDesc;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @GetMapping("/divclarity/v1/uidata/stock/suggest2")
    ResponseEntity<String[]> getStockSuggest2(@RequestHeader HttpHeaders requestHeaders) {
        String szCorelID = genLib.getHeaderData(requestHeaders, CConst.CORELID, UUID.randomUUID().toString()); //X-Request-Correlation-ID : Retrieve Correlation ID or Set If Missing
        CAudit auditInfo = new CAudit(szCorelID, "NONE");
        CEntityData entityData;
        JsonNode returnJSON = JsonNodeFactory.instance.arrayNode();;
        String[] stringArray = null;

        String szURL = buildURL(CConst.SUGGEST,null,null,null, null); //Build URL
        JsonNode responseJson = callAPI(szURL);//Call API & GetResponse
        if(null != responseJson && responseJson.isArray()) {
            entityData = entityBuilder.buildResponse(CConst.SUCCESS, null);
            stringArray = new String[responseJson.size()];
            int index = 0;
            for (JsonNode objNode : responseJson) {
                String szSuggest = objNode.path("text").asText();
                if (null != szSuggest && szSuggest.length() > 27) {
                    szSuggest = szSuggest.substring(0, 27).concat("...");
                    stringArray[index] = szSuggest;
                } else if (null != szSuggest) {
                    stringArray[index] = szSuggest;
                }
                index++;
            }
        }else{
            entityData = entityBuilder.buildResponse(CConst.INTERNALERR, null);
        }
        return (new ResponseEntity<>(stringArray, entityData.getHeaders(), entityData.getHttpStatus()));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/divclarity/v1/uidata/stock/suggest")
    ResponseEntity<JsonNode> getStockSuggest(@RequestHeader HttpHeaders requestHeaders) {
        String szCorelID = genLib.getHeaderData(requestHeaders, CConst.CORELID, UUID.randomUUID().toString()); //X-Request-Correlation-ID : Retrieve Correlation ID or Set If Missing
        CAudit auditInfo = new CAudit(szCorelID, "NONE");
        CEntityData entityData;
        JsonNode returnJSON = JsonNodeFactory.instance.arrayNode();;

        String szURL = buildURL(CConst.SUGGEST,null,null,null, null); //Build URL
        JsonNode responseJson = callAPI(szURL);//Call API & GetResponse
        if(null != responseJson) {
            entityData = entityBuilder.buildResponse(CConst.SUCCESS, null);
            returnJSON =responseJson;
        }else
        {
            entityData = entityBuilder.buildResponse(CConst.INTERNALERR, null);
        }
        return (new ResponseEntity<>(returnJSON, entityData.getHeaders(), entityData.getHttpStatus()));
    }


    @CrossOrigin(origins = "*")
    @GetMapping("/divclarity/v1/uidata/paymonth/{szMonth}")
    ResponseEntity getPayMonth(@PathVariable String szMonth, @RequestHeader HttpHeaders requestHeaders) {
        String szCorelID = genLib.getHeaderData(requestHeaders, CConst.CORELID, UUID.randomUUID().toString()); //X-Request-Correlation-ID : Retrieve Correlation ID or Set If Missing
        CAudit auditInfo = new CAudit(szCorelID, "NONE");
        CEntityData entityData;
        Timestamp tsRange = null;
        ArrayList<Object[]> listResults = new ArrayList<Object[]>();
        JsonNode returnJSON = JsonNodeFactory.instance.arrayNode();;
        String[] arrayKeys = {"symbol","companyname","month","ttmDividendRate"};

        if(null != szMonth && szMonth.length() >0) {
            szMonth = genLib.removeBadChars(szMonth);
            String monthNumber = genLib.convertMonth(szMonth);
            szMonth = genLib.getMonthName(monthNumber);
            String szURL = buildURL(CConst.PAYMONTH_DATA, null, null, null, monthNumber); //Build URL
            JsonNode responseJson = callAPI(szURL);//Call API & GetResponse
            if (null != responseJson) { //Loop Through the Response
                JsonNode stockNode = responseJson.path("stocks");
                if (!stockNode.isMissingNode()) {
                    if (stockNode.isArray()) {
                        for (JsonNode objNode : stockNode) {
                            listResults.add(prepareArray(objNode, arrayKeys,szMonth));
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
    @GetMapping("/divclarity/v1/uidata/{szSymbol}/cashflow/{szType}")
    ResponseEntity getCashFlowData(@PathVariable String szSymbol, @PathVariable String szType, @RequestHeader HttpHeaders requestHeaders) {
        String szCorelID = genLib.getHeaderData(requestHeaders, CConst.CORELID, UUID.randomUUID().toString()); //X-Request-Correlation-ID : Retrieve Correlation ID or Set If Missing
        CAudit auditInfo = new CAudit(szCorelID, "NONE");
        CEntityData entityData;
        Timestamp tsRange = null;
        ArrayList<Object[]> listResults = new ArrayList<Object[]>();
        JsonNode returnJSON = JsonNodeFactory.instance.arrayNode();;
        CashRowTemplate crowTemplate = new CashRowTemplate();
        String[] arrayKeys = null;

        if(null != szSymbol && szSymbol.length() >0 && null != szType && szType.length()>0) {
            szSymbol = genLib.removeBadChars(szSymbol);
            szType = genLib.removeBadChars(szType);
            String szURL = buildURL(CConst.CASHFLOW_DATA, szSymbol, null, null, szType); //Build URL
            JsonNode responseJson = callAPI(szURL);//Call API & GetResponse
            if (null != responseJson) { //Loop Through the Response
                JsonNode cashFlowNode = responseJson.path(CConst.NODEPATHCFLOW);
                if (!cashFlowNode.isMissingNode()) {
                    int index = 0;
                    if (cashFlowNode.isArray()) {
                        arrayKeys = new String[cashFlowNode.size()];
                        Map<String, JsonNode> mapTemp = new HashMap<>();
                        for(JsonNode jsonData : cashFlowNode){ //Save Key Order, and Pus into a Map
                            String szReportDate = jsonData.path("reportdate").asText();
                            arrayKeys[index]=szReportDate;
                            mapTemp.put(szReportDate,jsonData);
                            index++;
                        }
                        listResults = buildRowTable(crowTemplate,arrayKeys,mapTemp);
                        //ADD A Calculated ROW FreeCashFlow = Operating Cash Flow (cashflow) - Capital Expenditure (capitalexpenditures)
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
    @GetMapping("/divclarity/v1/uidata/{szSymbol}/income/{szType}")
    ResponseEntity getIncomeData(@PathVariable String szSymbol, @PathVariable String szType, @RequestHeader HttpHeaders requestHeaders) {
        String szCorelID = genLib.getHeaderData(requestHeaders, CConst.CORELID, UUID.randomUUID().toString()); //X-Request-Correlation-ID : Retrieve Correlation ID or Set If Missing
        CAudit auditInfo = new CAudit(szCorelID, "NONE");
        CEntityData entityData;
        Timestamp tsRange = null;
        ArrayList<Object[]> listResults = new ArrayList<Object[]>();
        JsonNode returnJSON = JsonNodeFactory.instance.arrayNode();;
        IncomeRowTemplate irowTemplate = new IncomeRowTemplate();
        String[] arrayKeys = null;

        if(null != szSymbol && szSymbol.length() >0 && null != szType && szType.length()>0) {
            szSymbol = genLib.removeBadChars(szSymbol);
            szType = genLib.removeBadChars(szType);
            String szURL = buildURL(CConst.INCOME_DATA, szSymbol, null, null,szType); //Build URL
            JsonNode responseJson = callAPI(szURL);//Call API & GetResponse
            if (null != responseJson) { //Loop Through the Response
                JsonNode incomeNode = responseJson.path(CConst.NODEPATHISTAT);
                if (!incomeNode.isMissingNode()) {
                    int index = 0;
                    if (incomeNode.isArray()) {
                        arrayKeys = new String[incomeNode.size()];
                        Map<String, JsonNode> mapTemp = new HashMap<>();
                        for(JsonNode jsonData : incomeNode){ //Save Key Order, and Pus into a Map
                            String szReportDate = jsonData.path("reportdate").asText();
                            arrayKeys[index]=szReportDate;
                            mapTemp.put(szReportDate,jsonData);
                            index++;
                        }
                        listResults = buildRowTable(irowTemplate,arrayKeys,mapTemp);
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
    @GetMapping("/divclarity/v1/uidata/{szSymbol}/balancesheet/{szType}")
    ResponseEntity getBalanceSheetData(@PathVariable String szSymbol, @PathVariable String szType, @RequestHeader HttpHeaders requestHeaders) {
        String szCorelID = genLib.getHeaderData(requestHeaders, CConst.CORELID, UUID.randomUUID().toString()); //X-Request-Correlation-ID : Retrieve Correlation ID or Set If Missing
        CAudit auditInfo = new CAudit(szCorelID, "NONE");
        CEntityData entityData;
        Timestamp tsRange = null;
        ArrayList<Object[]> listResults = new ArrayList<Object[]>();
        JsonNode returnJSON = JsonNodeFactory.instance.arrayNode();;
        BalanceRowTemplate browTemplate = new BalanceRowTemplate();
        String[] arrayKeys = null;

        if(null != szSymbol && szSymbol.length() >0 && null != szType && szType.length()>0) {
            szSymbol = genLib.removeBadChars(szSymbol);
            szType = genLib.removeBadChars(szType);
            String szURL = buildURL(CConst.BALANCESHEET_DATA, szSymbol, null, null, szType); //Build URL
            JsonNode responseJson = callAPI(szURL);//Call API & GetResponse
            if (null != responseJson) { //Loop Through the Response
                JsonNode balanceNode = responseJson.path(CConst.NODEPATHBSHEET);
                if (!balanceNode.isMissingNode()) {
                    int index = 0;
                    if (balanceNode.isArray()) {
                        arrayKeys = new String[balanceNode.size()];
                        Map<String, JsonNode> mapTemp = new HashMap<>();
                        for(JsonNode jsonData : balanceNode){ //Save Key Order, and Pus into a Map
                            String szReportDate = jsonData.path("reportdate").asText();
                            arrayKeys[index]=szReportDate;
                            mapTemp.put(szReportDate,jsonData);
                            index++;
                        }
                        listResults = buildRowTable(browTemplate,arrayKeys,mapTemp);
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
            szSector = genLib.removeBadChars(szSector);
            String szURL = buildURL(CConst.SECTOR_DATA, null, null, szSector, null); //Build URL
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
    ResponseEntity<ArrayList<Object[]>> getDivData(@PathVariable String szSymbol, @PathVariable String szRange, @RequestHeader HttpHeaders requestHeaders) {
        String szCorelID = genLib.getHeaderData(requestHeaders, CConst.CORELID, UUID.randomUUID().toString()); //X-Request-Correlation-ID : Retrieve Correlation ID or Set If Missing
        CAudit auditInfo = new CAudit(szCorelID, "NONE");
        CEntityData entityData;
        ArrayList<Object[]> listResults = new ArrayList<>();
        String[] arrayKeys = {"exdate","amount","paymentdate","declareddate","recorddate"};

        if(null != szSymbol && szSymbol.length() >0 && null != szRange && szRange.length()>0) {
            szSymbol = genLib.removeBadChars(szSymbol);
            szRange = genLib.removeBadChars(szRange);
            String szURL = buildURL(CConst.DIV_DATA, szSymbol, szRange, null, null); //Build URL
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
        ArrayList<Object[]> listResults = new ArrayList<Object[]>();
        JsonNode returnJSON = JsonNodeFactory.instance.arrayNode();;
        String[] arrayKeys = {"symbol","companyname","ccctype","ttmDividendRate"};

        String szURL = buildURL(CConst.CCC_DATA,null,null, null, null); //Build URL
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
            szSymbol = genLib.removeBadChars(szSymbol);
            szRange = genLib.removeBadChars(szRange);
            entityData = entityBuilder.buildResponse(CConst.SUCCESS, null);
            tsRange= genLib.changeTimeStamp(szRange);
            String szURL = buildURL(CConst.STOCK_DIVSERIES,szSymbol,szRange,null, null); //Build URL
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
        JsonNode returnJSON = JsonNodeFactory.instance.arrayNode();

        if(null != szRange && szRange.length() >0 && null != szSymbol && szSymbol.length()>0) {
            szSymbol = genLib.removeBadChars(szSymbol);
            szRange = genLib.removeBadChars(szRange);
            entityData = entityBuilder.buildResponse(CConst.SUCCESS, null);
            tsRange= genLib.changeTimeStamp(szRange);
            String szURL = buildURL(CConst.STOCK_PRICESERIES,szSymbol,szRange,null, null); //Build URL
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
    @GetMapping("/divclarity/v1/uidata/{szSymbol}/fingraph/{szFinType}/{szType}")
    ResponseEntity<Map<String, Object>> getFinGraph(@PathVariable String szSymbol, @PathVariable String szType, @PathVariable String szFinType, @RequestHeader HttpHeaders requestHeaders) {
        CSeriesGraph seriesGraph = new CSeriesGraph();
        boolean bBadFinType = false;
        String szURL = "";
        String szNodePath = "";
        CEntityData entityData;
        String[] arrayKeys;
        IGraphTemplate graphTemplate=null;
        if(null != szType && szType.length() >0 && null != szSymbol && szSymbol.length()>0 && null != szFinType && szFinType.length()>0) {
            szSymbol = genLib.removeBadChars(szSymbol);
            szType = genLib.removeBadChars(szType);
            szType = validateType(szType);
            szFinType = genLib.removeBadChars(szFinType);
            switch (szFinType) { //Figure Out What URL To Call & Set URL / Path Type Variables
                case CConst.CFLOW:
                    szURL = buildURL(CConst.CASHFLOW_DATA, szSymbol, null, null, szType); //Build URL
                    szURL = szURL.concat(CConst.SORTASC);
                    szNodePath = CConst.NODEPATHCFLOW;
                    graphTemplate = new CashGraphTemplate();
                    break;
                case CConst.BSHEET:
                    szURL = buildURL(CConst.BALANCESHEET_DATA, szSymbol, null, null, szType); //Build URL
                    szURL = szURL.concat(CConst.SORTASC);
                    szNodePath = CConst.NODEPATHBSHEET;
                    graphTemplate = new BalanceGraphTemplate();
                    break;
                case CConst.ISTAT:
                    szURL = buildURL(CConst.INCOME_DATA, szSymbol, null, null,szType); //Build URL
                    szURL = szURL.concat(CConst.SORTASC);
                    szNodePath = CConst.NODEPATHISTAT;
                    graphTemplate = new IncomeGraphTemplate();
                    break;
                default:
                    bBadFinType = true;
                    break;
            }
            if(bBadFinType){
                entityData = entityBuilder.buildResponse(CConst.BADREQUEST, null);
            }else {
                JsonNode responseJson = callAPI(szURL);//Call API & GetResponse
                if (null != responseJson) { //Loop Through the Response
                    JsonNode dataNode = responseJson.path(szNodePath);
                    if (!dataNode.isMissingNode()) {
                        int index = 0;
                        if (dataNode.isArray()) {
                            arrayKeys = new String[dataNode.size()];
                            Map<String, JsonNode> mapTemp = new HashMap<>();
                            for(JsonNode jsonData : dataNode){ //Save Key Order, and Pus into a Map
                                String szReportDate = jsonData.path(CConst.NPATHRDATE).asText();
                                arrayKeys[index]=szReportDate;
                                mapTemp.put(szReportDate,jsonData);
                                index++;
                            }
                            seriesGraph = buildFinGraph(graphTemplate,arrayKeys,mapTemp);  //Call BuildFinGraph
                        }
                        entityData = entityBuilder.buildResponse(CConst.SUCCESS, null);
                    } else {
                        entityData = entityBuilder.buildResponse(CConst.INTERNALERR, null);
                    }
                } else {
                    entityData = entityBuilder.buildResponse(CConst.INTERNALERR, null);
                }
                entityData = entityBuilder.buildResponse(CConst.SUCCESS, null);
            }
        }else{
            entityData = entityBuilder.buildResponse(CConst.BADREQUEST, null);
        }
        return (new ResponseEntity<>(seriesGraph.buildOutput(), entityData.getHeaders(), entityData.getHttpStatus()));
    }

    private CSeriesGraph buildFinGraph(IGraphTemplate graphTemplate, String[] arrayKeys, Map<String, JsonNode> mapData){
        CSeriesGraph seriesGraph = new CSeriesGraph();
        String[] xAxisLegend = buildXAxisCateogires(arrayKeys);
        if(null != xAxisLegend) {
            List<CSeriesInfo> listSeriesInfo = new ArrayList<>();
            seriesGraph.setXAxisCategories(xAxisLegend);
            seriesGraph.setPointStart(genLib.getMonth(arrayKeys[0],"MM/dd/yyyy"),genLib.getDay(arrayKeys[0],"MM/dd/yyyy"),genLib.getYear(arrayKeys[0],"MM/dd/yyyy"));
            seriesGraph.setTitle(graphTemplate.getTitle());
            List<CGraphDesc> listGraphDesc = graphTemplate.getGraphList();
            for (CGraphDesc graphDesc : listGraphDesc) { //Fill with Type Series Info
                Object[] objTemp = new Object[arrayKeys.length];//Create temp Object Array
                if(null != graphDesc && null != graphDesc.getCalcName()){
                    for(int dindex=0; dindex < arrayKeys.length; dindex++) {
                        JsonNode objNode = mapData.get(arrayKeys[dindex]);
                        switch (graphDesc.getCalcName()) {
                            case CConst.FCF:
                                Double dbReturn = calcFreeCashFlow(objNode, graphDesc.getDivideBy());
                                if(null != dbReturn && graphDesc.isbConvertIfNeg()){dbReturn = dbReturn * -1;}
                                if(null != dbReturn){objTemp[dindex] = dbReturn.intValue();}
                                break;
                            default:
                                break;
                        }
                    }
                    listSeriesInfo.add(new CSeriesInfo(graphDesc.getGraphLabel(),objTemp));
                }else{
                    for(int dindex=0; dindex < arrayKeys.length; dindex++) {
                        JsonNode objNode = mapData.get(arrayKeys[dindex]);
                        if(null != objNode) { //Get JsonNode
                            String szValue =  objNode.path(graphDesc.getJsonDataKey()).asText();//Get JSON Data
                            if(null == szValue || szValue.length()<1){ //If null or blank convert
                                objTemp[dindex] = null;
                            }else{//if not null and divideby set save as double
                                objTemp[dindex] = prepDouble(szValue,graphDesc.getDivideBy(),graphDesc.isbConvertIfNeg());
                            }
                        }
                    }
                    listSeriesInfo.add(new CSeriesInfo(graphDesc.getGraphLabel(),objTemp));
                }
            }
            seriesGraph.setListSeriesInfo(listSeriesInfo);
        }
        return seriesGraph;
    }

    private String[] buildXAxisCateogires(String[] arrayKeys){
        String[] returnArray = null;
        if(null != arrayKeys && arrayKeys.length>0) {
            returnArray = new String[arrayKeys.length];
            for (int index = 0; index < arrayKeys.length; index++) {
                String szDate = arrayKeys[index];
                String splitArray[] = szDate.split("/", 0);
                if (splitArray.length == 3) {
                    String szLegendItem;
                    switch (splitArray[0]) { //Figure Out What URL To Call & Set URL / Path Type Variables
                        case "01":
                            szLegendItem = "Jan ";
                            szLegendItem = szLegendItem.concat(splitArray[2].substring(splitArray[2].length()-2));
                            returnArray[index] = szLegendItem;
                            break;
                        case "02":
                            szLegendItem = "Feb ";
                            szLegendItem = szLegendItem.concat(splitArray[2].substring(splitArray[2].length()-2));
                            returnArray[index] = szLegendItem;
                            break;
                        case "03":
                            szLegendItem = "Mar ";
                            szLegendItem = szLegendItem.concat(splitArray[2].substring(splitArray[2].length()-2));
                            returnArray[index] = szLegendItem;
                            break;
                        case "04":
                            szLegendItem = "Apr ";
                            szLegendItem = szLegendItem.concat(splitArray[2].substring(splitArray[2].length()-2));
                            returnArray[index] = szLegendItem;
                            break;
                        case "05":
                            szLegendItem = "May ";
                            szLegendItem = szLegendItem.concat(splitArray[2].substring(splitArray[2].length()-2));
                            returnArray[index] = szLegendItem;
                            break;
                        case "06":
                            szLegendItem = "Jun ";
                            szLegendItem = szLegendItem.concat(splitArray[2].substring(splitArray[2].length()-2));
                            returnArray[index] = szLegendItem;
                            break;
                        case "07":
                            szLegendItem = "Jul ";
                            szLegendItem = szLegendItem.concat(splitArray[2].substring(splitArray[2].length()-2));
                            returnArray[index] = szLegendItem;
                            break;
                        case "08":
                            szLegendItem = "Aug ";
                            szLegendItem = szLegendItem.concat(splitArray[2].substring(splitArray[2].length()-2));
                            returnArray[index] = szLegendItem;
                            break;
                        case "09":
                            szLegendItem = "Sep ";
                            szLegendItem = szLegendItem.concat(splitArray[2].substring(splitArray[2].length()-2));
                            returnArray[index] = szLegendItem;
                            break;
                        case "10":
                            szLegendItem = "Oct ";
                            szLegendItem = szLegendItem.concat(splitArray[2].substring(splitArray[2].length()-2));
                            returnArray[index] = szLegendItem;
                            break;
                        case "11":
                            szLegendItem = "Nov ";
                            szLegendItem = szLegendItem.concat(splitArray[2].substring(splitArray[2].length()-2));
                            returnArray[index] = szLegendItem;
                            break;
                        case "12":
                            szLegendItem = "Dec ";
                            szLegendItem = szLegendItem.concat(splitArray[2].substring(splitArray[2].length()-2));
                            returnArray[index] = szLegendItem;
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return returnArray;
    }

    private ArrayList<Object[]> buildRowTable(IRowTemplate rowTemplate, String[] arrayKeys, Map<String, JsonNode> mapData) {
        ArrayList<Object[]> listResults = new ArrayList<Object[]>();
        Object[] objStart= new Object[arrayKeys.length+1]; //First Row is Just Keys
        objStart[0]="Information";
        for(int index =0; index < arrayKeys.length; index++){
            objStart[index+1] = arrayKeys[index];
        }
        listResults.add(objStart);
        //Loop Through List Building Arrays
        List<CRowDesc> listRowDesc = rowTemplate.getRowList();
        for(CRowDesc rowDescTemp : listRowDesc){
            Object[] objTemp = new Object[arrayKeys.length+1];//Create temp Object Array
            if(null != rowDescTemp && rowDescTemp.getRowClassification().equalsIgnoreCase(CConst.ROWGROUP)){
                objTemp[0] = rowDescTemp.getRowHeader();
                listResults.add(objTemp);
                for(int gindex=0; gindex < arrayKeys.length; gindex++){
                    objTemp[gindex+1] = ""; //If Row Group Set Blanks
                }
            }else if(null != rowDescTemp && rowDescTemp.getRowClassification().equalsIgnoreCase(CConst.ROWDATA)){
                objTemp[0] = rowDescTemp.getRowHeader();
                for(int dindex=0; dindex < arrayKeys.length; dindex++) {
                    JsonNode objNode = mapData.get(arrayKeys[dindex]);
                    if(null != objNode) { //Get JsonNode
                        String szValue =  objNode.path(rowDescTemp.getJsonDataKey()).asText();//Get JSON Data
                        if(null == szValue || szValue.length()<1){ //If null or blank convert
                            objTemp[dindex+1] = "-";
                        }else if(szValue != null && rowDescTemp.getDivideBy() >  Double.valueOf(0).doubleValue()) {//if not null and divideby set save as double
                            objTemp[dindex+1] = evaluteDouble(szValue,rowDescTemp.getDivideBy());
                        }else if(szValue != null && rowDescTemp.getDivideBy() < Double.valueOf(1).doubleValue()){ //if not null and divideby noset save as text
                            objTemp[dindex+1] = szValue;
                        }
                    }else{
                        objTemp[dindex+1] = "-";//Convert open array Space
                    }
                }
                listResults.add(objTemp);
            }else if(null != rowDescTemp && rowDescTemp.getRowClassification().equalsIgnoreCase(CConst.ROWCALC)){
                objTemp[0] = rowDescTemp.getRowHeader();
                String szCalcReturn = "-";
                for(int dindex=0; dindex < arrayKeys.length; dindex++) {
                    JsonNode objNode = mapData.get(arrayKeys[dindex]);
                    if(null != objNode) { //Get JsonNode
                        if (null != rowDescTemp.getCalcName()) {
                            switch (rowDescTemp.getCalcName()) {
                                case CConst.FCF:
                                    Double dbTotal = calcFreeCashFlow(objNode, rowDescTemp.getDivideBy());
                                    if(null != dbTotal) {
                                        szCalcReturn = String.format("%,.0f", dbTotal);
                                    }else{
                                        szCalcReturn="-";
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                        objTemp[dindex+1] = szCalcReturn;
                    }
                    else{
                        objTemp[dindex+1] = "-";//Convert open array Space
                    }
                }
                listResults.add(objTemp);
            }
        }
        return listResults;
    }

    public Double calcFreeCashFlow(JsonNode jsonNode,double divideBy){
        Double dbReturn=null;
        String szCashFlow =  jsonNode.path("cashflow").asText();//Get JSON Data
        String szCapExpend =  jsonNode.path("capitalexpenditures").asText();//Get JSON Data
        if(null != szCashFlow && szCashFlow.length()>0 && !szCashFlow.equalsIgnoreCase("null") && null != szCapExpend && szCapExpend.length()>0 && !szCapExpend.equalsIgnoreCase("null")){
            try {
                //Convert to Doubles
                double dbCashFlow = Double.parseDouble(szCashFlow);
                double dbCapExpend = Double.parseDouble(szCapExpend);
                double dbTotal = dbCashFlow + dbCapExpend; //Add Together8
                if(divideBy > (double) 0){ //Divide if DivideBy Set
                    dbTotal = dbTotal / divideBy;
                    dbReturn = dbTotal;
                }
            }catch(Exception Excep){}
        }else if(null != szCashFlow && szCashFlow.length()>0 && !szCashFlow.equalsIgnoreCase("null")){
            try {
                double dbCashFlow = Double.parseDouble(szCashFlow);
                double dbTotal = dbCashFlow;
                if(divideBy > (double) 0){ //Divide if DivideBy Set
                    dbTotal = dbTotal / divideBy;
                    dbReturn = dbTotal;
                }
            }catch(Exception Excep){}
        }
        return dbReturn;
    }

    public Integer prepDouble(String szValue, double divideBy, boolean convertNeg){
        Double dbReturnValue = null;
        Integer IntReturnValue  = null;
        try{
            double dbResult = Double.parseDouble(szValue);
            if(divideBy > (double)1){
                dbResult = dbResult / divideBy;
            }
            if(convertNeg){
                dbResult = dbResult * (double) -1;
            }
            dbReturnValue = BigDecimal.valueOf(dbResult).setScale(0, RoundingMode.HALF_UP).doubleValue();
            IntReturnValue = dbReturnValue.intValue();
        }catch(Exception Excep){
            //Return Null
        }
        return IntReturnValue;
    }

    public String evaluteDouble(String szValue, double divideBy){
        String returnValue;
        try{
            double dbReturn = Double.parseDouble(szValue);
            double dbResult = dbReturn / divideBy;
            returnValue = String.format("%,.0f",dbResult);
        }catch(Exception Excep){
            returnValue = "-";
        }
        return returnValue;
    }

    private Object[] prepareArray(JsonNode objNode, String[] arrayKeys){
        Object[] resultArray;
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

    private Object[] prepareArray(JsonNode objNode, String[] arrayKeys, String szMonth){
        String jsonValue;
        Object[] resultArray;
        int index;
        if(null != objNode) {
            resultArray = new Object[arrayKeys.length];
            for(index = 0; index < arrayKeys.length; index++){
                if(arrayKeys[index].equals("month")){
                    resultArray[index] = szMonth;
                }else{
                    jsonValue = objNode.path(arrayKeys[index]).asText();
                    if(null == jsonValue || jsonValue.length() < 1 || jsonValue.equalsIgnoreCase("null")){
                        jsonValue = "-";
                    }
                    resultArray[index] = jsonValue;
                }
            }
        }else{
            resultArray = new Object[0];
        }
        return resultArray;
    }

    private String buildURL(String szURL, String szSymbol, String szRange, String szSector, String szType){
        String finalURL;
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
        if(null != szType && szType.length()>0){
            finalURL = finalURL.replace("<type>",szType);
        }
        return  finalURL;
    }

    private JsonNode callAPI(String szURL) {
        JsonNode jsonResponse = null;
        try {
            //BuildURL
            HttpHeaders headers = new HttpHeaders();
            headers.set("accept", "application/json");
            HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
            ResponseEntity<JsonNode> response = restTemplate.exchange(szURL, HttpMethod.GET, requestEntity, JsonNode.class);
            jsonResponse = response.getBody();
        } catch (Exception Excep) {
            System.out.println("ERORR processing request"); //TODO FiX This LAter
        }
        return jsonResponse;
    }

    private String validateType(String szType) {
        String szReturn;
        if (null != szType && szType.equalsIgnoreCase("QUARTERLY")) {
            szReturn = "QUARTERLY";
        } else {
            szReturn = "ANNUAL";
        }
        return szReturn;
    }
}
