package com.jhl.util;

import java.util.Random;

/**
 * Created by xin.fang on 14-11-4.
 * 随机字符串或数字的工具栏
 * 注意为了便于区别，排除了如下字符:l,o, O, 0, I
 */
public class RandomUtil {

   private static final char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H','J', 'K', 'L', 'M', 'N', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4',
            '5', '6', '7', '8', '9'};

    private static final char[] strSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H','J', 'K', 'L', 'M', 'N', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private static final char[] numSequence = {'1', '2', '3', '4','5', '6', '7', '8', '9'};

    /**
     * 随机字符串和数字的组合
     * @param length
     * @return
     */
    public static String random(int length) {
        Random random = new Random();
        String randStr = "";
        int arrayLength = codeSequence.length;
        for (int i = 0; i < length; i++) {
            String rand = String.valueOf(codeSequence[random.nextInt(arrayLength)]);
            randStr += rand;
        }
        return randStr;
    }


    /**
     * 随机字符串
     * @param length
     * @return
     */
    public static String randomStr(int length) {
        Random random = new Random();
        String randStr = "";
        int arrayLength = strSequence.length;
        for (int i = 0; i < length; i++) {
            String rand = String.valueOf(strSequence[random.nextInt(arrayLength)]);
            randStr += rand;
        }
        return randStr;
    }

    /**
     * 随机数字
     * @param length
     * @return
     */
    public static String randomNum(int length) {
        Random random = new Random();
        String randStr = "";
        int arrayLength = numSequence.length;
        for (int i = 0; i < length; i++) {
            String rand = String.valueOf(numSequence[random.nextInt(arrayLength)]);
            randStr += rand;
        }
        return randStr;
    }
}
