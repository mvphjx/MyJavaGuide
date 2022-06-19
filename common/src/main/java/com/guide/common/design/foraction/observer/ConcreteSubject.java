package com.guide.common.design.foraction.observer;

import java.util.ArrayList;
import java.util.List;

public class ConcreteSubject implements Subject
{
    //观察者列表
    private List<Observer> observers = new ArrayList();

    @Override
    public void registerObserver(Observer observer)
    {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer)
    {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Message message)
    {
        for (Observer observer : observers) { observer.update(message); }
    }

    @Override
    public void send(Message message)
    {
        // 基于stream完成消息的生产和消费，并确保异常状态下消息至少被消费一次
        // 1）发布消息，存储结构为 listpack
        // 2）等待消费者拉取数据，waitfor observers
        // 3）记录last_delivered_id来标识已经读取到的位点，客户端连接断开后重连还是能从该位点继续读取，消息不会丢失。
        // stream引入了ack机制保证消息至少被处理一次
    }
}
