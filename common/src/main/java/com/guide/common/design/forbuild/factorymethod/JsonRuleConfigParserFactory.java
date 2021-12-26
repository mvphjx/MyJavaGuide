package com.guide.common.design.forbuild.factorymethod;

import com.guide.common.design.forbuild.IRuleConfigParser;
import com.guide.common.design.forbuild.simplefactory.JsonRuleConfigParser;

public class JsonRuleConfigParserFactory implements IRuleConfigParserFactory
{
    @Override
    public IRuleConfigParser createParser()
    {
        return new JsonRuleConfigParser();
    }
}
