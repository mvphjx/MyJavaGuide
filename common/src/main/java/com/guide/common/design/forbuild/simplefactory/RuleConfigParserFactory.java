package com.guide.common.design.forbuild.simplefactory;

import com.guide.common.design.forbuild.IRuleConfigParser;

import java.util.HashMap;
import java.util.Map;

/**
 * 简单工厂（Simple Factory）
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

