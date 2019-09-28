package com.person.juc;

/**
 * created by 2019-09-28
 * 线程八锁
 * 题目：打印"one" "two"
 *
 * 1.两个同步方法，两个线程，标准打印 // one two
 * 2.新增Thread.sleep(3000);给getOne(),打印  // one two
 * 3.新增普通方法getThree(), 打印 // three one two
 * 4.两个同步方法，两个ThreadMonitorDemo对象，打印 // two one
 * 5.一个ThreadMonitorDemo对象，改变getOne为静态同步方法，打印 // two one
 * 6.一个ThreadMonitorDemo对象，改变两个方法为静态同步方法，打印 // one two
 * 7.两个ThreadMonitorDemo对象, 只改变getOne为静态同步方法，打印// two one
 * 8.两个ThreadMonitorDemo对象, 改变两个方法为静态同步方法，打印// one two
 *
 * 关键：
 * 1.非静态方法的锁默认为this, 静态方法的锁为对应的Class实例
 * 2.某个时刻内，只能有一个线程持有锁，无论几个方法
 */
public class TestThreadMonitor {

    public static void main(String []args) {

        ThreadMonitorDemo threadMonitorDemo = new ThreadMonitorDemo();
//        ThreadMonitorDemo threadMonitorDemo1 = new ThreadMonitorDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                threadMonitorDemo.getOne();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                threadMonitorDemo.getTwo();
//                threadMonitorDemo1.getTwo();
            }
        }).start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                threadMonitorDemo.getThree();
//            }
//        }).start();
    }
}

class ThreadMonitorDemo {

//    public synchronized void getOne() {
//        try {
//            Thread.sleep(3000);
//            System.out.println("one");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    public static synchronized void getOne() {
        try {
            Thread.sleep(3000);
            System.out.println("one");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    public synchronized void getTwo() {
//        System.out.println("two");
//    }

    public static synchronized void getTwo() {
        System.out.println("two");
    }

//    public void getThree() {
//        System.out.println("three");
//    }
}
