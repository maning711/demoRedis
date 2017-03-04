package com;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ifly on 2017/3/1.
 */
public class TestThread2 {

    String flag = "0";
    int count = 0;

    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {

        TestThread2 testThread2 = new TestThread2();
        testThread2.new AThread().start();
        testThread2.new AThread().start();
        testThread2.new AThread().start();
        testThread2.new BThread().start();
    }

    class AThread extends Thread {

        @Override
        public void run() {
            long curTime = System.currentTimeMillis();
            while ("0".equals(flag)) {
                if (count > 100) {
                    try {
                        System.out.println("线程名称：" + Thread.currentThread().getName()
                                + " 执行时间：" + sd.format(new Date())
                                + " 开始等待");

                        synchronized (flag) {
                            flag.wait();
                        }

//                        System.out.println("线程名称：" + Thread.currentThread().getName()
//                                + " 等待时间：" + (System.currentTimeMillis() - curTime));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("线程名称：" + Thread.currentThread().getName()
                            + " 执行时间：" + sd.format(new Date()));
                    count++;
                }
            }
        }
    }

    class BThread extends Thread {

        @Override
        public void run() {

            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程名称：" + Thread.currentThread().getName()
                    + " 执行时间：" + sd.format(new Date())
                    + " 我要唤醒A线程");

            synchronized (flag) {
                flag.notifyAll();
                flag = "1";
            }
        }
    }
}
