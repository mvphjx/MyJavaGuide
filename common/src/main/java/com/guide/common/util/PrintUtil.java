package com.guide.common.util;

import cn.hutool.json.JSONUtil;

public class PrintUtil
{
    public static void println(Object obj)
    {
        System.out.println(JSONUtil.toJsonStr(JSONUtil.parse(obj), 2));
    }
}
