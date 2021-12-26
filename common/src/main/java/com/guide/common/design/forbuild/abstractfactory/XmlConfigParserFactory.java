package com.guide.common.design.forbuild.abstractfactory;

import com.guide.common.design.forbuild.IRuleConfigParser;
import com.guide.common.design.forbuild.ISystemConfigParser;
import com.guide.common.design.forbuild.simplefactory.XmlRuleConfigParser;

public class XmlConfigParserFactory implements IConfigParserFactory
{
    @Override
    public IRuleConfigParser createRuleParser()
    {
        return new XmlRuleConfigParser();
    }

    @Override
    public ISystemConfigParser createSystemParser()
    {
        return new XmlSystemConfigParser();
    }
}
