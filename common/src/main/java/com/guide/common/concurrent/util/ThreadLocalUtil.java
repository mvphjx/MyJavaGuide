package com.guide.common.concurrent.util;

import java.util.Map;

/**
 * ThreadLocal封装
 * 配合线程池使用时，要注意清空 设置的值
 * 否则可能会影响后续逻辑
 */
public class ThreadLocalUtil
{
    /**
     * 线程局部变量
     */
    private static final ThreadLocal<Map<String, String>> threadInfo = new ThreadLocal();
    public static final String DataSourceKey = "DataSourceKey";

    public static String getValue(String key)
    {
        Map<String, String> map = threadInfo.get();
        if (map.isEmpty())
        {
            return null;
        }
        return map.get(key);
    }

    public static void setValue(String key, String value)
    {
        Map<String, String> map = threadInfo.get();
        map.put(key, value);
    }

}
