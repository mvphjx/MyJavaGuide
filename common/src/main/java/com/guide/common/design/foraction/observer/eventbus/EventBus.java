package com.guide.common.design.foraction.observer.eventbus;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * EventBus 实现的是阻塞同步的观察者模式
 */
public class EventBus
{
    private Executor executor;
    private ObserverRegistry registry = new ObserverRegistry();

    public EventBus()
    {
        this.executor = Executors.newFixedThreadPool(1);
    }

    protected EventBus(Executor executor)
    {
        this.executor = executor;
    }

    public void register(Object object)
    {
        registry.register(object);
    }

    public void post(Object event)
    {
        List<ObserverAction> observerActions = registry.getMatchedObserverActions(event);
        for (ObserverAction observerAction : observerActions)
        {
            executor.execute(new Runnable()
            {
                @Override
                public void run()
                {
                    observerAction.execute(event);
                }
            });
        }
    }
}
