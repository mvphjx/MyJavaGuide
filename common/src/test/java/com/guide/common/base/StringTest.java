package com.guide.common.base;

public class StringTest
{
    public static void main(String[] args)
    {
        System.out.println(String.format("(UNIT_CODE like '%s')", "11"));
        System.out.println(String.format("(UNIT_CODE like '%s%%')", "11"));
    }
}
