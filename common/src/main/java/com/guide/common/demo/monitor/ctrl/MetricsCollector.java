package com.guide.common.demo.monitor.ctrl;

import cn.hutool.core.util.StrUtil;
import com.guide.common.demo.monitor.dao.RedisMetricsStorage;
import com.guide.common.demo.monitor.data.RequestInfo;
import com.guide.common.demo.monitor.dao.IMetricsStorage;
import com.guide.common.demo.monitor.service.ConsoleReporter;
import com.guide.common.demo.monitor.service.EmailReporter;

/**
 * API
 *
 * @author hjx
 * @version 1.0
 * @date 2021/12/20 23:48
 */

public class MetricsCollector
{
    /**
     * 基于接口而非实现编程
     * 可以通过框架依赖注入
     */
    private IMetricsStorage metricsStorage;

    public MetricsCollector(IMetricsStorage metricsStorage)
    {
        this.metricsStorage = metricsStorage;
    }

    //用一个函数代替了最小原型中的两个函数
    public void recordRequest(RequestInfo requestInfo)
    {
        if (requestInfo == null || StrUtil.isBlank(requestInfo.getApiName()))
        {
            return;
        }
        metricsStorage.saveRequestInfo(requestInfo);
    }

    public static void main(String[] args)
    {
        IMetricsStorage storage = new RedisMetricsStorage();
        ConsoleReporter consoleReporter = new ConsoleReporter(storage);
        consoleReporter.startRepeatedReport(60, 60);
        EmailReporter emailReporter = new EmailReporter(storage);
        emailReporter.addToAddress("wangzheng@xzg.com");
        emailReporter.startDailyReport();
        MetricsCollector collector = new MetricsCollector(storage);
        collector.recordRequest(new RequestInfo("register", 123, 10234));
        collector.recordRequest(new RequestInfo("register", 223, 11234));
        collector.recordRequest(new RequestInfo("register", 323, 12334));
        collector.recordRequest(new RequestInfo("login", 23, 12434));
        collector.recordRequest(new RequestInfo("login", 1223, 14234));
        try { Thread.sleep(100000); } catch (InterruptedException e) { e.printStackTrace(); }
    }
}

