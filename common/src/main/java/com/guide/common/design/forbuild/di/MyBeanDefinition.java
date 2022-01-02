package com.guide.common.design.forbuild.di;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 容器存放的实例（bean）定义
 */
@Data
public class MyBeanDefinition
{
    private String id;
    private String className;
    private List<ConstructorArg> constructorArgs = new ArrayList<>();
    private Scope scope = Scope.SINGLETON;
    private boolean lazyInit = false;
    public boolean isSingleton()
    {
        return scope.equals(Scope.SINGLETON);
    }

    public static enum Scope
    {
        SINGLETON, PROTOTYPE
    }

    @Data
    public static class ConstructorArg
    {
        private boolean isRef;
        private Class type;
        private Object arg;
        // 省略必要的getter/setter/constructors
    }
}
