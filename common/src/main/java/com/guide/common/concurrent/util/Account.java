package com.guide.common.concurrent.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * 显式锁 实现账户转账
 *
 * @author hjx
 * @version 1.0
 * @date 2021年7月4日19:12:43
 */
public class Account {

	private Lock lock = new ReentrantLock();
    private volatile double money;

    public Account(double initialMoney) {
        this.money = initialMoney;
    }

    public void add(double money) {
        lock.lock();
        try {
            this.money += money;
        } finally {
            lock.unlock();
        }
    }

    public void reduce(double money) {
        lock.lock();
        try {
            this.money -= money;
        } finally {
            lock.unlock();
        }
    }

    public double getMoney() {
        return money;
    }

    void lock() {
        lock.lock();
    }

    void unlock() {
        lock.unlock();
    }

    boolean tryLock() {
        return lock.tryLock();
    }

}
