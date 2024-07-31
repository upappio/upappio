package com.io.upapp.http.body;

public class AppBody {

    private String packageName;

    private String upUuid;

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
