package com.guide.common.demo.monitor.dao;

import com.guide.common.demo.monitor.data.RequestInfo;

import java.util.List;
import java.util.Map;

/**
 * 持久化
 *
 * @author hjx
 * @version 1.0
 * @date 2021/12/20 23:48
 */
public interface IMetricsStorage
{
    void saveRequestInfo(RequestInfo requestInfo);

    List<RequestInfo> getRequestInfos(String apiName, long startTimeInMillis, long endTimeInMillis);

    Map<String, List<RequestInfo>> getRequestInfos(long startTimeInMillis, long endTimeInMillis);
}
