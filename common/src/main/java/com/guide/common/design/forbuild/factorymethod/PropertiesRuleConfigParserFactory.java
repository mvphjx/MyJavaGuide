package com.guide.common.design.forbuild.factorymethod;

import com.guide.common.design.forbuild.IRuleConfigParser;
import com.guide.common.design.forbuild.simplefactory.PropertiesRuleConfigParser;

public class PropertiesRuleConfigParserFactory implements IRuleConfigParserFactory
{
    @Override
    public IRuleConfigParser createParser()
    {
        return new PropertiesRuleConfigParser();
    }
}
