package com.guide.common.design.forbuild.factorymethod;

import com.guide.common.design.forbuild.IRuleConfigParser;
import com.guide.common.design.forbuild.simplefactory.YamlRuleConfigParser;

public class YamlRuleConfigParserFactory implements IRuleConfigParserFactory
{
    @Override
    public IRuleConfigParser createParser()
    {
        return new YamlRuleConfigParser();
    }
}
