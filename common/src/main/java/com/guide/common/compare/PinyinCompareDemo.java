package com.guide.common.compare;

import cn.hutool.core.comparator.PinyinComparator;
import cn.hutool.extra.pinyin.PinyinUtil;

public class PinyinCompareDemo
{
    public static void main(String[] args)
    {
        PinyinComparator pinyinComparator = new PinyinComparator();
        System.out.println(pinyinComparator.compare("获取全部","获取指定"));
        System.out.println(pinyinComparator.compare("获取指定","新增"));
        System.out.println(pinyinComparator.compare("获取全部","新增"));
        String result = PinyinUtil.getFirstLetter("H是第一个", ", ");
        System.out.println(result);
    }
}
