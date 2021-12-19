package com.guide.common.design.oop.polymorphism;

/**
 * 子类，扩展为有序数组
 */
public class SortedDynamicArray extends DynamicArray
{
    @Override
    public void add(Integer e)
    {
        ensureCapacity();
        int i;
        //保证数组中的数据有序
        for (i = size - 1; i >= 0; --i)
        {
            if (elements[i] > e)
            {
                elements[i + 1] = elements[i];
            }
            else
            {
                break;
            }
        }
        elements[i + 1] = e;
        ++size;
    }
}
