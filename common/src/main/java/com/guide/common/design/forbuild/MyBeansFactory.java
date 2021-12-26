package com.guide.common.design.forbuild;

import com.google.common.annotations.VisibleForTesting;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 依赖注入容器（Dependency Injection Container）
 * DI 容器底层最基本的设计思路就是基于工厂模式，
 * 除此之外，DI 容器负责的事情要比单纯的工厂模式要多。
 * 比如，它还包括配置的解析、对象生命周期的管理。
 */
public class MyBeansFactory
{
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, MyBeanDefinition> beanDefinitions = new ConcurrentHashMap<>();

    public void addBeanDefinitions(List<MyBeanDefinition> beanDefinitionList)
    {
        for (MyBeanDefinition beanDefinition : beanDefinitionList)
        {
            this.beanDefinitions.putIfAbsent(beanDefinition.getId(), beanDefinition);
        }

        for (MyBeanDefinition beanDefinition : beanDefinitionList)
        {
            if (beanDefinition.isLazyInit() == false && beanDefinition.isSingleton())
            {
                createBean(beanDefinition);
            }
        }
    }

    public Object getBean(String beanId)
    {
        MyBeanDefinition beanDefinition = beanDefinitions.get(beanId);
        if (beanDefinition == null)
        {
            throw new RuntimeException("Bean is not defined: " + beanId);
        }
        return createBean(beanDefinition);
    }

    @VisibleForTesting
    protected Object createBean(MyBeanDefinition beanDefinition)
    {
        if (beanDefinition.isSingleton() && singletonObjects.contains(beanDefinition.getId()))
        {
            return singletonObjects.get(beanDefinition.getId());
        }

        Object bean = null;
        try
        {
            Class beanClass = Class.forName(beanDefinition.getClassName());
            List<MyBeanDefinition.ConstructorArg> args = beanDefinition.getConstructorArgs();
            if (args.isEmpty())
            {
                bean = beanClass.newInstance();
            }
            else
            {
                Class[] argClasses = new Class[args.size()];
                Object[] argObjects = new Object[args.size()];
                for (int i = 0; i < args.size(); ++i)
                {
                    MyBeanDefinition.ConstructorArg arg = args.get(i);
                    if (!arg.isRef())
                    {
                        argClasses[i] = arg.getType();
                        argObjects[i] = arg.getArg();
                    }
                    else
                    {
                        MyBeanDefinition refBeanDefinition = beanDefinitions.get(arg.getArg());
                        if (refBeanDefinition == null)
                        {
                            throw new RuntimeException("Bean is not defined: " + arg.getArg());
                        }
                        argClasses[i] = Class.forName(refBeanDefinition.getClassName());
                        argObjects[i] = createBean(refBeanDefinition);
                    }
                }
                bean = beanClass.getConstructor(argClasses).newInstance(argObjects);
            }
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e)
        {
            throw new RuntimeException("BeanCreationFailureException", e);
        }

        if (bean != null && beanDefinition.isSingleton())
        {
            singletonObjects.putIfAbsent(beanDefinition.getId(), bean);
            return singletonObjects.get(beanDefinition.getId());
        }
        return bean;
    }
}
