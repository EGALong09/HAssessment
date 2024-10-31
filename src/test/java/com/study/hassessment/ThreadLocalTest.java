package com.study.hassessment;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {
    /**
     * ThreadLocal用于提供线程局部变量，即使是同一变量名，各个线程都是单独存储数据的，不会互相影响
     * 在多用户访问时，每个用户一个线程，各自使用的a对象就不会互相污染
     */

    @Test
    public void testThreadLocalSetAndGet() {
        //提供一个ThreadLocal对象
        ThreadLocal tl = new ThreadLocal();

        //开启两个线程
        new Thread(() -> {
            tl.set("小蓝");
            System.out.println(Thread.currentThread().getName() + ": " + tl.get());
            System.out.println(Thread.currentThread().getName() + ": " + tl.get());
            System.out.println(Thread.currentThread().getName() + ": " + tl.get());
        }, "蓝色").start();

        new Thread(() -> {
            tl.set("小绿");
            System.out.println(Thread.currentThread().getName() + ": " + tl.get());
            System.out.println(Thread.currentThread().getName() + ": " + tl.get());
            System.out.println(Thread.currentThread().getName() + ": " + tl.get());
        }, "绿色").start();
    }
}
