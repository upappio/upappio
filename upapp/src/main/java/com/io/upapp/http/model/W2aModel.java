package com.io.upapp.http.model;

import java.util.List;

public class W2aModel {


    private SiteBean site;
    private InfoBean info;

    public SiteBean getSite() {
        return site;
    }

    public void setSite(SiteBean site) {
        this.site = site;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class SiteBean {

        private String createBy;
        private String createTime;
        private String updateBy;
        private String updateTime;
        private String remark;
        private int id;
        private int userId;
        private int appId;
        private int advId;
        private String apkUrl;
        private String description;
        private String developer;
        private String downloadText;
        private String commentText;
        private String landingUrl;
        private int pageView;
        private String delFlag;
        private int star5;
        private int star4;
        private int star3;
        private int star2;
        private int star1;
        private double star;
        private String tags;
        private String webUrl;
        private String email;
        private String privacy;
        private String automatic;
        private String advDome;
        private String googleDome;
        private String pageType;
        private String downloadPage;
        private String name;
        private List<Integer> starList;

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getAppId() {
            return appId;
        }

        public void setAppId(int appId) {
            this.appId = appId;
        }

        public int getAdvId() {
            return advId;
        }

        public void setAdvId(int advId) {
            this.advId = advId;
        }

        public String getApkUrl() {
            return apkUrl;
        }

        public void setApkUrl(String apkUrl) {
            this.apkUrl = apkUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDeveloper() {
            return developer;
        }

        public void setDeveloper(String developer) {
            this.developer = developer;
        }

        public String getDownloadText() {
            return downloadText;
        }

        public void setDownloadText(String downloadText) {
            this.downloadText = downloadText;
        }

        public String getCommentText() {
            return commentText;
        }

        public void setCommentText(String commentText) {
            this.commentText = commentText;
        }

        public String getLandingUrl() {
            return landingUrl;
        }

        public void setLandingUrl(String landingUrl) {
            this.landingUrl = landingUrl;
        }

        public int getPageView() {
            return pageView;
        }

        public void setPageView(int pageView) {
            this.pageView = pageView;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }

        public int getStar5() {
            return star5;
        }

        public void setStar5(int star5) {
            this.star5 = star5;
        }

        public int getStar4() {
            return star4;
        }

        public void setStar4(int star4) {
            this.star4 = star4;
        }

        public int getStar3() {
            return star3;
        }

        public void setStar3(int star3) {
            this.star3 = star3;
        }

        public int getStar2() {
            return star2;
        }

        public void setStar2(int star2) {
            this.star2 = star2;
        }

        public int getStar1() {
            return star1;
        }

        public void setStar1(int star1) {
            this.star1 = star1;
        }

        public double getStar() {
            return star;
        }

        public void setStar(double star) {
            this.star = star;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getWebUrl() {
            return webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPrivacy() {
            return privacy;
        }

        public void setPrivacy(String privacy) {
            this.privacy = privacy;
        }

        public String getAutomatic() {
            return automatic;
        }

        public void setAutomatic(String automatic) {
            this.automatic = automatic;
        }

        public String getAdvDome() {
            return advDome;
        }

        public void setAdvDome(String advDome) {
            this.advDome = advDome;
        }

        public String getGoogleDome() {
            return googleDome;
        }

        public void setGoogleDome(String googleDome) {
            this.googleDome = googleDome;
        }

        public String getPageType() {
            return pageType;
        }

        public void setPageType(String pageType) {
            this.pageType = pageType;
        }

        public String getDownloadPage() {
            return downloadPage;
        }

        public void setDownloadPage(String downloadPage) {
            this.downloadPage = downloadPage;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Integer> getStarList() {
            return starList;
        }

        public void setStarList(List<Integer> starList) {
            this.starList = starList;
        }
    }

    public static class InfoBean {

        private String createBy;
        private String createTime;
        private String updateBy;
        private String updateTime;
        private String remark;
        private int id;
        private int userId;
        private String icon;
        private String name;
        private String shortName;
        private String domain;
        private String attributionWindowPeriod;
        private int isTest;
        private int status;
        private String apkKey;
        private String devKey;
        private String packageName;
        private String language;

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getAttributionWindowPeriod() {
            return attributionWindowPeriod;
        }

        public void setAttributionWindowPeriod(String attributionWindowPeriod) {
            this.attributionWindowPeriod = attributionWindowPeriod;
        }

        public int getIsTest() {
            return isTest;
        }

        public void setIsTest(int isTest) {
            this.isTest = isTest;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getApkKey() {
            return apkKey;
        }

        public void setApkKey(String apkKey) {
            this.apkKey = apkKey;
        }

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

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }
    }
}
