package com.guide.common.rpc.demo;

/**
 * 远程服务接口实现
 * @author hjx
 * @version 1.0
 * @date 2021/8/15 1:32
 */
public class ServiceImpl implements ServiceDemo
{
    @Override
    public int add(int a, int b)
    {
        return a + b;
    }
}
