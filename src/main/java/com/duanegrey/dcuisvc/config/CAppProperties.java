package com.duanegrey.dcuisvc.config;

import com.duanegrey.dcuisvc.util.CGenLib;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class CAppProperties {
    private String dataHost;
    private String dataPort;
    private String szReadTimeout;
    private String szConnectionTimeout;
    CGenLib genLib = new CGenLib();

    @Autowired
    public CAppProperties(Environment env) {
        this.dataHost = env.getProperty("DCUI_DATAHOST");
        this.dataPort = env.getProperty("DCUI_DATAPORT");
        this.szReadTimeout = env.getProperty("DCUI_READTIMEOUT");
        this.szConnectionTimeout = env.getProperty("DCUI_CONNECTTIMEOUT");
    }

    public String getDataHost() {
        return dataHost;
    }

    public String getDataPort() {
        return dataPort;
    }

    public long getLgReadTimeout() {
        return genLib.StringToLong(szReadTimeout,10000);
    }

    public long getLgConnectTimeout() {
        return genLib.StringToLong(szConnectionTimeout,10000);
    }

    @Override
    public String toString() {
        return "CAppProperties{" +
                "dataHost='" + dataHost + '\'' +
                ", dataPort='" + dataPort + '\'' +
                '}';
    }
}
