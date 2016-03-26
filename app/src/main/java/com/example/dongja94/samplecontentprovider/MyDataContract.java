package com.example.dongja94.samplecontentprovider;

import android.net.Uri;

/**
 * Created by dongja94 on 2016-03-26.
 */
public final class MyDataContract {
    static final String AUTHORITY = "com.example.dongja94.samplecontentprovider";
    public static final class MyData1 {
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/data1");
        public static final String _ID = "_id";
        public static final String NAME = "name";
        public static final String AGE = "age";
        public static final String TYPE = "vnd.android.cursor.dir/vnd.com.example.dongja94.samplecontentprovider.table1";
        public static final String ITEM_TYPE = "vnd.android.cursor.item/vnd.com.example.dongja94.samplecontentprovider.table1";
    }
    public static final class MyData2 {
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/data2");
        public static final String _ID = "_id";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String TYPE = "vnd.android.cursor.dir/vnd.com.example.dongja94.samplecontentprovider.table2";
        public static final String ITEM_TYPE = "vnd.android.cursor.item/vnd.com.example.dongja94.samplecontentprovider.table2";
    }
}
