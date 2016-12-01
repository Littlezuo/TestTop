package com.topfine.malltest.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint({"SimpleDateFormat", "DefaultLocale"})
public class StrUtils {
    public static final String orange = "#EF8100";
    public static final String orange_dark = "#EA8918";
    public static final String green = "#1aa607";
    public static final String blue = "#770000ff";
    /**
     * 获取当前的网络状态  -1：没有网络  1：WIFI网络 2：wap网络3：net网络
     *
     * @param context
     * @return
     */
    public static final int CMNET = 3;
    public static final int CMWAP = 2;
    public static final int WIFI = 1;
    // bt字节参考量
    private static final float SIZE_BT = 1024L;
    // KB字节参考量
    private static final float SIZE_KB = SIZE_BT * 1024.0f;
    // MB字节参考量
    private static final float SIZE_MB = SIZE_KB * 1024.0f;
    // GB字节参考量
    private static final float SIZE_GB = SIZE_MB * 1024.0f;
    // TB字节参考量
    // private static final float SIZE_TB=SIZE_GB * 1024.0f;
    // BigDecimal四舍五入精度为2
    private static final int SACLE = 2;
    // 缓冲的大小
    private static final int BUFF_SIZE = 1024;

    // 判断是否转int类型
    public static boolean isFormatInteger(String str) {
        if (str != null && !str.equals("") && isGigital(str)) {
            return true;
        }
        return false;
    }

