package com.example.mobileonepwd.entity;

/**
 * Created by CheerS17 on 5/20/15.
 */
public class User {

    private String phone;
    private int uid;
    private String pwd;
    private String token;


    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }


//    public User(String userName, String token, int id) {
//        super();
//        this.userName = userName;
//        this.token = userPtwd;
//        this.userId = userId;
//    }
//
//    public User(String userName, String userPwd) {
//        super();
//        this.userName = userName;
//        this.userPwd = userPwd;
//    }
}
