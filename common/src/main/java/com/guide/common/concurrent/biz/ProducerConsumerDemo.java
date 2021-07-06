package com.guide.common.concurrent.biz;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 生产者消费者
 *
 * @author hjx
 * @version 1.0
 * @date 2021年7月4日18:57:48
 */
public class ProducerConsumerDemo
{
    public static void main(String[] args)
    {
        MyBlockingQueue<String> queue = new MyBlockingQueue<>(10);
        new Producer(queue).start();
        new Consumer(queue).start();
        new Consumer(queue).start();
    }

    /**
     * 任务队列
     */
    static class MyBlockingQueue<E>
    {
        private Queue<E> queue = null;
        private int limit;

        public MyBlockingQueue(int limit)
        {
            this.limit = limit;
            queue = new ArrayDeque<>(limit);
        }

        /**
         * 加锁 保证添加任务线程安全
         *
         * @param e
         * @throws InterruptedException
         */
        public synchronized void put(E e) throws InterruptedException
        {
            while (queue.size() == limit)
            {
                System.out.println("Queue Full->Wait");
                //开始等待，直到被其他线程唤醒
                wait();
            }
            queue.add(e);
            notifyAll();
        }

        /**
         * 加锁 保证获取任务线程安全
         *
         * @return
         * @throws InterruptedException
         */
        public synchronized E take() throws InterruptedException
        {
            while (queue.isEmpty())
            {
                System.out.println("Queue Empty->Wait");
                wait();
            }
            E e = queue.poll();
            notifyAll();
            return e;
        }
    }

    /**
     * 生产者
     */
    static class Producer extends Thread
    {
        MyBlockingQueue<String> queue;

        public Producer(MyBlockingQueue<String> queue)
        {
            this.queue = queue;
        }

        @Override
        public void run()
        {
            int num = 0;
            try
            {
                while (true)
                {
                    String task = String.valueOf(num);
                    queue.put(task);
                    System.out.println(this.getName() + " produce task " + task);
                    num++;
                    Thread.sleep((int) (Math.random() * 1));
                }
            }
            catch (InterruptedException e)
            {
            }
        }
    }

    /**
     * 消费者
     */
    static class Consumer extends Thread
    {
        MyBlockingQueue<String> queue;

        public Consumer(MyBlockingQueue<String> queue)
        {
            this.queue = queue;
        }

        @Override
        public void run()
        {
            try
            {
                while (true)
                {
                    String task = queue.take();
                    System.out.println(this.getName() + " handle task " + task);
                    Thread.sleep((int) (Math.random() * 2));
                }
            }
            catch (InterruptedException e)
            {
            }
        }
    }

}
