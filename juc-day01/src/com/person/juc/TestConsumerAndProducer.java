package com.person.juc;

public class TestConsumerAndProducer {
    public static void main(String []args) {
        Clerk clerk = new Clerk();
        Consumer consumer = new Consumer(clerk);
        Productor productor = new Productor(clerk);

        new Thread(productor, "生产者").start();
        new Thread(consumer,"消费者").start();

    }
}

class Clerk {
    private int product = 0;

    //进货
    public synchronized void get() throws InterruptedException {
        //虚假唤醒问题，wait应该使用在循环中
        while(product >= 1) {
            System.out.println("产品已满");
            this.wait();
        }
        System.out.println(Thread.currentThread().getName() + ": " + product++);
        this.notifyAll();

    }

    //卖货
    public synchronized void sale() throws InterruptedException {
        //虚假唤醒问题，wait应该使用在循环中
        while (product <= 0) {
            System.out.println("缺货！");
            this.wait();
        }
        System.out.println(Thread.currentThread().getName() + ": " + product--);
        this.notifyAll();
    }
}

class Productor implements Runnable{
    private Clerk clerk;

    public Productor(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for(int i = 0; i<20; i++) {
            try {
                clerk.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

class Consumer implements Runnable{
    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for(int i = 0; i<20; i++) {
            try {
                clerk.sale();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}