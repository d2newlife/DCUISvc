package com.duanegrey.dcuisvc.model.utility;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class CEntityData {
    private Map<String, Object> mapBody = new HashMap<String, Object>();
    private HttpHeaders headers = new HttpHeaders();
    private HttpStatus httpStatus;

    public CEntityData() {
    }

    public CEntityData(Map<String, Object> mapBody, HttpHeaders headers, HttpStatus httpStatus) {
        this.mapBody = mapBody;
        this.headers = headers;
        this.httpStatus = httpStatus;
    }

    public Map<String, Object> getMapBody() {
        return mapBody;
    }

    public void setMapBody(Map<String, Object> mapBody) {
        this.mapBody = mapBody;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void addBodyData(String szValueName, Object objData){
        if(null != szValueName && null != objData){
            mapBody.put(szValueName, objData);
        }
    }
}
