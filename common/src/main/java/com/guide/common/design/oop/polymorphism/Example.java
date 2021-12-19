package com.guide.common.design.oop.polymorphism;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Example
{
    public static void main(String args[])
    {
        /**
         * 通过继承+方法重写，实现的多态。
         */
        DynamicArray dynamicArray = new SortedDynamicArray();
        dynamicArray.add(5);
        dynamicArray.add(1);
        dynamicArray.add(3);
        // 打印结果：1、3、5
        log.info(dynamicArray.toString());
    }
}
