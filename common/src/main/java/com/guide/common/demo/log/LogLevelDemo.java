package com.guide.common.demo.log;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * 通过org.slf4j.event.Level我们可以看到一共有五个等级，按优先级从低到高依次为：
 * TRACE：一般用于记录调用链路，比如方法进入时打印xxx start；
 * DEBUG：个人觉得它和 trace 等级可以合并，如果一定要区分，可以用来打印方法的出入参；
 * INFO：默认级别，一般用于记录代码执行时的关键信息；
 * WARN：当代码执行遇到预期外场景，但它不影响后续执行时，可以使用；
 * ERROR：出现异常，以及代码无法兜底时使用；
 */
@Slf4j
public class LogLevelDemo
{
    @Resource
    private Object rpcService;

    public String querySomething(String request)
    {
        // 使用 trace 标识这个方法调用情况
        log.trace("querySomething start");
        // 使用 debug 记录出入参
        log.debug("querySomething request={}", request);
        String response = null;
        try
        {
            if (rpcService != null)
            {
                // 使用 info 标识重要节点
                log.info("rpcService ok, rpcService={}", rpcService);
            }
            else
            {
                // 使用 warn 标识程序调用有预期外错误，但这个错误在可控范围内
                log.warn("rpcService failed, rpcService={},request={}", rpcService, request);
            }
        }
        catch (Exception e)
        {
            // 使用 error 记录程序的异常信息
            log.error("querySomething rpcService.call abnormal, request={}, exception={}", request, e.getMessage(), e);
        }
        // 使用 debug 记录出入参
        log.debug("querySomething response={}", response);
        // 使用 trace 标识这个方法调用情况
        log.trace("querySomething end");
        return response;
    }
}
