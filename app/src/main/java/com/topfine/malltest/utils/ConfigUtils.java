package com.topfine.malltest.utils;

import android.text.TextUtils;

import com.topfine.malltest.base.MyApplication;



/**
 * Created by wuyunan on 16/8/18.
 */

public class ConfigUtils {


    private static final String KEY_CONFIG_AREA_VERSION = "CONFIG_AREA_VERSION";
    private static final String KEY_CONFIG_AREA_NEEDUPDATE = "CONFIG_AREA_NEEDUPDATE";
    private static final String KEY_CONFIG_AREA_CODE = "CONFIG_AREA_CODE";
    private static final String KEY_CONFIG_CURRENT_AREA_NAME = "CONFIG_CURRENT_AREA_NAME";
    private static final String KEY_CONFIG_MOCK = "KEY_CONFIG_MOCK";
    private static final String KEY_CONFIG_CURPROJ = "KEY_CONFIG_CURPROJ";
    private static final String KEY_CONFIG_CURRENT_AREA_TOTALNAME = "KEY_CONFIG_CURRENT_AREA_TOTALNAME";
    /**
     * 客服热线号码
     */
    private static final String KEY_CONFIG_ABOUT_URL = "KEY_CONFIG_ABOUT_URL";
    /**
     * 客服热线号码
     */
    private static final String KEY_CONFIG_SERVICE_PHONE_NUMBER = "KEY_CONFIG_SERVICE_PHONE_NUMBER";
    /**
     * 百度推送channel ID
     */
    private static final String KEY_CONFIG_PUSH_TARGET_ID = "KEY_CONFIG_PUSH_TARGET_ID";
    private static final String KEY_CONFIG_REGPROTOURL = "KEY_CONFIG_REGPROTOURL";
    private static final String KEY_DEFAULT_SEARCHWORD = "KEY_DEFAULT_SEARCHWORD";

    public static String areaCodeDefaultValue = String.format(Constants.AREA_CODE_FORMAT, 0, 0, 0, 0);


    private static final String KEY_CONFIG_CART_EDITMODE = "KEY_CONFIG_CART_EDITMODE";


    /**
     * 是否需要更新地域列表数据
     *
     * @return
     */
    public static boolean isNeedRefreshArea() {
        return SpUtil.getBoolean(MyApplication.getContext(), KEY_CONFIG_AREA_NEEDUPDATE, true);
    }

    /**
     * 更新地域列表数据版本号
     *
     * @param areaVer 版本号
     */
    public static void updateAreaVersion(int areaVer) {
        int lastVersion = SpUtil.getInt(MyApplication.getContext(), KEY_CONFIG_AREA_VERSION, 0);
        if (areaVer > lastVersion && !isNeedRefreshArea()) {
            setAreaNeedUpdate(true);
        }
        SpUtil.setInt(MyApplication.getContext(), KEY_CONFIG_AREA_VERSION, areaVer);
    }

    /**
     * 获取地域列表数据版本号
     *
     * @return 版本号
     */
    public static int getAreaVersion() {
        int lastVersion = SpUtil.getInt(MyApplication.getContext(), KEY_CONFIG_AREA_VERSION, 0);
        return lastVersion;
    }

    /**
     * 是否需要跳转选择area
     *
     * @return
     */
    public static boolean shouldChooseArea() {
        return TextUtils.equals(getAreaCode(), areaCodeDefaultValue);
    }

    /**
     * 设置是否需要更新地域列表
     *
     * @param needUpdate
     */
    public static void setAreaNeedUpdate(boolean needUpdate) {
        SpUtil.setBoolean(MyApplication.getContext(), KEY_CONFIG_AREA_NEEDUPDATE, needUpdate);
    }

    /**
     * 是否需要Mock数据
     */
    public static boolean isMockServer() {
        return SpUtil.getBoolean(MyApplication.getContext(), KEY_CONFIG_MOCK, false);
    }

    /**
     * 设置是否需要Mock数据
     *
     * @param isMock
     */
    public static void setMockServer(boolean isMock) {
        SpUtil.setBoolean(MyApplication.getContext(), KEY_CONFIG_MOCK, isMock);
    }

    /**
     * 设置当前选择地域值
     *
     * @param cityCode
     * @param zoneCode
     * @param addressId
     * @param provinceCode
     */
    public static void setAreaCode(int provinceCode, int cityCode, int zoneCode, long addressId) {
        String value = String.format(Constants.AREA_CODE_FORMAT, provinceCode, cityCode, zoneCode, addressId);

        SpUtil.setString(MyApplication.getContext(), KEY_CONFIG_AREA_CODE, value);
    }

    /**
     * 保存当前的项目名称
     *
     * @param curProName
     */
    public static void setCurProjName(String curProName) {
        SpUtil.setString(MyApplication.getContext(), KEY_CONFIG_CURPROJ, curProName);
    }

    public static String getCurProjName() {
        return SpUtil.getString(MyApplication.getContext(), KEY_CONFIG_CURPROJ, "");
    }

    /**
     * 获取地域值
     * <p>
     * 默认值0_0_0_0
     *
     * @return
     */
    public static String getAreaCode() {
        return SpUtil.getString(MyApplication.getContext(), KEY_CONFIG_AREA_CODE, areaCodeDefaultValue);
    }

    /**
     * 获取地域值集合
     * <p>
     * 默认值0_0_0_0
     *
     * @return
     */
    public static String[] getAreaCodeList() {

        String[] codes = null;
        String string = getAreaCode();
        if (!TextUtils.isEmpty(string)) {
            codes = string.split("_");
        }
        return codes;
    }

