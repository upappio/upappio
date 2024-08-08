package com.io.upapp.http.body;

public class DetailBody {

    private String upUuid;

    private String platform;
    private String  advChannelId;
    private String eventName;

    private String devKey;

    public String getDevKey() {
        return devKey;
    }

    public void setDevKey(String devKey) {
        this.devKey = devKey;
    }

    public String getUpUuid() {
        return upUuid;
    }

    public void setUpUuid(String upUuid) {
        this.upUuid = upUuid;
    }

    public String getAvChannelId() {
        return advChannelId;
    }

    public void setAvChannelId(String avChannelId) {
        this.advChannelId = avChannelId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
