package com.guide.common.design.oop.composition;

/**
 * 鸵鸟
 * 通过接口&组合&委托，实现下蛋职责、发声职责。
 * 1 实现接口
 * 2 注入实现类（组合）
 * 3 委托
 */
public class Ostrich implements Tweetable, EggLayable
{
    /**
     * 组合
     */
    private EggLayImpl eggLay = new EggLayImpl();
    /**
     * 组合
     */
    private TweetImpl tweet = new TweetImpl();

    @Override
    public void tweet()
    {
        //委托
        tweet.tweet();
    }

    @Override
    public void layEgg()
    {
        //委托
        eggLay.layEgg();
    }
}
