package com.duanegrey.dcuisvc.model.utility;

public class CAudit {
    private String corelid;
    private String uid;

    public CAudit() {
    }

    public CAudit(String corelid, String uid) {
        this.corelid = corelid;
        this.uid = uid;
    }

    public String getCorelid() {
        return corelid;
    }

    public void setCorelid(String corelid) {
        this.corelid = corelid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

