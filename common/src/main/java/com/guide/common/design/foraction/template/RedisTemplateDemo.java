package com.guide.common.design.foraction.template;

import cn.hutool.core.util.ObjectUtil;

import java.util.concurrent.TimeUnit;

/**
 * Spring 提供了很多 Template 类，比如，JdbcTemplate、RedisTemplate、RestTemplate。
 * 尽管都叫作 xxxTemplate，但它们并非基于模板模式来实现的，而是基于回调来实现的，确切地说应该是同步回调。
 * 而同步回调从应用场景上很像模板模式，所以，在命名上，这些类使用 Template（模板）这个单词作为后缀。
 */
public class RedisTemplateDemo
{
    /**
     * RedisTemplate 通过回调的机制，将不变的执行流程抽离出来，放到模板方法 execute() 中，
     * 将可变的部分设计成回调 RedisCallback，由用户来定制。
     */
    public <T> T execute(RedisCallback<T> action)
    {
        return null;
    }

    /**
     * set() 函数是对 execute() 函数的二次封装，让接口用起来更加方便。
     */
    public void set(Object key, Object value, long timeout, TimeUnit unit)
    {
        execute(connection -> null);
    }

    /**
     * Callback interface for Redis 'low level' code. To be used with { RedisTemplate} execution methods, often as
     * anonymous classes within a method implementation. Usually, used for chaining several operations together (
     * {@code get/set/trim etc...}.
     *
     * @author Costin Leau
     */
    interface RedisCallback<T>
    {

        /**
         * Gets called by { RedisTemplate} with an active Redis connection. Does not need to care about activating or
         * closing the connection or handling exceptions.
         *
         * @param connection active Redis connection
         * @return a result object or {@code null} if none
         * @throws
         */
        T doInRedis(String connection);
    }
}
