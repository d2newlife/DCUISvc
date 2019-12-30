package com.duanegrey.dcuisvc.model;

public class CSeriesOutput {
    private String name;
    private Object[] data;

    public CSeriesOutput(String name, Object[] data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public Object[] getData() {
        return data;
    }
}
