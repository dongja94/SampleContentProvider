package com.example.dongja94.samplecontentprovider;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by dongja94 on 2016-03-26.
 */
public class MyData2 {
    public static AtomicLong last_id = new AtomicLong();
    static {
        last_id.set(1);
    }
    public long _id;
    public String title;
    public String description;
}