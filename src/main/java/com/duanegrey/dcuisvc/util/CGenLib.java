package com.duanegrey.dcuisvc.util;

import com.duanegrey.dcuisvc.model.utility.CAudit;
import com.duanegrey.dcuisvc.model.utility.CDateCalc;
import org.springframework.http.HttpHeaders;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.commons.io.FileUtils;

public class CGenLib {

    private CAppLogger appLogger = new CAppLogger();

    public CGenLib() {
    }

    public CDateCalc dateDifference(Timestamp timeOne)
    {
        CDateCalc dateCalc = new CDateCalc();

        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();
        dateCalc = dateDifference(new Timestamp(timeStampMillis),timeOne);

        return dateCalc;
    }

    public CDateCalc dateDifference(Timestamp timeOne, Timestamp timeTwo)
    {
        int nDays = 0;
        CDateCalc dateCalc = new CDateCalc();

        try {
            long diff = timeOne.getTime() - timeTwo.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            dateCalc.setMulti(true,diffSeconds,diffMinutes,diffHours,diffDays);

        } catch (Exception Excep) {
            appLogger.fatal(null,null,this.getClass().getCanonicalName(),"dateDifference"," Calculation of date difference failed due to the following exception :" + Excep.getMessage());
            dateCalc.setMulti(false,0,0,0,0);
        }
        return dateCalc;
    }

    public boolean isExpired(Date expireDate, CAudit auditTrack)
    {
        boolean bExpired = true;
        try {
            if (null != expireDate) {
                LocalDateTime ldtExpired = convertToLocalDateTime(expireDate);
                if(ldtExpired.isAfter(LocalDateTime.now())){
                    bExpired = false;
                }
            }
        }catch(Exception Excep){
            appLogger.fatal(auditTrack.getCorelid(),auditTrack.getUid(),this.getClass().getCanonicalName(),"isExpired"," Date Expiration check failed ");
        }
        return bExpired;
    }

    public LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public long secondsBetweenDates(Date dateOne, Date dateTwo)
    {
        long secondsBetween;

        LocalDateTime startDate = convertToLocalDateTime(dateOne);
        LocalDateTime endDate = convertToLocalDateTime(dateTwo);
        secondsBetween = Duration.between(startDate, endDate).toMinutes();

        return secondsBetween;
    }

    public String parseContent(String szContent, String szStartTag, String szEndTag) {
        String szValue = null;
        int nResult = -1;
        int nResult2 = -1;

        if (null != szContent && null != szStartTag && null != szEndTag) {

            szStartTag = szStartTag.toLowerCase();
            szEndTag = szEndTag.toLowerCase();
            szContent = szContent.toLowerCase();
            nResult = szContent.indexOf(szStartTag);
            if(-1 != nResult)
            {
                nResult2 = szContent.indexOf(szEndTag, nResult);
                if(-1 != nResult2)
                {
                    szValue = szContent.substring(nResult+szStartTag.length(), nResult2);
                }
                else {
                    appLogger.info(null,null,this.getClass().getCanonicalName(), "parseContent", " ParseContent cancelled due search tag not found in content: " + szEndTag);
                }
            }
            else {
                appLogger.info(null,null,this.getClass().getCanonicalName(), "parseContent", " ParseContent cancelled due search tag not found in content: " + szStartTag);
            }
        }
        else {
            appLogger.fatal(null,null,this.getClass().getCanonicalName(), "parseContent", " ParseContent cancelled due to one or more values being null");
        }
        return szValue;
    }

    public boolean isTodaySunday()
    {
        boolean bTrue = false;
        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();
        Timestamp currentTime = new Timestamp(timeStampMillis);
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeInMillis(currentTime.getTime());

        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
        {
            bTrue = true;
        }
        return bTrue;
    }

    public String appendValue(String szOriginal, String szSeparator, String szAddition)
    {
        String szFinal = "";
        if(null != szOriginal && szOriginal.length()>1 && null != szAddition && szAddition.length()>1)
        {
            szFinal = szOriginal + szSeparator + szAddition;
        }
        else if(null != szOriginal && szOriginal.length()>1)
        {
            szFinal = szOriginal;
        }
        else if(null != szAddition && szAddition.length()>1)
        {
            szFinal = szAddition;
        }
        return szFinal;
    }

    public String appendValue(String szOriginal, String szAddition)
    {
        String szFinal = "";
        if(null != szOriginal && szOriginal.length()>1 && null != szAddition && szAddition.length()>1)
        {
            szFinal = szOriginal + ", " + szAddition;
        }
        else if(null != szOriginal && szOriginal.length()>1)
        {
            szFinal = szOriginal;
        }
        else if(null != szAddition && szAddition.length()>1)
        {
            szFinal = szAddition;
        }
        return szFinal;
    }

