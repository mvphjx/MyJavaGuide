package com.guide.common.design.forbuild.factorymethod;

import com.guide.common.design.forbuild.IRuleConfigParser;

/**
 * 工厂方法（Factory Method）
 * 工厂方法模式比起简单工厂模式更加符合开闭原则。
 *
 * 抽象出工厂方法接口，新的工厂实现此接口，方便扩展新的工厂
 */
public interface IRuleConfigParserFactory
{
    IRuleConfigParser createParser();
}

