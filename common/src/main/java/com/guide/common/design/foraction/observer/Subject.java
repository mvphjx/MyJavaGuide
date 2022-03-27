package com.guide.common.design.foraction.observer;

public interface Subject
{
    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers(Message message);

}
