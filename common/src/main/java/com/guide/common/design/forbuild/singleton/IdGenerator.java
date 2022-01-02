package com.guide.common.design.forbuild.singleton;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 全局唯一类
 * 单例模式关注点：
 * 构造函数需要是 private 访问权限的，这样才能避免外部通过 new 创建实例；
 * 考虑对象创建时的线程安全问题；
 * 考虑是否支持延迟加载；
 * 考虑 getInstance() 性能是否高（是否加锁）。
 *
 * 缺点：
 * 1. 单例对 OOP 特性的支持不友好
 * 2. 单例会隐藏类之间的依赖关系
 * 3. 单例对代码的扩展性不友好
 * 4. 单例对代码的可测试性不友好
 * 5. 单例不支持有参数的构造函数
 *
 * 解决：
 * 通过框架创建对象，依赖注入，实现单例
 */
public class IdGenerator
{
    // AtomicLong是一个Java并发库中提供的一个原子变量类型,
    // 它将一些线程不安全需要加锁的复合操作封装为了线程安全的原子操作，
    // 比如下面会用到的incrementAndGet().
    private AtomicLong id = new AtomicLong(0);
    private static final IdGenerator INSTANCE = new IdGenerator();

    /**
     * 构造函数需要是 private 访问权限的，这样才能避免外部通过 new 创建实例
     */
    private IdGenerator()
    {
    }

    public static IdGenerator getInstance()
    {
        return INSTANCE;
    }

    public long getId()
    {
        return id.incrementAndGet();
    }

    public static void main(String[] args)
    {
        // IdGenerator使用举例
        long id = IdGenerator.getInstance().getId();
    }
}


