package com.guide.common.design.forbuild.prototype;

import java.util.HashMap;
import java.util.List;

/**
 * 如果对象的创建成本比较大，而同一个类的不同对象之间差别不大（大部分字段都相同），
 * 在这种情况下，我们可以利用对已有对象（原型）进行复制（或者叫拷贝）的方式，来创建新对象，
 * 以达到节省创建时间的目的。这种基于原型来创建对象的方式就叫作原型设计模式，简称原型模式。
 *
 * 原型模式有两种实现方法，深拷贝和浅拷贝。
 */
public class Demo
{
    private HashMap<String, SearchWord> currentKeywords = new HashMap<>();
    private long lastUpdateTime = -1;

    public void refresh()
    {
        // Shallow copy
        HashMap<String, SearchWord> newKeywords = (HashMap<String, SearchWord>) currentKeywords.clone();

        // 从数据库中取出更新时间>lastUpdateTime的数据，放入到newKeywords中
        List<SearchWord> toBeUpdatedSearchWords = getSearchWords(lastUpdateTime);
        long maxNewUpdatedTime = lastUpdateTime;
        for (SearchWord searchWord : toBeUpdatedSearchWords)
        {
            if (searchWord.getLastUpdateTime() > maxNewUpdatedTime)
            {
                maxNewUpdatedTime = searchWord.getLastUpdateTime();
            }
            if (newKeywords.containsKey(searchWord.getKeyword()))
            {
                newKeywords.remove(searchWord.getKeyword());
            }
            newKeywords.put(searchWord.getKeyword(), searchWord);
        }

        lastUpdateTime = maxNewUpdatedTime;
        currentKeywords = newKeywords;
    }

    private List<SearchWord> getSearchWords(long lastUpdateTime)
    {
        // TODO: 从数据库中取出更新时间>lastUpdateTime的数据
        return null;
    }
}
