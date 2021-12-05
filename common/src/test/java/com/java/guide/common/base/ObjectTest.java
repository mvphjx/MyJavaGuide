package com.java.guide.common.base;

import cn.hutool.core.util.ObjectUtil;

public class ObjectTest
{
    public static void main(String[] args)
    {
        boolean equals = ObjectUtil.equals((byte)0, (byte) 0);
        System.out.println(equals);
    }
}
