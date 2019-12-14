package com.duanegrey.dcuisvc.util;

public class CConst {
    //Token Specific
    public static final String UID = "UID";
    public static final String ROLES = "ROLES";
    public static final String ISSUER = "AuthServer";
    public static final String SUBJECT = "API Usage";
    public static final String AUTHTYPE = "bearer";
    public static final String REFRESHTYPE = "refresh";
    public static final String TOKENTYPE="token_type";
    public static final int EXPIRED=2000;
    public static final int BADTOKEN=2001;
    public static final int BADSIGNATURE=20002;
    public static final int WRONGTOKENTYPE=20003;
    public static final int MISSINGAUTHHEADER = 20004;
    public static final int AUTHHEADERISSUE = 20005;
    public static final int MISSINGUSERINFO=20006;

    //Error Codes
    public static final int SUCCESS = 200;
    public static final int CREATED = 201;
    public static final int BADREQUEST = 400;
    public static final int NOTFOUND = 404;
    public static final int FORBIDDEN = 403;
    public static final int NOTALLOWED = 405;
    public static final int INTERNALERR = 500;
    public static final int DBFAIL = 1000;
    public static final int ROLERETFAIL = 1001;
    public static final int NOROLEFOUND = 1002;
    public static final int UNAMEINUSE = 1003;
    public static final int MISSINGDATA = 1004;
    public static final int UNKNOWN = 1005;

    //General
    public static final String CORELID="X-Request-Correlation-ID";
    public static final String AUTHHEADER="Authorization";

    //Data APIs
    public static final String STOCK_PRICESERIES = "http://<host>:<port>/divclarity/v1/stock/<symbol>/priceseries/range/<range>";
    public static final String CCC_DATA = "http://<host>:<port>/divclarity/v1/stock/cccdata";
}
