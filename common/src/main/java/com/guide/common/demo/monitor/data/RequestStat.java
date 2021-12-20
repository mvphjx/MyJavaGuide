package com.guide.common.demo.monitor.data;

import lombok.Data;

/**
 * 统计结果
 */
@Data
public class RequestStat
{
    private double maxResponseTime;
    private double minResponseTime;
    private double avgResponseTime;
    private double p999ResponseTime;
    private double p99ResponseTime;
    private long count;
    private long tps;
    //...省略getter/setter方法...
}
