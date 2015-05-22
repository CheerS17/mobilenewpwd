package com.example.mobileonepwd.entity;

/**
 * Created by CheerS17 on 5/21/15.
 */
public class Info {

    private int id;
    private String phone;
    private String sitename;
    private String siteUsername;
    private int LCount;//字母
    private int NCount;//数字
    private int PCount;//标点

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setLCount(int LCount) {
        this.LCount = LCount;
    }

    public int getLCount() {
        return LCount;
    }

    public int getNCount() {
        return NCount;
    }

    public void setNCount(int NCount) {
        this.NCount = NCount;
    }

    public int getPCount() {
        return PCount;
    }

    public void setPCount(int PCount) {
        this.PCount = PCount;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getSiteUsername() {
        return siteUsername;
    }

    public void setSiteUsername(String siteUsername) {
        this.siteUsername = siteUsername;
    }
}
