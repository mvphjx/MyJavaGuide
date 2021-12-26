package com.guide.common.design.forbuild.abstractfactory;

import com.guide.common.design.forbuild.IRuleConfigParser;
import com.guide.common.design.forbuild.ISystemConfigParser;
import com.guide.common.design.forbuild.simplefactory.JsonRuleConfigParser;

public class JsonConfigParserFactory implements IConfigParserFactory {
    @Override
    public IRuleConfigParser createRuleParser() {
        return new JsonRuleConfigParser();
    }

    @Override
    public ISystemConfigParser createSystemParser() {
        return new JsonSystemConfigParser();
    }
}
