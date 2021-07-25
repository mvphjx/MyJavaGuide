package com.guide.common.design.chain;

/**
 * 策略/处理方法
 *
 * @param <T> 策略参数
 * @param <R> 策略返回值
 */
public interface StrategyHandler<T, R>
{

    StrategyHandler DEFAULT = t -> null;

    /**
     * apply strategy
     *
     * @param param
     * @return
     */
    R apply(T param);
}
