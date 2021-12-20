package com.guide.common.demo.monitor.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求信息
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestInfo
{
    private String apiName;
    private double responseTime;
    private long timestamp;



}
