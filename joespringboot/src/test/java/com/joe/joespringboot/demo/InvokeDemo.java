package com.joe.joespringboot.demo;

import java.util.concurrent.*;

public class InvokeDemo {

    public static void main(String[] args){
        Executors.newFixedThreadPool(5);
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
//        System.out.println(queue.offer("1"));
//        System.out.println(queue.offer("1"));
//        System.out.println(queue.offer("1"));
//        System.out.println(queue.offer("1"));
//        System.out.println(queue.offer("1"));
//        System.out.println(queue.offer("1"));
//        System.out.println(queue.offer("1"));
//        System.out.println(queue.offer("1"));
//        System.out.println(queue.offer("1"));
//        System.out.println(queue.offer("1"));
//        System.out.println(queue.offer("1"));
//        System.out.println(queue.offer("1"));
//        System.out.println(queue.offer("1"));
        try {
            queue.put("1");
            queue.put("1");
            queue.put("1");
            queue.put("1");
            queue.put("1");
            queue.put("1");
            queue.put("1");
            queue.put("1");
            queue.put("1");
            queue.put("1");
            queue.put("1");
        }catch (Exception e){
            System.out.println("error");
        }
    }

}