    /**
     * 获取当前省
     *
     * @return
     */
    public static long getProvinceCode() {

        String[] codes = getAreaCodeList();
        return Long.valueOf(codes[0]);
    }

    /**
     * 获取当前市
     *
     * @return
     */
    public static long getCityCode() {

        String[] codes = getAreaCodeList();
        return Long.valueOf(codes[1]);
    }

    /**
     * 获取当前县
     *
     * @return
     */
    public static long getZoneCode() {

        String[] codes = getAreaCodeList();
        return Long.valueOf(codes[2]);
    }

    /**
     * 获取当前的addr
     *
     * @return
     */
    public static long getAddrCode() {

        String[] codes = getAreaCodeList();
        return Long.valueOf(codes[3]);
    }

    /**
     * 获取当前选中的地域显示名字
     * <p>
     * 默认值0_0_0_0
     *
     * @return
     */
    public static String getCurrentAreaName() {
        String defaultvalue = "";
        return SpUtil.getString(MyApplication.getContext(), KEY_CONFIG_CURRENT_AREA_NAME, defaultvalue);
    }

    /**
     * 设置当前选择地域显示名字
     *
     * @param name
     */
    public static void setCurrentAreaName(String name) {

        SpUtil.setString(MyApplication.getContext(), KEY_CONFIG_CURRENT_AREA_NAME, name);
    }

    /**
     * 设置当前选择地域三级字符串 格式 ： 省 市 县
     *
     * @param name
     */
    public static void setCurrentTotalAreaName(String name) {

        SpUtil.setString(MyApplication.getContext(), KEY_CONFIG_CURRENT_AREA_TOTALNAME, name);
    }

    /**
     * 获取当前选中的地域显示名字
     * <p>
     *
     * @return
     */
    public static String getCurrentAreaTotalName() {
        String defaultvalue = "";
        return SpUtil.getString(MyApplication.getContext(), KEY_CONFIG_CURRENT_AREA_TOTALNAME, defaultvalue);
    }

    /**
     * 是否需要更新地域列表数据
     *
     * @return
     */
    public static boolean isLogin() {
        return !TextUtils.isEmpty(SpUtil.getToken(MyApplication.getContext()));
    }

    /**
     * 是否需要更新地域列表数据
     *
     * @return
     */
    public static void setLogOut() {
        setAddrZero();
        SpUtil.removeToken(MyApplication.getContext());
        SpUtil.removeMobile(MyApplication.getContext());
    }

    public static void setAddrZero() {
        String[] areaCodeList = getAreaCodeList();
        setAreaCode(Integer.valueOf(areaCodeList[0]), Integer.valueOf(areaCodeList[1]), Integer.valueOf(areaCodeList[2]), 0);
        setCurProjName("");
    }


    /**
     * 购物车编辑模式
     *
     * @return
     */
    public static boolean isCartEditMode() {
        return SpUtil.getBoolean(MyApplication.getContext(), KEY_CONFIG_CART_EDITMODE, false);
    }

    /**
     * 购物车设置编辑模式
     *
     * @return
     */
    public static void setCartEditMode(boolean isEditMode) {
        SpUtil.setBoolean(MyApplication.getContext(), KEY_CONFIG_CART_EDITMODE, isEditMode);
    }

    /**
     * 获取客服号码
     *
     * @return
     */
    public static String getServicePhoneNumber() {
        return SpUtil.getString(MyApplication.getContext(), KEY_CONFIG_SERVICE_PHONE_NUMBER, "");
    }


    /**
     * 获取格式化客服号码
     *
     * @return
     */
    public static String getFormatServcePhoneNumber() {
        StringBuilder builder = new StringBuilder(getServicePhoneNumber());
        return builder.insert(3, "-").toString();
    }


    /**
     * 保存客服号码
     *
     * @param phone
     */
    public static void setServicePhoneNumber(String phone) {
        SpUtil.setString(MyApplication.getContext(), KEY_CONFIG_SERVICE_PHONE_NUMBER, phone);
    }

    /**
     * 保存关于的url
     */
    public static void setAboutUrl(String aboutUrl) {
        SpUtil.setString(MyApplication.getContext(), KEY_CONFIG_ABOUT_URL, aboutUrl);
    }

    /**
     * 获取关于的url
     */
    public static String getAboutUrl() {
        return SpUtil.getString(MyApplication.getContext(), KEY_CONFIG_ABOUT_URL, "");
    }

    /**
     * 获取百度推送channel ID
     *
     * @param channelId
     */
    public static void setPushTargetId(String channelId) {
        SpUtil.setString(MyApplication.getContext(), KEY_CONFIG_PUSH_TARGET_ID, channelId);
    }

    /**
     * 获取百度推送channel ID
     *
     * @return channel ID
     */
    public static String getPushTargetId() {
        return SpUtil.getString(MyApplication.getContext(), KEY_CONFIG_PUSH_TARGET_ID, "");

    }

    public static void setRegProToUrl(String regProToUrl) {
        SpUtil.setString(MyApplication.getContext(), KEY_CONFIG_REGPROTOURL, regProToUrl);
    }

    public static String getRegProToUrl() {
        return SpUtil.getString(MyApplication.getContext(), KEY_CONFIG_REGPROTOURL, "");
    }


    public static void setDefaultSearWord(String defaultSearWord) {
        SpUtil.setString(MyApplication.getContext(), KEY_DEFAULT_SEARCHWORD, defaultSearWord);
    }

    public static String getDefaultSearWord() {
        return SpUtil.getString(MyApplication.getContext(), KEY_DEFAULT_SEARCHWORD, "");
    }
}
