package com.topfine.malltest.constant;

/**
 * 提供网络访问数据相关的url地址
 * Created by shkstart on 2016/8/12 0012.
 */
public class UrlConfig {
    public final static int STG = 1;  //测试环境
    public final static int PRD = 2;  //生产环境

    private static String host = "192.168.56.1";

    private static final String BASEURL = "http://" + host + ":8080/Invest/";

    public static final String PRODUCT = BASEURL + "product";
    public static final String TEST = BASEURL + "test";
    public static final String INDEX = BASEURL + "index";
    public static final String LOGIN = BASEURL + "login";
    public static String TestLogin = "https://test.weiyancao.cn:8443/1.0/Testapi/test";
//    public static String TestLogin = "https://api.smartstorechina.com/1.0/User/login";

    public static void setProEnvironment(int envir){
        if(envir == STG) {
            host = "192.168.26.1";
        }else if(envir == PRD) {
            host = "test";
        }
    }

}
