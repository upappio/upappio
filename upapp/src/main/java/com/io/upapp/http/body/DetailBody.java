package com.io.upapp.http.body;

public class DetailBody {

    private String upUuid;

    private String  avChannelId;
    private String eventName;

    public String getUpUuid() {
        return upUuid;
    }

    public void setUpUuid(String upUuid) {
        this.upUuid = upUuid;
    }

    public String getAvChannelId() {
        return avChannelId;
    }

    public void setAvChannelId(String avChannelId) {
        this.avChannelId = avChannelId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
