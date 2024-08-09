package com.io.upapp.http.body;

public class KWEventBody {
    private String upUuid;

    private String devKey;

    public String getUpUuid() {
        return upUuid;
    }

    public void setUpUuid(String upUuid) {
        this.upUuid = upUuid;
    }

    public String getDevKey() {
        return devKey;
    }

    public void setDevKey(String devKey) {
        this.devKey = devKey;
    }

    private String access_token;
    private String clickid;
    private String event_name;
    private int is_attributed;
    private String mmpcode;
    private String pixelId;
    private String pixelSdkVersion;
    private PropertiesBean properties;
    private boolean testFlag;
    private String third_party;
    private boolean trackFlag;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getClickid() {
        return clickid;
    }

    public void setClickid(String clickid) {
        this.clickid = clickid;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public int getIs_attributed() {
        return is_attributed;
    }

    public void setIs_attributed(int is_attributed) {
        this.is_attributed = is_attributed;
    }

    public String getMmpcode() {
        return mmpcode;
    }

    public void setMmpcode(String mmpcode) {
        this.mmpcode = mmpcode;
    }

    public String getPixelId() {
        return pixelId;
    }

    public void setPixelId(String pixelId) {
        this.pixelId = pixelId;
    }

    public String getPixelSdkVersion() {
        return pixelSdkVersion;
    }

    public void setPixelSdkVersion(String pixelSdkVersion) {
        this.pixelSdkVersion = pixelSdkVersion;
    }

    public PropertiesBean getProperties() {
        return properties;
    }

    public void setProperties(PropertiesBean properties) {
        this.properties = properties;
    }

    public boolean isTestFlag() {
        return testFlag;
    }

    public void setTestFlag(boolean testFlag) {
        this.testFlag = testFlag;
    }

    public String getThird_party() {
        return third_party;
    }

    public void setThird_party(String third_party) {
        this.third_party = third_party;
    }

    public boolean isTrackFlag() {
        return trackFlag;
    }

    public void setTrackFlag(boolean trackFlag) {
        this.trackFlag = trackFlag;
    }

    public static class PropertiesBean {
        private String content_id;
        private String content_type;
        private String content_name;

        public String getContent_id() {
            return content_id;
        }

        public void setContent_id(String content_id) {
            this.content_id = content_id;
        }

        public String getContent_type() {
            return content_type;
        }

        public void setContent_type(String content_type) {
            this.content_type = content_type;
        }

        public String getContent_name() {
            return content_name;
        }

        public void setContent_name(String content_name) {
            this.content_name = content_name;
        }
    }
}
