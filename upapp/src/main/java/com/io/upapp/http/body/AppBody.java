package com.io.upapp.http.body;

import java.io.Serializable;

public class AppBody implements Serializable {

    private static final long serialVersionUID = 2L;
    private String packageName;

    private String upUuid;

    private String devKey;

    public String getDevKey() {
        return devKey;
    }

    public void setDevKey(String devKey) {
        this.devKey = devKey;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getUpUuid() {
        return upUuid;
    }

    public void setUpUuid(String upUuid) {
        this.upUuid = upUuid;
    }

    public AppBody(String packageName) {
        this.packageName = packageName;
    }

    public AppBody(String packageName, String upUuid) {
        this.packageName = packageName;
        this.upUuid = upUuid;
    }
}
