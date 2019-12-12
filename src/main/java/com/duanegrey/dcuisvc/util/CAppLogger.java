package com.duanegrey.dcuisvc.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CAppLogger {

    private Logger AppLogger;

    public CAppLogger(){
        AppLogger = LogManager.getLogger("RollingFile");
    }

    public void debug(String szClass, String szMethod, String szMessage)
    {
        AppLogger.debug(formatMessage(szClass, szMethod, szMessage));
    }

    public void debug(String szCorelID, String szClass, String szMethod, String szMessage)
    {
        AppLogger.debug(formatMessage(szCorelID, szClass, szMethod, szMessage));
    }

    public void info(String szClass, String szMethod, String szMessage){
        AppLogger.info(formatMessage(szClass, szMethod, szMessage));
    }

    public void info(String szCorelID, String szClass, String szMethod, String szMessage){
        AppLogger.info(formatMessage(szCorelID, szClass, szMethod, szMessage));
    }

    public void info(String szCorelID, String szUID, String szClass, String szMethod, String szMessage){
        AppLogger.info(formatMessage(szCorelID, szUID, szClass, szMethod, szMessage));
    }

    public void warn(String szClass, String szMethod, String szMessage){
        AppLogger.warn(formatMessage(szClass, szMethod, szMessage));
    }

    public void warn(String szCorelID, String szClass, String szMethod, String szMessage){
        AppLogger.warn(formatMessage(szCorelID, szClass, szMethod, szMessage));
    }

    public void fatal(String szClass, String szMethod, String szMessage){
        AppLogger.fatal(formatMessage(szClass, szMethod, szMessage));
    }

    public void fatal(String szCorelID, String szClass, String szMethod, String szMessage){
        AppLogger.fatal(formatMessage(szCorelID, szClass, szMethod, szMessage));
    }

    private String formatMessage(String szClass, String szMethod, String szMessage){
        String szReturn = new String();
        if(null == szClass){szClass = "No Class Information";}
        if(null == szMethod){szMethod = "No Method Information";}
        szReturn = szReturn.concat(szClass).concat(".").concat(szMethod).concat(": ").concat(szMessage);
        return szReturn;
    }

    private String formatMessage(String szCorelID, String szClass, String szMethod, String szMessage){
        String szReturn = new String();
        if(null == szCorelID){szCorelID = "No Correlation ID Information";}
        if(null == szClass){szClass = "No Class Information";}
        if(null == szMethod){szMethod = "No Method Information";}
        if(null == szMessage){szMessage = "No Message Information";}
        szReturn = szReturn.concat("CORELID:").concat(szCorelID).concat(": ").concat(szClass).concat(".").concat(szMethod).concat(": ").concat(szMessage);
        return szReturn;
    }

    public void fatal(String szCorelID, String szUID, String szClass, String szMethod, String szMessage) {
        AppLogger.fatal(formatMessage(szCorelID, szUID, szClass, szMethod, szMessage));
    }

    public String formatMessage(String szCorelID, String szUID, String szClass, String szMethod, String szMessage) {
        String szReturn = new String();
        if (null == szCorelID) {
            szCorelID = "No Correlation ID Information";
        }
        if (null == szUID) {
            szUID = "No User ID Information";
        }
        if (null == szClass) {
            szClass = "No Class Information";
        }
        if (null == szMethod) {
            szMethod = "No Method Information";
        }
        if (null == szMessage) {
            szMessage = "No Message Information";
        }
        szReturn = szReturn.concat("CORELID:").concat(szCorelID).concat(":USERID:").concat(szUID).concat(":CLASS:").concat(szClass).concat(":METHOD:").concat(szMethod).concat(":MESSAGE:").concat(szMessage);
        return szReturn;
    }
}

