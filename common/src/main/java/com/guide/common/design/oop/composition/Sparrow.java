package com.guide.common.design.oop.composition;

/**
 * 麻雀
 */
public class Sparrow implements Flyable, EggLayable, Tweetable
{
    //... 省略其他属性和方法...
    @Override
    public void fly()
    { //...
    }

    @Override
    public void tweet()
    { //...
    }

    @Override
    public void layEgg()
    { //...
    }
}