    /**
     * @param str 字符串
     * @return 如果字符串是数字返回ture，反正false
     */
    public static boolean isGigital(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isGigital = pattern.matcher(str);
        if (!isGigital.matches()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断字符串是不是float型
     */
    public static boolean isFloat(String str) {
        Pattern pattern = Pattern.compile("[0-9]*(\\.?)[0-9]*");
        Matcher isFloat = pattern.matcher(str);
        if (isFloat.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param url 保存文件的文字
     * @return 文件名
     */
    public static String getFileName(String url) {
        String fileName = null;
        if (url != null && url.contains("/")) {
            String[] data = url.split("/");
            fileName = data[data.length - 1];
        }
        return fileName;
    }

    /**
     * @param style 类型
     * @return 用逗号，或者分号截取字符串前两个(这个方法用于类型的字符串截取)
     */
    public static String get2InString(String style) {
        Pattern pattern = Pattern.compile("[,;]");
        String[] actors = pattern.split(style);
        StringBuffer buffer = new StringBuffer();
        if (actors.length <= 1) {
            buffer.append(actors[0]);
        } else if (actors.length == 2) {
            buffer.append(actors[0]);
            buffer.append(",");
            buffer.append(actors[1]);
        } else if (actors.length >= 3) {
            buffer.append(actors[0]);
            buffer.append(",");
            buffer.append(actors[1]);
            buffer.append(",");
            buffer.append(actors[2]);
        }
        return buffer.toString();
    }

    /**
     * @param str 字符串
     * @return 字符串转化MD5
     */
    public static String calcMd5(String str) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(str.getBytes());
            return toHexString(algorithm.digest(), "");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param file 文件
     * @return 文件转换MD5
     */
    public static String calcMd5(File file) {
        FileInputStream in = null;
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer;
            byteBuffer = ch
                    .map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            algorithm.update(byteBuffer);
            return toHexString(algorithm.digest(), "");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 上面的辅助类
    public static String toHexString(byte[] bytes, String separator) {
        StringBuilder hexString = new StringBuilder();
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        for (byte b : bytes) {
            hexString.append(hexDigits[b >> 4 & 0xf]);
            hexString.append(hexDigits[b & 0xf]);
        }
        return hexString.toString();
    }

    // 去掉字符串中的空格、回车、换行符、制表符
    public static String replaceBlank(String str) {
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            String after = m.replaceAll("");
            return after;
        } else {
            return null;
        }
    }

    // 换域名
    public static String replaceRealmName(String newRealmName,
                                          String oldRealmName, String source) {
        if (oldRealmName == null) {
            return source;
        }
        StringBuffer bf = new StringBuffer("");
        int index = -1;
        while ((index = source.indexOf(oldRealmName)) != -1) {
            bf.append(source.substring(0, index) + newRealmName);
            source = source.substring(index + oldRealmName.length());
            index = source.indexOf(oldRealmName);
        }
        bf.append(source);
        return bf.toString();
    }

    // 获取新闻的时间
    public static String getDescriptionTime(String tm) {
        Long timestamp = Long.parseLong(tm) * 1000;
        String date = new SimpleDateFormat("yyyy年MM月dd日")
                .format(new Date(timestamp));
        return date;
    }

    // 获取今天的时间
    public static String getTodayTime() {
        long todayDate = new Date().getTime();
        String date = new SimpleDateFormat("yyyy年MM月dd日")
                .format(new Date(todayDate));
        return date;
    }

    // 获取昨天的时间
    public static String getYesterdayTime() {
        long todayDate = new Date().getTime();
        long yesterdayDate = todayDate - 24 * 60 * 60 * 1000;
        String date = new SimpleDateFormat("yyyy年MM月dd日")
                .format(new Date(yesterdayDate));
        return date;
    }

    // // 获取opudid
    // public static String getOpenUdid() {
    // OpenUDID_manager.sync(PPStvApp.getPPSInstance());
    // String openUDID = null;
    // if (OpenUDID_manager.isInitialized()) {
    // openUDID = OpenUDID_manager.getOpenUDID();
    // }
    // return openUDID;
    // }

    // 获取旧分辨率
    public static String getOldLcd(Activity activity) {
        StringBuffer buffer = new StringBuffer();
        int density = 0;
        int widthPixels = 0;
        int heightPixels = 0;
        DisplayMetrics dm = new DisplayMetrics();
        if (dm != null) {
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            density = dm.densityDpi;
            widthPixels = dm.widthPixels;
            heightPixels = dm.heightPixels;
        }
        buffer.append(heightPixels);
        buffer.append("*");
        buffer.append(widthPixels);
        buffer.append(",");
        buffer.append(density);
        buffer.append("dpi");
        return buffer.toString();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    // 获取屏幕长宽
    public static int[] getScreenSizeArray(Activity activity) {
        int[] size = new int[2];
        int widthPixels = 0;
        int heightPixels = 0;
        DisplayMetrics dm = new DisplayMetrics();
        if (dm != null) {
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            widthPixels = dm.widthPixels;
            heightPixels = dm.heightPixels;
            size[0] = widthPixels;
            size[1] = heightPixels;
        }
        return size;
    }

    // 获取新分辨率
    public static String getNewLcd(Activity activity) {
        StringBuffer buffer = new StringBuffer();
        int widthPixels = 0;
        int heightPixels = 0;
        DisplayMetrics dm = new DisplayMetrics();
        if (dm != null) {
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            widthPixels = dm.widthPixels;
            heightPixels = dm.heightPixels;
        }
        buffer.append(heightPixels);
        buffer.append("*");
        buffer.append(widthPixels);
        return buffer.toString();
    }

    // 获取手机屏幕宽高乘积
    public static int getScreenSize(Activity activity) {
        int widthPixels = 0;
        int heightPixels = 0;
        DisplayMetrics dm = new DisplayMetrics();
        if (dm != null) {
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            widthPixels = dm.widthPixels;
            heightPixels = dm.heightPixels;
        }
        return widthPixels * heightPixels;
    }

    // 获取当前时间戳
    public static String getTimesTamp() {
        long timestamp = System.currentTimeMillis();
        return String.valueOf(timestamp);
    }


    // 屏幕密度
    public static float getdensity(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        return density;
    }

    // 把字符串转换成UTF-8的格式
    public static String stringToUTF(String str) {
        if (str != null && !str.equals("")) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // 把字符串转换成GBK的格式
    public static String stringToGBK(String str) {
        if (str != null && !str.equals("")) {
            try {
                return URLDecoder.decode(str, "GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // 把字符串编码成GBK的格式
    public static String stringUTF8ToGBK(String str) {
        if (str != null && !str.equals("")) {
            try {
                return URLEncoder.encode(str, "GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // 获取系统时间
    public static String getSystemTime() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = format.format(date);
        return result;

    }

    // 把文件转变字符串
    public static String file2String(File file, String encoding) {
        InputStreamReader reader = null;
        StringWriter writer = new StringWriter();
        try {
            if (encoding == null || "".equals(encoding.trim())) {
                reader = new InputStreamReader(new FileInputStream(file));
            } else {
                reader = new InputStreamReader(new FileInputStream(file),
                        encoding);
            }
            // 将输入流写入输出流
            char[] buffer = new char[BUFF_SIZE];
            int n = 0;
            while (-1 != (n = reader.read(buffer))) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return writer.toString();
    }

    // 把inputstream转换为字符串（方法一）
    public static String getStr1FromInputstream(InputStream input) {
        String result = null;
        int i = -1;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            while ((i = input.read()) != -1) {
                baos.write(i);
            }
            result = baos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 把inputstream转换为字符串（方法二）
    public static String getStr2FromInputstream(InputStream input) {
        int i = -1;
        String result = null;
        byte[] b = new byte[1024];
        StringBuffer sb = new StringBuffer();
        try {
            while ((i = input.read(b)) != -1) {
                sb.append(new String(b, 0, i));
            }
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 获取手机型号
    public static String getLocalModel() {
        String model = android.os.Build.MODEL;
        if (model == null) {
            model = "";
        }
        return model;
    }

    // 获取手机系统版本
    public static String getLocalSystemVersion() {
        String version = android.os.Build.VERSION.RELEASE;
        if (version == null) {
            version = "";
        }
        return version;

    }

    // 获取手机厂商
    public static String getLocalManufacturer() {
        String manufacturer = android.os.Build.MANUFACTURER;
        if (manufacturer == null) {
            manufacturer = "";
        }
        return manufacturer;
    }

    // 获取包名
    public static String getPackageName(Activity activity) {

        String packageName = null;
        try {
            packageName = String.valueOf(activity.getPackageManager()
                    .getPackageInfo(activity.getPackageName(), 0).packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageName;
    }

    // 判断设备是否越狱
    public static boolean getIsJailBreak() {
        for (String str : new String[]{"/system/bin/", "/system/xbin/",
                "/data/local/xbin/", "/data/local/bin/", "/system/sd/xbin/"}) {
            if (new File(str + "su").exists()) {
                return true;
            }
        }
        return false;
    }

    // 获取手机硬件属性
    public static String[] getTotalHardwareMessage() {
        String result[] = new String[3];
        String str1 = "/proc/cpuinfo";
        String str2 = null;
        FileReader localFileReader = null;
        BufferedReader localBufferedReader = null;
        try {
            localFileReader = new FileReader(str1);
            localBufferedReader = new BufferedReader(localFileReader);
            while ((str2 = localBufferedReader.readLine()) != null) {
                if (str2.contains("Processor")) {
                    if (str2.contains(":")) {
                        String[] arrayOfString = str2.split(":");
                        if (arrayOfString.length == 2) {
                            result[0] = arrayOfString[1];
                            if (result[0].length() > 32 && result[0] != null) {
                                result[0] = result[0].substring(0, 32);
                            }
                        }
                    }
                }
                if (str2.contains("Features")) {
                    if (str2.contains(":")) {
                        String[] arrayOfString = str2.split(":");
                        if (arrayOfString.length == 2) {
                            result[1] = arrayOfString[1];
                            if (result[1].length() > 50 && result[1] != null) {
                                result[1] = result[1].substring(0, 50);
                            }
                        }
                    }
                }
                if (str2.contains("Hardware")) {
                    if (str2.contains(":")) {
                        String[] arrayOfString = str2.split(":");
                        if (arrayOfString.length == 2) {
                            result[2] = arrayOfString[1];
                            if (result[2].length() > 32 && result[2] != null) {
                                result[2] = result[2].substring(0, 32);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (localBufferedReader != null) {
                try {
                    localBufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (localFileReader != null) {
                try {
                    localFileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    // 获取时区
    public static String getTimeArea() {
        return String.valueOf(
                TimeZone.getDefault().getOffset(new Date().getTime() / 1000))
                .toString();
    }

    // 获取useragent
    public static String getUserAgent(Context context) {
        String userAgent = null;
        WebView webView = new WebView(context);
        WebSettings settings = webView.getSettings();
        if (settings != null) {
            userAgent = settings.getUserAgentString();
        }
        return userAgent;
    }

    public static byte[] gbk2utf8(String string) {
        char c[] = string.toCharArray();
        byte[] fullByte = new byte[3 * c.length];
        for (int i = 0; i < c.length; i++) {
            int m = (int) c[i];
            String word = Integer.toBinaryString(m);

            StringBuffer sb = new StringBuffer();
            int len = 16 - word.length();
            for (int j = 0; j < len; j++) {
                sb.append("0");
            }
            sb.append(word);
            sb.insert(0, "1110");
            sb.insert(8, "10");
            sb.insert(16, "10");
            String s1 = sb.substring(0, 8);
            String s2 = sb.substring(8, 16);
            String s3 = sb.substring(16);
            byte b0 = Integer.valueOf(s1, 2).byteValue();
            byte b1 = Integer.valueOf(s2, 2).byteValue();
            byte b2 = Integer.valueOf(s3, 2).byteValue();
            byte[] bf = new byte[3];
            bf[0] = b0;
            fullByte[i * 3] = bf[0];
            bf[1] = b1;
            fullByte[i * 3 + 1] = bf[1];
            bf[2] = b2;
            fullByte[i * 3 + 2] = bf[2];

        }
        return fullByte;
    }

    /**
     * 获取有颜色的文字，这里默认为橙色
     *
     * @param str 文字内容
     * @return
     */
    public static String getHtmlColorString(String color, String str) {
        StringBuffer sb = new StringBuffer();
        sb.append("<font color='" + color + "'>");
        sb.append(str);
        sb.append("</font>");
        return sb.toString();
    }

    /**
     * 根据分数不同返回不同颜色的文字
     *
     * @return
     */
    public static String getRatingColorString(String vote) {
        StringBuffer sb = new StringBuffer();
        if (vote == null || vote.equals("") || !StrUtils.isFloat(vote)) {
            vote = "0";
        }
        int score = (int) Float.parseFloat(vote);
        switch (score) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                sb.append("<font color='" + "#f3ad07" + "'>");
                break;
            case 7:
                sb.append("<font color='" + "#ef8203" + "'>");
                break;
            case 8:
                sb.append("<font color='" + "#ff7510" + "'>");
                break;
            default:
                sb.append("<font color='" + "#fe4223" + "'>");
                break;
        }
        sb.append(vote);
        sb.append("</font>");
        return sb.toString();
    }

    public static String getNewDetailsFn(String str, String name) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(str.substring(0, 2));
        buffer.append("年");
        buffer.append(str.substring(2, 4));
        buffer.append("月");
        buffer.append(str.substring(4, 6));
        buffer.append("日");
        buffer.append("-");
        buffer.append(name);
        return buffer.toString();
    }

    // 获取BP设置重试地址
    public static String getBpSetRetryUrl(String retryName, String sourceStr) {
        String retryUrl = StrUtils.replaceRealmName(retryName,
                "bip.ppstream.com", sourceStr);
        return retryUrl;
    }

    // 获取列表重试地址
    public static String getRetryUrl(String retryName, String sourceStr) {
        String retryUrl = StrUtils.replaceRealmName(retryName,
                "list1.ppstream.com", sourceStr);
        return retryUrl;
    }

    // 获取BP播放重试地址
    public static String getBpPlayRetryUrl(String retryName, String sourceStr) {
        String retryUrl = StrUtils.replaceRealmName(retryName, "dp.ugc.pps.tv",
                sourceStr);
        return retryUrl;
    }

    // 根据传入的字节数，返回对应的字符串
    public static String getReadableSize(long length) {
        if (length >= 0 && length < SIZE_BT) {
            // Math.round四舍五入
            return (double) (Math.round(length * 10) / 10.0) + "B";
        } else if (length >= SIZE_BT && length < SIZE_KB) {
            // //length/SIZE_BT+"KB";
            return (double) (Math.round((length / SIZE_BT) * 10) / 10.0) + "KB";
        } else if (length >= SIZE_KB && length < SIZE_MB) {
            // length/SIZE_KB+"MB";
            return (double) (Math.round((length / SIZE_KB) * 10) / 10.0) + "MB";
        } else if (length >= SIZE_MB && length < SIZE_GB) {
            // bigdecimal这个对象进行数据相互除
            BigDecimal longs = new BigDecimal(Double.valueOf(length + "")
                    .toString());
            BigDecimal sizeMB = new BigDecimal(Double.valueOf(SIZE_MB + "")
                    .toString());
            String result = longs.divide(sizeMB, SACLE,
                    BigDecimal.ROUND_HALF_UP).toString();
            return result + "GB";
        } else {
            // bigdecimal这个对象进行数据相互除
            BigDecimal longs = new BigDecimal(Double.valueOf(length + "")
                    .toString());
            BigDecimal sizeMB = new BigDecimal(Double.valueOf(SIZE_GB + "")
                    .toString());
            String result = longs.divide(sizeMB, SACLE,
                    BigDecimal.ROUND_HALF_UP).toString();
            return result + "TB";
        }
    }

    // 获取string转int
    public static int string2Int(String str) {
        if (str != null && !str.equals("") && isGigital(str)) {
            return Integer.parseInt(str);
        }
        return 0;
    }

    // 获取cup数目
    public static int getNumCores() {
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }
        try {
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(new CpuFilter());
            return files.length;
        } catch (Exception e) {
            return 1;
        }
    }

    public static int getAPNType(Context context) {
        int netType = -1;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            //            Log.e("networkInfo.getExtraInfo()", "networkInfo.getExtraInfo() is " + networkInfo.getExtraInfo());
            if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
                netType = CMNET;
            } else {
                netType = CMWAP;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = WIFI;
        }
        return netType;
    }


    /**
     * 获取货币格式文字
     *
     * @param money
     * @return
     */
    public static String getMoneyString(double money) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        format.setCurrency(Currency.getInstance(Locale.CHINA));
        String result = format.format(money);
        return result;
    }

    /**
     * 获取货币格式文字
     *
     * @return
     */
    public static String getMoneySymbol() {

        return Currency.getInstance(Locale.CHINA).getSymbol();

    }

    /**
     * 获取application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(),
                        PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getVersion(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "0.0.0";
        }
    }

    /**
     * 获取状态栏/通知栏的高度
     *
     * @return
     */
    private static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }

    /**
     * 获取屏幕的宽高
     */
    public static Point getScreenSize(Context context) {
        Point point = new Point();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //不含虚拟按键
        windowManager.getDefaultDisplay().getSize(point);
        //包含虚拟按键
        //windowManager.getDefaultDisplay().getRealSize(point);
        //屏幕宽度
        int height = point.y;
        //屏幕高度
        int width = point.x;

        return point;
    }


    @SuppressLint("DefaultLocale")
    public static String getDateTimeDisplay(long timeInMillis) {
        if (timeInMillis <= 0) {
            return "";
        }
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(timeInMillis);

        String strTime = String.format("%d-%s-%s %s:%s:%s",
                time.get(Calendar.YEAR), pad(time.get(Calendar.MONTH) + 1),
                pad(time.get(Calendar.DAY_OF_MONTH)),
                pad(time.get(Calendar.HOUR_OF_DAY)),
                pad(time.get(Calendar.MINUTE)), pad(time.get(Calendar.SECOND)));
        return strTime;
    }

    public static String getDateTimeYYYYMMDDHHMMDisplay(long timeInMillis) {
        if (timeInMillis <= 0) {
            return "";
        }
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(timeInMillis);

        String strTime = String.format("%d-%s-%s %s:%s",
                time.get(Calendar.YEAR), pad(time.get(Calendar.MONTH) + 1),
                pad(time.get(Calendar.DAY_OF_MONTH)),
                pad(time.get(Calendar.HOUR_OF_DAY)),
                pad(time.get(Calendar.MINUTE)));
        return strTime;
    }

    /**
     * 时刻显示(当天的显示'今天')
     *
     * @param timeInMillis
     * @return
     */
    public static String getTimeSpecialDisplay(long timeInMillis) {
        if (timeInMillis <= 0) {
            return "";
        }
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(timeInMillis);

        if (isToday(timeInMillis)) {
            return String.format("今天 %s:%s",
                    pad(time.get(Calendar.HOUR_OF_DAY)),
                    pad(time.get(Calendar.MINUTE)));
        } else {
            return String.format("%d-%s-%s %s:%s", time.get(Calendar.YEAR),
                    pad(time.get(Calendar.MONTH) + 1),
                    pad(time.get(Calendar.DAY_OF_MONTH)),
                    pad(time.get(Calendar.HOUR_OF_DAY)),
                    pad(time.get(Calendar.MINUTE)));
        }
    }


    public static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    /**
     * 判断某个时间是否是今天
     *
     * @param timeInMillis
     * @return
     */
    public static boolean isToday(long timeInMillis) {
        boolean result = false;
        Calendar calToday = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);
        if (calToday.get(Calendar.YEAR) == cal.get(Calendar.YEAR)
                && calToday.get(Calendar.MONTH) == cal.get(Calendar.MONTH)
                && calToday.get(Calendar.DAY_OF_MONTH) == cal
                .get(Calendar.DAY_OF_MONTH)) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    @SuppressLint("DefaultLocale")
    public static String getDateDisplay(long timeInMillis) {
        if (timeInMillis <= 0) {
            return "";
        }
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(timeInMillis);

        String strTime = String.format("%d-%s-%s", time.get(Calendar.YEAR),
                pad(time.get(Calendar.MONTH) + 1),
                pad(time.get(Calendar.DAY_OF_MONTH)));
        return strTime;
    }


    @SuppressLint("DefaultLocale")
    public static String getDateMonthDisplay(long timeInMillis) {
        if (timeInMillis <= 0) {
            return "";
        }
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(timeInMillis);

        String strTime = String.format("%d年%s月", time.get(Calendar.YEAR),
                pad(time.get(Calendar.MONTH) + 1));
        return strTime;
    }

    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    /**
     * 判断当前应用程序处于前台还是后台
     *
     * @param context
     * @return
     */
    public static boolean isBackground(Context context) {
        if (context == null)
            return true;
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager
                .getRunningAppProcesses();
        if (appProcessInfos != null && appProcessInfos.size() > 0) {
            for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos) {
                if (appProcessInfo.processName.equals(context.getPackageName())) {
                    if (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                        Logger.i("Background", String.format(
                                "Background App:", appProcessInfo.processName));
                        return true;
                    } else {
                        Logger.i("Background", String.format(
                                "Foreground App:", appProcessInfo.processName));
                        return false;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 匹配格式： 11位手机号码 3-4位区号，7-8位直播号码，1－4位分机号 如：12345678901、1234-12345678-1234
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        // String regex = "^13/d{9}||15[8,9]/d{8}$";
        String regex = "((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|"
                + "(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|"
                + "(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber.trim());

        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    /**
     * 匹配格式： 3-4位区号，7-8位直播号码，1－4位分机号 如：12345678901、1234-12345678-1234
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isTelphoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        // String regex = "^13/d{9}||15[8,9]/d{8}$";
        String regex = "(^((\\d{7,8})|\\(?(\\d{4}|\\d{3})\\)?-?(\\d{7,8})|"
                + "\\(?(\\d{4}|\\d{3})\\)?-?(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|"
                + "(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber.trim());

        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context
     * @return true 表示开启
     */
    public static final boolean isGpsOPen(final Context context) {
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        // boolean network =
        // locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps) {
            return true;
        }
        return false;
    }

    /**
     * 校验网址
     *
     * @param urlAddress
     * @return
     */
    public static boolean isUrl(String urlAddress) {

        String lRegex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

        Pattern p = Pattern.compile(lRegex);
        Matcher m;
        m = p.matcher(urlAddress);
        boolean matches = m.matches();

        return matches;
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


 private static void sortMapByValues(Map<String, Integer> aMap) {

     Set<Map.Entry<String, Integer>> mapEntries = aMap.entrySet();

     System.out.println("Values and Keys before sorting ");
     for (Map.Entry<String, Integer> entry : mapEntries) {
         System.out.println(entry.getValue() + " - " + entry.getKey());
     }

     // used linked list to sort, because insertion of elements in linked list is faster than an array list.
     List<Map.Entry<String, Integer>> aList = new LinkedList<Map.Entry<String, Integer>>(mapEntries);

     // sorting the List
     Collections.sort(aList, new Comparator<Map.Entry<String, Integer>>() {

         @Override
         public int compare(Map.Entry<String, Integer> ele1,
                            Map.Entry<String, Integer> ele2) {

             return ele1.getValue().compareTo(ele2.getValue());
         }
     });

     // Storing the list into Linked HashMap to preserve the order of insertion.
     Map<String, Integer> aMap2 = new LinkedHashMap<String, Integer>();
     for (Map.Entry<String, Integer> entry : aList) {
         aMap2.put(entry.getKey(), entry.getValue());
     }

     // printing values after soring of map
     System.out.println("Value " + " - " + "Key");
     for (Map.Entry<String, Integer> entry : aMap2.entrySet()) {
         System.out.println(entry.getValue() + " - " + entry.getKey());
     }
 }

}
