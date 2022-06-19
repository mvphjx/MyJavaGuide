package com.guide.common.design.foraction.observer;

/**
 * 观察者模式的“模板代码”，Demo
 * 可以通过UML查看  具体实现
 * 消息队列使用场景：应用模块的解耦、消息的异步化、削峰填谷
 */
public class Demo
{
    public static void main(String[] args)
    {
        //创建消息平台
        ConcreteSubject subject = new ConcreteSubject();
        //订阅消息
        subject.registerObserver(new ConcreteObserverOne());
        subject.registerObserver(new ConcreteObserverTwo());
        //广播消息
        subject.notifyObservers(new Message());
    }
}