    public String appendJobCode(String szCodeList, String szCodeType)
    {
        String szFinal = "";
        if(null != szCodeList && szCodeList.length()>1 && null != szCodeType && szCodeType.length()>1)
        {
            szFinal = szCodeList + "|" + szCodeType;
        }
        else if(null != szCodeList && szCodeList.length()>1)
        {
            szFinal = szCodeList;
        }
        else if(null != szCodeType && szCodeType.length()>1)
        {
            szFinal = szCodeType;
        }
        return szFinal;
    }

    public String getHeaderData(HttpHeaders headers, String szParamName, String szDefaultValue)
    {
        String szReturnvalue = null;

        List<String> listValues = headers.get(szParamName);
        if(null != listValues && listValues.size() > 0)
        {
            szReturnvalue = listValues.get(0);
        }
        else
        {
            szReturnvalue = szDefaultValue;
        }
        return szReturnvalue;
    }

    public String readTextFile(String fileName)
    {
        String fileContent;
        try
        {
            fileContent = new String(Files.readAllBytes(Paths.get(fileName)));
        }
        catch (IOException IExcep)
        {
            appLogger.fatal(this.getClass().getCanonicalName(),"readTextFile",IExcep.getMessage());
            fileContent = null;
        }
        return fileContent;
    }

    public List<String> readTextFileByLines(String fileName)
    {
        List<String> fileData;
        try
        {
            fileData = Files.readAllLines(Paths.get(fileName));
        }
        catch (IOException IExcep)
        {
            appLogger.fatal(this.getClass().getCanonicalName(),"readTextFileByLines",IExcep.getMessage());
            fileData = null;
        }
        return fileData;
    }

    public boolean writeToTextFile(String fileName, String content)
    {
        boolean result;
        try
        {
            Files.write(Paths.get(fileName), content.getBytes(), StandardOpenOption.CREATE);
            result = true;
        }
        catch (IOException IExcep)
        {
            appLogger.fatal(this.getClass().getCanonicalName(),"writeToTextFile",IExcep.getMessage());
            result = false;
        }
        return result;
    }

    public boolean validateNotNull(String szValue){
        boolean bReturn = false;
        if(null != szValue && szValue.length()>0){
            bReturn = true;
        }
        return bReturn;
    }

    public Double validateDouble(Double doubleInput){
        Double doubleReturn = null;

        if(null != doubleInput) {
            if(Double.isNaN(doubleInput)){
                doubleReturn = null;
            }
            else{
                doubleReturn = doubleInput;
            }
        }
        else
        {
            doubleReturn = doubleInput;
        }
        return doubleReturn;
    }

    public Double ObjToDouble(Object objTemp){
        Double dbReturn;
        if(null == objTemp){
            dbReturn = null;
        }else{
            try{
                dbReturn = Double.valueOf(objTemp.toString());
            }catch(Exception Excep){dbReturn = null;}
        }
        return dbReturn;
    }

    public BigInteger ObjToBigInteger(Object objTemp){
        BigInteger biReturn;
        if(null == objTemp){
            biReturn = null;
        }else{
            try{
                Number number = NumberFormat.getInstance().parse(objTemp.toString());
                biReturn = BigInteger.valueOf(((Number) number).longValue() );
            }catch(Exception Excep){biReturn = null;}
        }
        return biReturn;
    }

    public String ObjToString(Object objTemp){
        String szReturn;
        if(null == objTemp){
            szReturn = null;
        }else{
            szReturn = objTemp.toString();
        }
        return szReturn;
    }

