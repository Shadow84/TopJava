package ru.javawebinar.topjava.dao;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Privat on 14.09.2016.
 */
public class MealIdGenerator {
    private static final AtomicInteger startId = new AtomicInteger(1);

    public static int generate(){
        return startId.getAndIncrement();
    }
}
