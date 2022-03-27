package com.guide.common.design.foraction.observer;

/**
 * 观察者模式的“模板代码”，Demo
 * 可以通过UML查看  具体实现
 */
public class Demo
{
    public static void main(String[] args)
    {
        ConcreteSubject subject = new ConcreteSubject();
        subject.registerObserver(new ConcreteObserverOne());
        subject.registerObserver(new ConcreteObserverTwo());
        subject.notifyObservers(new Message());
    }
}
