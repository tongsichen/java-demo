package com.itheima.ui;

import java.util.ArrayList;
import java.util.Random;

public class CodeUtil {
    public static String getCode() {
        //生成一个随机验证码
        //char和String的区别:char代表单个字符;String代表一串字符序列
        ArrayList<Character> List = new ArrayList<>();
        //添加字母a-z  A到Z
        for (int i = 0; i < 26; i++) {
            List.add((char) ('a' + i));
            List.add((char) ('A' + i));
        }
        //打印集合
        String result = "";
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            int randomIndex = r.nextInt(List.size());//[0到52)
            char c = List.get(randomIndex);
            result = result + c;
        }
        //在后面拼接数字
        int number = r.nextInt(10);//[0,10);
        //把随机数据拼接到result后面
        result = result + number;
        //把字符串变成字符数组
        char[] chars = result.toCharArray();//[A,B,C,D,5]
        //在字符数组中生成一个随机索引
        int index = r.nextInt(chars.length);//[0,5)
        //让随机索引上的字符去跟数字做交换
        char temp = chars[index];
        chars[index] = chars[4];
        chars[4] = temp;
        //把字符数组再变回字符串
        String code = new String(chars);
        return code;
    }
}

