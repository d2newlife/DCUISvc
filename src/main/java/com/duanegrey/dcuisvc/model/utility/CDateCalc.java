package com.duanegrey.dcuisvc.model.utility;

public class CDateCalc {

    long diffSeconds = 0;
    long diffMinutes = 0;
    long diffHours = 0;
    long diffDays = 0;
    boolean opSuccess = false;

    public CDateCalc() {
    }

    public CDateCalc(boolean bOpSuccess, long lgSeconds, long lgMinutes, long lgHours, long lgDays)
    {
        this.diffSeconds = lgSeconds;
        this.diffMinutes = lgMinutes;
        this.diffHours = lgHours;
        this.diffDays = lgDays;
        this.opSuccess = bOpSuccess;
    }

    public void setMulti(boolean bOpSuccess, long lgSeconds, long lgMinutes, long lgHours, long lgDays)
    {
        this.diffSeconds = lgSeconds;
        this.diffMinutes = lgMinutes;
        this.diffHours = lgHours;
        this.diffDays = lgDays;
        this.opSuccess = bOpSuccess;
    }

    public long getDiffSeconds() {
        return diffSeconds;
    }

    public void setDiffSeconds(long diffSeconds) {
        this.diffSeconds = diffSeconds;
    }

    public long getDiffMinutes() {
        return diffMinutes;
    }

    public void setDiffMinutes(long diffMinutes) {
        this.diffMinutes = diffMinutes;
    }

    public long getDiffHours() {
        return diffHours;
    }

    public void setDiffHours(long diffHours) {
        this.diffHours = diffHours;
    }

    public long getDiffDays() {
        return diffDays;
    }

    public void setDiffDays(long diffDays) {
        this.diffDays = diffDays;
    }

    public boolean isOpSuccess() {
        return opSuccess;
    }

    public void setOpSuccess(boolean opSuccess) {
        this.opSuccess = opSuccess;
    }
}



