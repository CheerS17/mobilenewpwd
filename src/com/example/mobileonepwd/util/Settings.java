package com.example.mobileonepwd.util;

/**
 * Created by leaf on 14-5-5.
 */
public class Settings {
    private static final String SERVER_URL = "http://219.216.101.69:8080/arcompus";
//    private static final String SERVER_URL = "http://www.developeryang.com/arcompus";

    private static final String LOGIN_URL = "/entry/login";
    private static final String REGISTER_URL = "/entry/register";
    private static final String CODE_URL = "/entry/getcode";
    private static final String CHECK_CODE_URL = "/entry/verify";
    private static final String CONTACT_LIST_URL = "/buddy/list";
    private static final String CONTACT_NICKNAME_URL = "/buddy/comment";
    private static final String CONTACT_REMOVE_URL = "/buddy/delete";
    private static final String CONTACT_INFO_URL = "/buddy/view";
    private static final String CHANGE_PWD_URL = "/user/changepasswd";

    private static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Settings.token = token;
    }

    public static String getServerUrl() {
        return SERVER_URL;
    }

    public static String getLoginUrl() {
        return SERVER_URL + LOGIN_URL;
    }

    public static String getRegisterUrl() {
        return SERVER_URL + REGISTER_URL;
    }

    public static String getCodeUrl() {
        return SERVER_URL + CODE_URL;
    }

    public static String getCheckCodeUrl() {
        return SERVER_URL + CHECK_CODE_URL;
    }

    public static String getContactListUrl() {
        return SERVER_URL + CONTACT_LIST_URL;
    }

    public static String getContactNicknameUrl() {
        return SERVER_URL + CONTACT_NICKNAME_URL;
    }

    public static String getContactRemoveUrl() {
        return SERVER_URL + CONTACT_REMOVE_URL;
    }

    public static String getContactInfoUrl() {
        return SERVER_URL + CONTACT_INFO_URL;
    }

    public static String getChangePwdUrl() {
        return SERVER_URL + CHANGE_PWD_URL;
    }
}
