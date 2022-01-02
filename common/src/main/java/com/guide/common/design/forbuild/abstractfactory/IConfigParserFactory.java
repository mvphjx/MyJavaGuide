package com.guide.common.design.forbuild.abstractfactory;

import com.guide.common.design.forbuild.IRuleConfigParser;
import com.guide.common.design.forbuild.ISystemConfigParser;

/**
 * 抽象工厂
 * 为了减少工厂类的个数，
 * 我们可以让一个工厂负责创建多个不同类型的对象（IRuleConfigParser、ISystemConfigParser 等），
 * 而不是只创建一种对象。
 */
public interface IConfigParserFactory {
    IRuleConfigParser createRuleParser();
    ISystemConfigParser createSystemParser();
    //此处可以扩展新的parser类型，比如IBizConfigParser
}
