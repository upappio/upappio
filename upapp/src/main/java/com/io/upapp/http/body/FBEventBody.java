package com.io.upapp.http.body;

import java.util.List;

public class FBEventBody {


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

    private String event_name;
    private int event_time;
    private String event_id;
    private String event_source_url;
    private String action_source;
    private UserDataBean user_data;
    private CustomDataBean custom_data;
    private boolean opt_out;

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public int getEvent_time() {
        return event_time;
    }

    public void setEvent_time(int event_time) {
        this.event_time = event_time;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getEvent_source_url() {
        return event_source_url;
    }

    public void setEvent_source_url(String event_source_url) {
        this.event_source_url = event_source_url;
    }

    public String getAction_source() {
        return action_source;
    }

    public void setAction_source(String action_source) {
        this.action_source = action_source;
    }

    public UserDataBean getUser_data() {
        return user_data;
    }

    public void setUser_data(UserDataBean user_data) {
        this.user_data = user_data;
    }

    public CustomDataBean getCustom_data() {
        return custom_data;
    }

    public void setCustom_data(CustomDataBean custom_data) {
        this.custom_data = custom_data;
    }

    public boolean isOpt_out() {
        return opt_out;
    }

    public void setOpt_out(boolean opt_out) {
        this.opt_out = opt_out;
    }

    public static class UserDataBean {


        private String client_ip_address;
        private String client_user_agent;
        private String fbc;
        private String fbp;
        private List<String> em;
        private List<String> ph;

        public String getClient_ip_address() {
            return client_ip_address;
        }

        public void setClient_ip_address(String client_ip_address) {
            this.client_ip_address = client_ip_address;
        }

        public String getClient_user_agent() {
            return client_user_agent;
        }

        public void setClient_user_agent(String client_user_agent) {
            this.client_user_agent = client_user_agent;
        }

        public String getFbc() {
            return fbc;
        }

        public void setFbc(String fbc) {
            this.fbc = fbc;
        }

        public String getFbp() {
            return fbp;
        }

        public void setFbp(String fbp) {
            this.fbp = fbp;
        }

        public List<String> getEm() {
            return em;
        }

        public void setEm(List<String> em) {
            this.em = em;
        }

        public List<String> getPh() {
            return ph;
        }

        public void setPh(List<String> ph) {
            this.ph = ph;
        }
    }

    public static class CustomDataBean {

        private double value;
        private String currency;
        private String content_type;
        private List<String> content_ids;

        private List<ContentsBean> contents;


        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getContent_type() {
            return content_type;
        }

        public void setContent_type(String content_type) {
            this.content_type = content_type;
        }

        public List<String> getContent_ids() {
            return content_ids;
        }

        public void setContent_ids(List<String> content_ids) {
            this.content_ids = content_ids;
        }

        public List<ContentsBean> getContents() {
            return contents;
        }

        public void setContents(List<ContentsBean> contents) {
            this.contents = contents;
        }

        public static class ContentsBean {
            private String id;
            private Long quantity;
            private String delivery_category;

            private float item_price;
            private String title ;
            private String description;
            private String brand;

            public float getItem_price() {
                return item_price;
            }

            public void setItem_price(float item_price) {
                this.item_price = item_price;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getBrand() {
                return brand;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public Long getQuantity() {
                return quantity;
            }

            public void setQuantity(Long quantity) {
                this.quantity = quantity;
            }

            public String getDelivery_category() {
                return delivery_category;
            }

            public void setDelivery_category(String delivery_category) {
                this.delivery_category = delivery_category;
            }
        }
    }
}
