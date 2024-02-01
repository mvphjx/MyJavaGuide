package com.guide.common.util;

import cn.hutool.core.date.BetweenFormatter;

public class TimeUtil
{
    /**
     * 格式化耗时
     *
     * @param costs 毫秒
     * @return
     */
    public static String formatTimeCosts(Long costs)
    {
        BetweenFormatter betweenFormater = new BetweenFormatter(costs, BetweenFormatter.Level.MILLISECOND, 3);
        String format = betweenFormater.format();
        return format;
    }

    public static void main(String[] args)
    {
        long costs = 1000 * 60 * 10;
        System.out.println(TimeUtil.formatTimeCosts(costs));
    }

}
