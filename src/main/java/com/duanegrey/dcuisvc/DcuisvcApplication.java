package com.duanegrey.dcuisvc;

import com.duanegrey.dcuisvc.config.CAppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class DcuisvcApplication implements CommandLineRunner {

    private final Environment env;

    @Autowired
    public DcuisvcApplication(Environment env){
        this.env = env;
    }

    public static void main(String[] args) {
        SpringApplication.run(DcuisvcApplication.class, args);
    }

    @Override
    public void run(String[] args) {
        String szOutput = validate();
        if(szOutput != null && szOutput.length()>0){
            System.out.println(szOutput);
        }
    }

    private String validate(){
        String szReturn = null;
        StringBuffer sbMessage = new StringBuffer();
        String dataHost = env.getProperty("DCUI_DATAHOST");
        String dataPort = env.getProperty("DCUI_DATAPORT");
        String szReadTimeout = env.getProperty("DCUI_READTIMEOUT");
        String szConnectionTimeout = env.getProperty("DCUI_CONNECTTIMEOUT");
        if(null == dataHost || dataHost.length() < 1) {
            sbMessage.append("Please check environment variable DCUI_DATAHOST "+ dataHost +" . If this it not set service calls will fail.\n");
        }
        if(null == dataPort || dataPort.length() < 1) {
            sbMessage.append("Please check environment variable DCUI_DATAPORT "+ dataHost +" . If this it not set service calls will fail.\n");
        }else{
            try {
                Integer.valueOf(dataPort);
            }catch(NumberFormatException NFExcep) {
                sbMessage.append("Please check environment variable DCUI_DATAPORT " + dataPort + " . If this it not set to a proper port number services calls can fail.\n");
            }
        }
        if(null == szReadTimeout || szReadTimeout.length() < 1) {
            sbMessage.append("Please check environment variable DCUI_READTIMEOUT "+ szReadTimeout +" . If this it not set a default value will be applied.\n");
        }else{
            try {
                Integer intValue = Integer.valueOf(szReadTimeout);
                if(intValue.intValue() > 10){
                    sbMessage.append("Please check environment variable DCUI_READTIMEOUT " + szReadTimeout + " . Beware setting of values over 10 seconds this value determine how long a service will wait to read data.\n");
                }
            }catch(NumberFormatException NFExcep) {
                sbMessage.append("Please check environment variable DCUI_READTIMEOUT " + szReadTimeout + " . If this it not set to a proper number(10 or below), this will determine how long the services calls wait for a response.\n");
            }
        }
        if(null == szConnectionTimeout || szConnectionTimeout.length() < 1) {
            sbMessage.append("Please check environment variable DCUI_CONNECTTIMEOUT "+ szConnectionTimeout +" . If this it not set a default value will be applied.\n");
        }else{
            try {
                Integer intValue = Integer.valueOf(szConnectionTimeout);
                if(intValue.intValue() > 10){
                    sbMessage.append("Please check environment variable DCUI_CONNECTTIMEOUT " + szConnectionTimeout + " . Beware setting of values over 10 seconds this value determine how long a will wait to connect.\n");
                }
            }catch(NumberFormatException NFExcep) {
                sbMessage.append("Please check environment variable DCUI_CONNECTTIMEOUT " + szConnectionTimeout + " . If this it not set to a proper number(10 or below), this will determine how long the services calls wait for a connection.\n");
            }
        }
        if(sbMessage.length() > 1){
            szReturn = sbMessage.toString();
        }
        return szReturn;
    }
}