    public Timestamp StringToTStamp(String szTemp, String szFormat)
    {
        Timestamp tStamp = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(szFormat);
            if (null != szTemp) {
                tStamp = new Timestamp((formatter.parse(szTemp)).getTime());
            }
        }catch(Exception Excep){
            appLogger.fatal(this.getClass().getCanonicalName(),"StringToTimestamp",Excep.getMessage());
        }
        return tStamp;
    }

    public List<String> getStocksFromFile(String szFile){
        List<String> fileContents = null;
        if(null != szFile)
        {
            try
            {
                File file = new File(szFile);
                fileContents = FileUtils.readLines(file, "UTF-8");
            }
            catch(IOException IOExcep)
            {
                appLogger.fatal(this.getClass().getCanonicalName(),"getStocksFromFile",IOExcep.getMessage());
            }
        }
        return fileContents;
    }

    public Timestamp subtractYears(Timestamp tsOriginal, int nAmount){
        Timestamp tsNew = null;
        if(null != tsOriginal && 0 != nAmount) {
            int nAmountNeg = nAmount * -1;
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(tsOriginal.getTime());
            cal.add(Calendar.YEAR, nAmountNeg);
            tsNew = new Timestamp(cal.getTimeInMillis());
        }
        return tsNew;
    }

    public Timestamp subtractMonths(Timestamp tsOriginal, int nAmount){
        Timestamp tsNew = null;
        if(null != tsOriginal && 0 != nAmount) {
            int nAmountNeg = nAmount * -1;
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(tsOriginal.getTime());
            cal.add(Calendar.MONTH, nAmountNeg);
            tsNew = new Timestamp(cal.getTimeInMillis());
        }
        return tsNew;
    }

    public Timestamp changeTimeStamp(String szRange){
        Timestamp tsNow = null;
        Timestamp tsRange = null;
        if(null != szRange) {
            switch (szRange) {
                case "1m":
                    tsNow = new Timestamp(Instant.now().toEpochMilli());
                    tsRange = subtractMonths(tsNow, 1);
                    break;
                case "3m":
                    tsNow = new Timestamp(Instant.now().toEpochMilli());
                    tsRange = subtractMonths(tsNow, 3);
                    break;
                case "6m":
                    tsNow = new Timestamp(Instant.now().toEpochMilli());
                    tsRange = subtractMonths(tsNow, 6);
                    break;
                case "1y":
                    tsNow = new Timestamp(Instant.now().toEpochMilli());
                    tsRange = subtractYears(tsNow, 1);
                    break;
                case "5y":
                    tsNow = new Timestamp(Instant.now().toEpochMilli());
                    tsRange = subtractYears(tsNow, 5);
                    break;
                case "10y":
                    tsNow = new Timestamp(Instant.now().toEpochMilli());
                    tsRange = subtractYears(tsNow, 10);
                    break;
                case "20y":
                    tsNow = new Timestamp(Instant.now().toEpochMilli());
                    tsRange = subtractYears(tsNow, 20);
                    break;
                default:
                    tsNow = new Timestamp(Instant.now().toEpochMilli());
                    tsRange = subtractYears(tsNow, 5);
            }
        }
        return tsRange;
    }
    public long StringToLong(String szValue, long lgDefault) {
        long lgReturn = 0;
        if(null != szValue && szValue.length() >0) {
            try {
                lgReturn = Long.valueOf(szValue);
            }catch(NumberFormatException NFExcep){
                lgReturn = lgDefault;
            }
        }
        return lgReturn;
    }

    public String convertMonth(String szMonth) {
        String szReturn = "";
        if (null != szMonth) {
            switch (szMonth) {
                case "January":
                    szReturn = "1";
                    break;
                case "February":
                    szReturn = "2";
                    break;
                case "March":
                    szReturn = "3";
                    break;
                case "April":
                    szReturn = "4";
                    break;
                case "May":
                    szReturn = "5";
                    break;
                case "June":
                    szReturn = "6";
                    break;
                case "July":
                    szReturn = "7";
                    break;
                case "August":
                    szReturn = "8";
                    break;
                case "September":
                    szReturn = "9";
                    break;
                case "October":
                    szReturn = "10";
                    break;
                case "November":
                    szReturn = "11";
                    break;
                case "December":
                    szReturn = "12";
                    break;
                default:
                    szReturn = "1";
            }
        }
        return szReturn;
    }
    public String removeBadChars(String szValue){
        String szReturn = "";
        if(null != szValue){
            szReturn = szValue.replaceAll("[^a-zA-Z0-9]%","");
        }
        return szReturn;
    }

    public Integer getYear(String szValue, String szPattern){
        Integer IntReturn = null;

        SimpleDateFormat formatter;
        if(null != szPattern)
        {
            formatter = new SimpleDateFormat(szPattern);
        }
        else
        {
            formatter = new SimpleDateFormat("MM/dd/yyyy");
        }
        try {
            if(null != szValue) {
                Timestamp tsTemp = new Timestamp ((formatter.parse(szValue)).getTime());
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(tsTemp.getTime());
                IntReturn = cal.get(Calendar.YEAR);
            }
        } catch (ParseException ParseExcep){}
        return IntReturn;
    }

    public Integer getMonth(String szValue, String szPattern){
        Integer IntReturn = null;

        SimpleDateFormat formatter;
        if(null != szPattern)
        {
            formatter = new SimpleDateFormat(szPattern);
        }
        else
        {
            formatter = new SimpleDateFormat("MM/dd/yyyy");
        }
        try {
            if(null != szValue) {
                Timestamp tsTemp = new Timestamp ((formatter.parse(szValue)).getTime());
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(tsTemp.getTime());
                IntReturn = cal.get(Calendar.MONTH);
            }
        } catch (ParseException ParseExcep){}
        return IntReturn;
    }

    public Integer getDay(String szValue, String szPattern){
        Integer IntReturn = null;

        SimpleDateFormat formatter;
        if(null != szPattern)
        {
            formatter = new SimpleDateFormat(szPattern);
        }
        else
        {
            formatter = new SimpleDateFormat("MM/dd/yyyy");
        }
        try {
            if(null != szValue) {
                Timestamp tsTemp = new Timestamp ((formatter.parse(szValue)).getTime());
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(tsTemp.getTime());
                IntReturn = cal.get(Calendar.DAY_OF_MONTH);
            }
        } catch (ParseException ParseExcep){}
        return IntReturn;
    }


}




