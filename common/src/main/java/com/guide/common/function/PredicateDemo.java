package com.guide.common.function;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Predicate;

/**
 * Predicate,FunctionalInterface
 * 1代表着一个断言表达式
 * 2这是一个实用的接口—>其中的实用方法指的是test方法
 * 3不足：感觉没有正则表达式强大、易用？？
 */
@Slf4j
public class PredicateDemo
{
    public static void main(String[] args)
    {

        //设置一个大于5的过滤条件
        Predicate<Integer> predicate = integer -> integer>5;
        for (int i = 0; i < 10; i++)
        {
            log.info("predicate1:"+predicate.test(i));
        }
        //设置一个小于8的过滤条件
        predicate = predicate.and(integer -> integer<8);
        for (int i = 0; i < 10; i++)
        {
            log.info("predicate2:"+predicate.test(i));
        }
    }
}
