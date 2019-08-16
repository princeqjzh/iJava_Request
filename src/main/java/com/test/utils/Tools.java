package com.test.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Tools {
    public static String getCurrentTime(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        return simpleDateFormat.format(date);
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
