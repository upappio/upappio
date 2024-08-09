package com.io.upapp.http.body;

import java.util.List;

public class TTEventBody {

    private String upUuid;

    private String devKey;


    private String event_source;
    private String event_source_id;
    private List<DataBean> data;

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

    public String getEvent_source() {
        return event_source;
    }

    public void setEvent_source(String event_source) {
        this.event_source = event_source;
    }

    public String getEvent_source_id() {
        return event_source_id;
    }

    public void setEvent_source_id(String event_source_id) {
        this.event_source_id = event_source_id;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }


    public static class DataBean {

        private String event;
        private int event_time;
        private String event_id;
        private UserBean user;
        private PropertiesBean properties;

        public String getEvent() {
            return event;
        }

        public void setEvent(String event) {
            this.event = event;
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

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public PropertiesBean getProperties() {
            return properties;
        }

        public void setProperties(PropertiesBean properties) {
            this.properties = properties;
        }

        public static class UserBean {

            private String external_id;
            private String phone;
            private String email;
            private String ip;
            private String user_agent;
            private String locale;

            public String getExternal_id() {
                return external_id;
            }

            public void setExternal_id(String external_id) {
                this.external_id = external_id;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getIp() {
                return ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }

            public String getUser_agent() {
                return user_agent;
            }

            public void setUser_agent(String user_agent) {
                this.user_agent = user_agent;
            }

            public String getLocale() {
                return locale;
            }

            public void setLocale(String locale) {
                this.locale = locale;
            }
        }

        public static class PropertiesBean {


            private String currency;
            private double value;
            private String content_type;
            private String description;
            private List<ContentsBean> contents;

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public double getValue() {
                return value;
            }

            public void setValue(double value) {
                this.value = value;
            }

            public String getContent_type() {
                return content_type;
            }

            public void setContent_type(String content_type) {
                this.content_type = content_type;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public List<ContentsBean> getContents() {
                return contents;
            }

            public void setContents(List<ContentsBean> contents) {
                this.contents = contents;
            }

            public static class ContentsBean {

                private double price;
                private int quantity;
                private String content_id;
                private String content_name;
                private String content_category;
                private String brand;

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public int getQuantity() {
                    return quantity;
                }

                public void setQuantity(int quantity) {
                    this.quantity = quantity;
                }

                public String getContent_id() {
                    return content_id;
                }

                public void setContent_id(String content_id) {
                    this.content_id = content_id;
                }

                public String getContent_name() {
                    return content_name;
                }

                public void setContent_name(String content_name) {
                    this.content_name = content_name;
                }

                public String getContent_category() {
                    return content_category;
                }

                public void setContent_category(String content_category) {
                    this.content_category = content_category;
                }

                public String getBrand() {
                    return brand;
                }

                public void setBrand(String brand) {
                    this.brand = brand;
                }
            }
        }
    }
}
