package com;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ifly on 2017/3/1.
 */
public class TestThread extends Thread {

    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        TestThread t1 = new TestThread();
        TestThread t2 = new TestThread();
        TestThread t3 = new TestThread();
        TestThread t4 = new TestThread();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

    public void run() {
        while (true) {
            System.out.println("线程名称："
                    + Thread.currentThread().getName() + "执行时间:" + sd.format(new Date()));

            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
