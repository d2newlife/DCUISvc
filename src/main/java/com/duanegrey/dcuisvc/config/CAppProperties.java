package com.duanegrey.dcuisvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class CAppProperties {
    private String dataHost;
    private String dataPort;
    private long lgReadTimeout;
    private long lgConnectTimeout;

    @Autowired
    public CAppProperties(Environment env) {
        this.dataHost = env.getProperty("DCUI_DATAHOST");
        this.dataPort = env.getProperty("DCUI_DATAPORT");
        this.dataHost = env.getProperty("DCUI_READTIMEOUT");
        this.dataPort = env.getProperty("DCUI_CONNECTTIMEOUT");
    }

    public String getDataHost() {
        return dataHost;
    }

    public String getDataPort() {
        return dataPort;
    }

    public long getLgReadTimeout() {
        return lgReadTimeout;
    }

    public long getLgConnectTimeout() {
        return lgConnectTimeout;
    }

    @Override
    public String toString() {
        return "CAppProperties{" +
                "dataHost='" + dataHost + '\'' +
                ", dataPort='" + dataPort + '\'' +
                '}';
    }
}
