package com.guide.common.design.forbuild.simplefactory;

import com.guide.common.design.forbuild.IRuleConfigParser;

import java.util.HashMap;
import java.util.Map;

/**
 * 简单工厂（Simple Factory）
 * 静态工厂方法模式（Static Factory Method Pattern）
 *
 * 内部创建、维护多个实例，通过参数获取指定实例
 */
public class RuleConfigParserFactory
{
    private static final Map<String, IRuleConfigParser> cachedParsers = new HashMap<>();

    static
    {
        cachedParsers.put("json", new JsonRuleConfigParser());
        cachedParsers.put("xml", new XmlRuleConfigParser());
        cachedParsers.put("yaml", new YamlRuleConfigParser());
        cachedParsers.put("properties", new PropertiesRuleConfigParser());
    }

    public static IRuleConfigParser createParser(String configFormat)
    {
        if (configFormat == null || configFormat.isEmpty())
        {
            //返回null还是IllegalArgumentException全凭你自己说了算
            return null;
        }
        IRuleConfigParser parser = cachedParsers.get(configFormat.toLowerCase());
        return parser;
    }

    public static void main(String[] args)
    {
        //run
        IRuleConfigParser json = RuleConfigParserFactory.createParser("json");
    }
}

