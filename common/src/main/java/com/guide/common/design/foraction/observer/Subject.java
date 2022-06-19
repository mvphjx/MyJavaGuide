package com.guide.common.design.foraction.observer;

public interface Subject
{
    //注册
    void registerObserver(Observer observer);
    //取消
    void removeObserver(Observer observer);
    /**
     * 即时通知-广播模式:redis2.0 pubsub
     * 不足：
     * Redis异常、客户端断连都会导致消息丢失
     * 消息缺乏堆积能力，不能削峰填谷。
     * 推送的方式缺乏背压机制，没有考虑消费者处理能力，推送的消息超过消费者处理能力后可能导致消息丢失或服务异常
     */
    void notifyObservers(Message message);
    /**
     * 消息队列:redis5.0 stream
     * 一个稳定的消息服务需要具备几个要点，
     * 1）保证消息不会丢失，至少被消费一次，
     * 2）要具备削峰填谷的能力，来匹配生产者和消费者吞吐的差异
     */
    void send(Message message);

}
