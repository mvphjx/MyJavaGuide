package com.guide.common.design.forbuild.factorymethod;

import com.guide.common.design.forbuild.IRuleConfigParser;
import com.guide.common.design.forbuild.simplefactory.XmlRuleConfigParser;

public class XmlRuleConfigParserFactory implements IRuleConfigParserFactory
{
    @Override
    public IRuleConfigParser createParser()
    {
        return new XmlRuleConfigParser();
    }
}
