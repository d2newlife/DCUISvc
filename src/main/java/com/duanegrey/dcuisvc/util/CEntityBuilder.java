package com.duanegrey.dcuisvc.util;

import com.duanegrey.dcuisvc.model.utility.CEntityData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class CEntityBuilder {
    public CEntityBuilder() {
    }

    public CEntityData buildResponse(int nResponseCode, String szUserMessage) {

        CEntityData entityData;
        switch (nResponseCode) {
            case CConst.CREATED:
                entityData = buildCreated();
                break;
            case CConst.DBFAIL:
                entityData = buildDBFailure();
                break;
            case CConst.UNAMEINUSE:
                entityData = buildNameUse();
                break;
            case CConst.MISSINGDATA:
                entityData = buildMissingData(szUserMessage);
                break;
            case CConst.SUCCESS:
                entityData = buildSuccess();
                break;
            case CConst.NOTFOUND:
                entityData = buildNotFound();
                break;
            case CConst.INTERNALERR:
                entityData = buildInternalErr();
                break;
            case CConst.EXPIRED:
                entityData = buildTokenExpired();
                break;
            case CConst.BADTOKEN:
                entityData = buildTokenIssue();
                break;
            case CConst.BADSIGNATURE:
                entityData = buildTokenTamper();
                break;
            case CConst.WRONGTOKENTYPE:
                entityData = buildWrongTokenType();
                break;
            case CConst.MISSINGAUTHHEADER:
                entityData = buildNoAuthHeader();
                break;
            case CConst.AUTHHEADERISSUE:
                entityData = buildErrAuthHeader();
                break;
            case CConst.MISSINGUSERINFO:
                entityData = buildMissingUserInfo();
                break;
            case CConst.FORBIDDEN:
                entityData = buildForbidden();
                break;
            case CConst.BADREQUEST:
                entityData = buildBadRequest(szUserMessage);
                break;
            default:
                entityData = buildDefault();
                break;
        }
        return entityData;
    }

    private CEntityData buildBadRequest(String szUserMessage){
        CEntityData entityData = new CEntityData();
        Map<String, Object> messageBody = new HashMap<String, Object>();
        messageBody.put("status", CConst.BADREQUEST);
        messageBody.put("developerMessage", "There is a problem with the data or format of the request");
        if(null != szUserMessage && szUserMessage.length() > 1)
        {
            messageBody.put("userMessage", szUserMessage);
        }else{
            messageBody.put("userMessage", "There was a problem with the last request please try again");
        }
        messageBody.put("moreInfo", "http://www.dividendclarity.com/docs/codes/"+CConst.BADREQUEST);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");

        entityData.setMapBody(messageBody);
        entityData.setHeaders(headers);
        entityData.setHttpStatus(HttpStatus.BAD_REQUEST);

        return entityData;
    }

    private CEntityData buildForbidden(){
        CEntityData entityData = new CEntityData();
        Map<String, Object> messageBody = new HashMap<String, Object>();
        messageBody.put("status", CConst.FORBIDDEN);
        messageBody.put("developerMessage", "Roles supplied in token are not allowed to access the requested API");
        messageBody.put("userMessage", "You are not authorized to access this URL");
        messageBody.put("moreInfo", "http://www.dividendclarity.com/docs/codes/"+CConst.FORBIDDEN);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");

        entityData.setMapBody(messageBody);
        entityData.setHeaders(headers);
        entityData.setHttpStatus(HttpStatus.FORBIDDEN);

        return entityData;
    }

    private CEntityData buildMissingUserInfo(){
        CEntityData entityData = new CEntityData();
        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("status", CConst.MISSINGUSERINFO);
        messageBody.put("developerMessage", "Minimum user information was not found to create a token");
        messageBody.put("userMessage", "Please login again, your essential user information was not found");
        messageBody.put("moreInfo", "http://www.dividendclarity.com/docs/codes/"+CConst.MISSINGUSERINFO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");

        entityData.setMapBody(messageBody);
        entityData.setHeaders(headers);
        entityData.setHttpStatus(HttpStatus.NOT_FOUND);

        return entityData;
    }

    private CEntityData buildNoAuthHeader(){
        CEntityData entityData = new CEntityData();
        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("status", CConst.MISSINGAUTHHEADER);
        messageBody.put("developerMessage", "Authorization Header is missing in the request");
        messageBody.put("userMessage", "Please login again, the request was missing authorization information");
        messageBody.put("moreInfo", "http://www.dividendclarity.com/docs/codes/"+CConst.MISSINGAUTHHEADER);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");

        entityData.setMapBody(messageBody);
        entityData.setHeaders(headers);
        entityData.setHttpStatus(HttpStatus.BAD_REQUEST);

        return entityData;
    }

    private CEntityData buildErrAuthHeader(){
        CEntityData entityData = new CEntityData();
        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("status", CConst.AUTHHEADERISSUE);
        messageBody.put("developerMessage", "There was an issue parsing the Authorization Header (It Should Be: Authorization Bearer JWT-Token-Value");
        messageBody.put("userMessage", "Please login again, the request authorization information is incorrect");
        messageBody.put("moreInfo", "http://www.dividendclarity.com/docs/codes/"+CConst.AUTHHEADERISSUE);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");

        entityData.setMapBody(messageBody);
        entityData.setHeaders(headers);
        entityData.setHttpStatus(HttpStatus.BAD_REQUEST);

        return entityData;
    }

    private CEntityData buildWrongTokenType(){
        CEntityData entityData = new CEntityData();
        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("status", CConst.WRONGTOKENTYPE);
        messageBody.put("developerMessage", "Incorrect token type used for authorization refresh");
        messageBody.put("userMessage", "Please login again, your token is incorrect");
        messageBody.put("moreInfo", "http://www.dividendclarity.com/docs/codes/"+CConst.WRONGTOKENTYPE);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");

        entityData.setMapBody(messageBody);
        entityData.setHeaders(headers);
        entityData.setHttpStatus(HttpStatus.BAD_REQUEST);

        return entityData;
    }

    private CEntityData buildTokenExpired(){
        CEntityData entityData = new CEntityData();
        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("status", CConst.EXPIRED);
        messageBody.put("developerMessage", "Token has expired, login credentials required.");
        messageBody.put("userMessage", "Please login again, your token has expired");
        messageBody.put("moreInfo", "http://www.dividendclarity.com/docs/codes/"+CConst.EXPIRED);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");

        entityData.setMapBody(messageBody);
        entityData.setHeaders(headers);
        entityData.setHttpStatus(HttpStatus.BAD_REQUEST);

        return entityData;
    }

    private CEntityData buildTokenIssue(){
        CEntityData entityData = new CEntityData();
        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("status", CConst.BADTOKEN);
        messageBody.put("developerMessage", "Token sent could not be decoded.");
        messageBody.put("userMessage", "Please login again, your token is incorrect.");
        messageBody.put("moreInfo", "http://www.dividendclarity.com/docs/codes/"+CConst.BADTOKEN);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");

        entityData.setMapBody(messageBody);
        entityData.setHeaders(headers);
        entityData.setHttpStatus(HttpStatus.BAD_REQUEST);

        return entityData;
    }
    private CEntityData buildTokenTamper(){
        CEntityData entityData = new CEntityData();
        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("status", CConst.BADSIGNATURE);
        messageBody.put("developerMessage", "Token signature has been modified");
        messageBody.put("userMessage", "Please login again, your token is incorrect.");
        messageBody.put("moreInfo", "http://www.dividendclarity.com/docs/codes/"+CConst.BADSIGNATURE);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");

        entityData.setMapBody(messageBody);
        entityData.setHeaders(headers);
        entityData.setHttpStatus(HttpStatus.BAD_REQUEST);

        return entityData;
    }

    private CEntityData buildDefault(){
        CEntityData entityData = new CEntityData();
        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("status", CConst.UNKNOWN);
        messageBody.put("developerMessage", "An unknown response was generated, due to an unknown issue.");
        messageBody.put("userMessage", "Please try your request again an unknown error has occurred.");
        messageBody.put("moreInfo", "http://www.dividendclarity.com/docs/codes/"+CConst.UNKNOWN);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");

        entityData.setMapBody(messageBody);
        entityData.setHeaders(headers);
        entityData.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        return entityData;
    }

    private CEntityData buildNotFound()
    {
        CEntityData entityData = new CEntityData();
        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("status", CConst.NOTFOUND);
        messageBody.put("developerMessage", "No information was found matching the request.");
        messageBody.put("userMessage", "No information was found matching the request.");
        messageBody.put("moreInfo", "http://www.dividendclarity.com/docs/codes/"+CConst.NOTFOUND);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");

        entityData.setMapBody(messageBody);
        entityData.setHeaders(headers);
        entityData.setHttpStatus(HttpStatus.NOT_FOUND);

        return entityData;
    }

    private CEntityData buildInternalErr()
    {
        CEntityData entityData = new CEntityData();
        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("status", CConst.INTERNALERR);
        messageBody.put("developerMessage", "Data returned from the request was incorrect.");
        messageBody.put("userMessage", "An application failure prevented, we are sorry.");
        messageBody.put("moreInfo", "http://www.dividendclarity.com/docs/codes/"+CConst.INTERNALERR);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");

        entityData.setMapBody(messageBody);
        entityData.setHeaders(headers);
        entityData.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        return entityData;
    }

    private CEntityData buildSuccess()
    {
        CEntityData entityData = new CEntityData();
        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("status", CConst.SUCCESS);
        messageBody.put("developerMessage", "Successful Transaction");
        messageBody.put("userMessage", "Successful Transaction");
        messageBody.put("moreInfo", "http://www.dividendclarity.com/docs/codes/"+CConst.SUCCESS);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");

        entityData.setMapBody(messageBody);
        entityData.setHeaders(headers);
        entityData.setHttpStatus(HttpStatus.OK);

        return entityData;
    }

    private CEntityData buildCreated()
    {
        CEntityData entityData = new CEntityData();
        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("status", CConst.CREATED);
        messageBody.put("developerMessage", "Member was added successfully.");
        messageBody.put("userMessage", "Member was added successfully.");
        messageBody.put("moreInfo", "http://www.dividendclarity.com/docs/codes/"+CConst.CREATED);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");

        entityData.setMapBody(messageBody);
        entityData.setHeaders(headers);
        entityData.setHttpStatus(HttpStatus.CREATED);

        return entityData;
    }

    private CEntityData buildDBFailure()
    {
        CEntityData entityData = new CEntityData();
        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("status", CConst.DBFAIL);
        messageBody.put("developerMessage", "Database error occurred preventing the last operation.");
        messageBody.put("userMessage", "An application failure prevented, we are sorry.");
        messageBody.put("moreInfo", "http://www.dividendclarity.com/docs/codes/"+CConst.DBFAIL);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");

        entityData.setMapBody(messageBody);
        entityData.setHeaders(headers);
        entityData.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        return entityData;
    }

    private CEntityData buildMissingData(String szUserMessage)
    {
        CEntityData entityData = new CEntityData();
        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("status", CConst.MISSINGDATA);
        messageBody.put("developerMessage", "Submitted data is missing required fields, operation canceled");
        if(null != szUserMessage && szUserMessage.length() > 1)
        {
            messageBody.put("userMessage", szUserMessage);
        }else{
            messageBody.put("userMessage", "Needed information is missing");
        }
        messageBody.put("moreInfo", "http://www.dividendclarity.com/docs/codes/"+CConst.MISSINGDATA);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");

        entityData.setMapBody(messageBody);
        entityData.setHeaders(headers);
        entityData.setHttpStatus(HttpStatus.BAD_REQUEST);
        return entityData;
    }

    private CEntityData buildNameUse()
    {
        CEntityData entityData = new CEntityData();
        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("status", CConst.UNAMEINUSE);
        messageBody.put("developerMessage", "Username requested is already in use.");
        messageBody.put("userMessage", "Username requested is already in use.");
        messageBody.put("moreInfo", "http://www.dividendclarity.com/docs/codes/"+CConst.UNAMEINUSE);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");

        entityData.setMapBody(messageBody);
        entityData.setHeaders(headers);
        entityData.setHttpStatus(HttpStatus.IM_USED);

        return entityData;
    }
}

