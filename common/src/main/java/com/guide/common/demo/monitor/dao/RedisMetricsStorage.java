package com.guide.common.demo.monitor.dao;

import com.guide.common.demo.monitor.data.RequestInfo;

import java.util.List;
import java.util.Map;

/**
 * Redis存储实现
 * @author hjx
 * @version 1.0
 * @date 2021/12/20 23:50
 */
public class RedisMetricsStorage implements IMetricsStorage
{
    @Override
    public void saveRequestInfo(RequestInfo requestInfo)
    {

    }

    @Override
    public List<RequestInfo> getRequestInfos(String apiName, long startTimeInMillis, long endTimeInMillis)
    {
        return null;
    }

    @Override
    public Map<String, List<RequestInfo>> getRequestInfos(long startTimeInMillis, long endTimeInMillis)
    {
        return null;
    }
}
