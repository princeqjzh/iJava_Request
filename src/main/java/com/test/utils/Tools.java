package com.test.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
    public static void main(String[] args) {
        System.out.println(Tools.genXCorID());
        System.out.println(Tools.getDateString(new Long(System.currentTimeMillis()).toString()));

    }

    public static String genXCorID() {
        return generateHexFour() + generateHexFour() + "-" + generateHexFour() + "-" + generateHexFour() + "-"
                + generateHexFour() + "-" + generateHexFour() + generateHexFour() + generateHexFour();
    }

    public static String generateHexFour() {
        String s = Double.toHexString(Math.floor((1 + Math.random()) * 0x10000)).replace("0x1.", "").replace("p16", "");
        return s;
    }

    /**
     * Millisecond string to date
     *
     * @param millisecond: Millisecond string of the time
     * @return date: Date string of the time.
     */
    public static String getDateString(String millisecond) {
        String milSecond = parseNumber(millisecond);
        return long2date(milSecond);
    }

    public static String parseNumber(String origStr) {
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(origStr);
        String number = matcher.replaceAll("");
        return number;
    }

    public static String long2date(String milisecond) {
        long timestamp = new Long(milisecond).longValue();
        Date date = new Date(timestamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    public static String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    public static String getRandomIp() {

        // ip范围
        int[][] range = {{607649792, 608174079}, // 36.56.0.0-36.63.255.255
                {1038614528, 1039007743}, // 61.232.0.0-61.237.255.255
                {1783627776, 1784676351}, // 106.80.0.0-106.95.255.255
                {2035023872, 2035154943}, // 121.76.0.0-121.77.255.255
                {2078801920, 2079064063}, // 123.232.0.0-123.235.255.255
                {-1950089216, -1948778497}, // 139.196.0.0-139.215.255.255
                {-1425539072, -1425014785}, // 171.8.0.0-171.15.255.255
                {-1236271104, -1235419137}, // 182.80.0.0-182.92.255.255
                {-770113536, -768606209}, // 210.25.0.0-210.47.255.255
                {-569376768, -564133889}, // 222.16.0.0-222.95.255.255
        };

        Random rdint = new Random();
        int index = rdint.nextInt(10);
        String ip = num2ip(range[index][0] + new Random().nextInt(range[index][1] - range[index][0]));
        return ip;
    }

    /*
     * 将十进制转换成IP地址
     */
    public static String num2ip(int ip) {
        int[] b = new int[4];
        String x = "";
        b[0] = (int) ((ip >> 24) & 0xff);
        b[1] = (int) ((ip >> 16) & 0xff);
        b[2] = (int) ((ip >> 8) & 0xff);
        b[3] = (int) (ip & 0xff);
        x = Integer.toString(b[0]) + "." + Integer.toString(b[1]) + "." + Integer.toString(b[2]) + "." + Integer.toString(b[3]);

        return x;
    }

    /**
     * 生成随机身份证号
     *
     * @return cardSN
     */
    public static String createIdcard() {
        String cardSN = "";
        int checkSum = 0;
        String[] provinceCode = {
                "11", "12", "13", "14", "15",
                "21", "22", "23",
                "31", "32", "33", "34", "35", "36", "37",
                "41", "42", "43", "44", "45", "46",
                "50", "51", "52", "53", "54",
                "61", "62", "63", "64", "65",
                "81", "82"
        };
        String[] cityCode = {
                "01", "02", "03", "04", "05", "06", "07", "08", "09",
                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
                "30", "31", "32", "33", "34", "35", "36", "37", "38", "39"
        };
        String[] villageCode = {
                "01", "02", "03", "04", "05", "06", "07", "08", "09",
                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
                "80", "81", "82", "83", "84", "85", "86", "87", "88", "89"
        };
        String[] birthYear = {
                "194", "195", "196", "197", "198"
        };
        String[] birthMonth = {
                "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
        };
        String[] birthDay = {
                "01", "02", "03", "04", "05", "06", "07", "08", "09",
                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                "20", "21", "22", "23", "24", "25", "26", "27", "28"
        };
        String[] oneCode = {
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
        };
        int[] fx = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        String[] checkCode = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
        cardSN += getRandomFromArray(provinceCode);//create province
        cardSN += getRandomFromArray(cityCode);//create city
        cardSN += getRandomFromArray(villageCode);//create village
        cardSN += getRandomFromArray(birthYear);
        cardSN += getRandomFromArray(oneCode);
        cardSN += getRandomFromArray(birthMonth);
        cardSN += getRandomFromArray(birthDay);
        cardSN += getRandomFromArray(oneCode);
        cardSN += getRandomFromArray(oneCode);
        cardSN += getRandomFromArray(oneCode);
        for (int i = 0; i < 17; i++) {
            checkSum += Integer.parseInt(String.valueOf(cardSN.charAt(i))) * fx[i];
        }
        cardSN += checkCode[checkSum % 11];
        return cardSN;
    }

    /**
     * 从数组中得到随机字符串
     *
     * @param strArray String[]
     * @return String
     */
    private static String getRandomFromArray(String[] strArray) {
        Random random = new Random();
        return strArray[random.nextInt(strArray.length)];
    }

}
