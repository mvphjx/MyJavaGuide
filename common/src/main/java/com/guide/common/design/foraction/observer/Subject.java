package com.guide.common.design.foraction.observer;

public interface Subject
{
    //注册
    void registerObserver(Observer observer);
    //取消
    void removeObserver(Observer observer);
    //通知
    void notifyObservers(Message message);

}
