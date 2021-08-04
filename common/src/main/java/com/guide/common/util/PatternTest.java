package com.guide.common.util;

import java.util.regex.Pattern;

/**
 * 正则表达式校验金额最多保留两位小数
 */
public class PatternTest
{
    /**
     * ([1-9]{1}\d*)  非0开头整数
     * (0{1})         0开头整数，必须只有一位
     * (\.\d{1,2})?  匹配1-2位小数，或者没有小数
     */
    private static final Pattern PATTERN = Pattern.compile("^(([1-9]{1}\\d*)|(0{1}))(\\.\\d{1,2})?$");

    public static void main(String[] args)
    {

        System.out.println("5 === " + PATTERN.matcher("5").matches());
        System.out.println("5. === " + PATTERN.matcher("5.").matches());
        System.out.println("5.1 === " + PATTERN.matcher("5.1").matches());
        System.out.println("5.12 === " + PATTERN.matcher("5.12").matches());
        System.out.println("5.123 === " + PATTERN.matcher("5.123").matches());
        System.out.println("0.12 === " + PATTERN.matcher("0.12").matches());
    }
}
