package com.example.dongja94.samplecontentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class MyContentProvider extends ContentProvider {
    public MyContentProvider() {
    }

    private static final int URI_MY_DATA1 = 1;
    private static final int URI_MY_DATA1_ID = 2;
    private static final int URI_MY_DATA2 = 3;
    private static final int URI_MY_DATA2_ID = 4;

    List<MyData1> myData1List = new ArrayList<>();
    List<MyData2> myData2List = new ArrayList<>();

    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(MyDataContract.AUTHORITY, "data1", URI_MY_DATA1);
        uriMatcher.addURI(MyDataContract.AUTHORITY, "data1/#", URI_MY_DATA1_ID);
        uriMatcher.addURI(MyDataContract.AUTHORITY, "data2", URI_MY_DATA2);
        uriMatcher.addURI(MyDataContract.AUTHORITY, "data2/#", URI_MY_DATA2_ID);
    }

    private MyData1 findMyData1(long id) {
        for (MyData1 data : myData1List) {
            if (data._id == id) return data;
        }
        return null;
    }

    private MyData2 findMyData2(long id) {
        for (MyData2 data : myData2List) {
            if (data._id == id) return data;
        }
        return null;
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case URI_MY_DATA1 :
                return MyDataContract.MyData1.TYPE;
            case URI_MY_DATA1_ID:
                return MyDataContract.MyData1.ITEM_TYPE;
            case URI_MY_DATA2 :
                return MyDataContract.MyData1.TYPE;
            case URI_MY_DATA2_ID :
                return MyDataContract.MyData2.ITEM_TYPE;
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case URI_MY_DATA1 : {
                MatrixCursor cursor = new MatrixCursor(new String[] {MyDataContract.MyData1._ID, MyDataContract.MyData1.NAME, MyDataContract.MyData1.AGE});
                for (MyData1 data : myData1List) {
                    if (TextUtils.isEmpty(selection) || data.name.contains(selection)) {
                        cursor.addRow(new Object[]{data._id, data.name, data.age});
                    }
                }
                return cursor;
            }
            case URI_MY_DATA1_ID :{
                MatrixCursor cursor = new MatrixCursor(new String[] {MyDataContract.MyData1._ID, MyDataContract.MyData1.NAME, MyDataContract.MyData1.AGE});
                long id = Long.parseLong(uri.getLastPathSegment());
                MyData1 data = findMyData1(id);
                if (data != null) {
                    cursor.addRow(new Object[]{data._id, data.name, data.age});
                }
                return cursor;
            }
            case URI_MY_DATA2 : {
                MatrixCursor cursor = new MatrixCursor(new String[] {MyDataContract.MyData2._ID, MyDataContract.MyData2.TITLE, MyDataContract.MyData2.DESCRIPTION});
                for (MyData2 data : myData2List) {
                    if (TextUtils.isEmpty(selection) || data.title.contains(selection) || data.description.contains(selection)) {
                        cursor.addRow(new Object[]{data._id, data.title, data.description});
                    }
                }
                return cursor;
            }
            case URI_MY_DATA2_ID :{
                MatrixCursor cursor = new MatrixCursor(new String[] {MyDataContract.MyData2._ID, MyDataContract.MyData2.TITLE, MyDataContract.MyData2.DESCRIPTION});
                long id = Long.parseLong(uri.getLastPathSegment());
                MyData2 data = findMyData2(id);
                if (data != null) {
                    cursor.addRow(new Object[]{data._id, data.title, data.description});
                }
                return cursor;
            }
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (uriMatcher.match(uri)) {
            case URI_MY_DATA1 : {
                MyData1 data = new MyData1();
                data._id = MyData1.last_id.getAndIncrement();
                if (values.containsKey(MyDataContract.MyData1.NAME)) {
                    data.name = values.getAsString(MyDataContract.MyData1.NAME);
                }
                if (values.containsKey(MyDataContract.MyData1.AGE)) {
                    data.age = values.getAsInteger(MyDataContract.MyData1.AGE);
                }
                myData1List.add(data);
                Uri insertUri = ContentUris.withAppendedId(uri,data._id);
                getContext().getContentResolver().notifyChange(insertUri, null);
                return insertUri;
            }
            case URI_MY_DATA2 : {
                MyData2 data = new MyData2();
                data._id = MyData2.last_id.getAndIncrement();
                if (values.containsKey(MyDataContract.MyData2.TITLE)) {
                    data.title = values.getAsString(MyDataContract.MyData2.TITLE);
                }
                if (values.containsKey(MyDataContract.MyData2.DESCRIPTION)) {
                    data.description = values.getAsString(MyDataContract.MyData2.DESCRIPTION);
                }
                myData2List.add(data);
                Uri insertUri = ContentUris.withAppendedId(uri,data._id);
                getContext().getContentResolver().notifyChange(insertUri, null);
                return insertUri;
            }
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case URI_MY_DATA1_ID : {
                long id = Long.parseLong(uri.getLastPathSegment());
                MyData1 d = findMyData1(id);
                if (d != null) {
                    if (values.containsKey(MyDataContract.MyData1.NAME)) {
                        d.name = values.getAsString(MyDataContract.MyData1.NAME);
                    }
                    if (values.containsKey(MyDataContract.MyData1.AGE)) {
                        d.age = values.getAsInteger(MyDataContract.MyData1.AGE);
                    }
                    myData1List.set(myData1List.indexOf(d), d);
                    Uri updateUri = ContentUris.withAppendedId(MyDataContract.MyData1.CONTENT_URI, id);
                    getContext().getContentResolver().notifyChange(updateUri, null);
                    return 1;
                }
            }
            case URI_MY_DATA2_ID : {
                long id = Long.parseLong(uri.getLastPathSegment());
                MyData2 d = findMyData2(id);
                if (d != null) {
                    if (values.containsKey(MyDataContract.MyData2.TITLE)) {
                        d.title = values.getAsString(MyDataContract.MyData2.TITLE);
                    }
                    if (values.containsKey(MyDataContract.MyData2.DESCRIPTION)) {
                        d.description = values.getAsString(MyDataContract.MyData2.DESCRIPTION);
                    }
                    myData2List.set(myData2List.indexOf(d), d);
                    Uri updateUri = ContentUris.withAppendedId(MyDataContract.MyData2.CONTENT_URI, id);
                    getContext().getContentResolver().notifyChange(updateUri, null);
                    return 1;
                }
            }
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case URI_MY_DATA1_ID : {
                long id = Long.parseLong(uri.getLastPathSegment());
                MyData1 d = findMyData1(id);
                if (d != null) {
                    myData1List.remove(d);
                    Uri removeUri = ContentUris.withAppendedId(MyDataContract.MyData1.CONTENT_URI, id);
                    getContext().getContentResolver().notifyChange(removeUri, null);
                    return 1;
                }
            }
            case URI_MY_DATA2_ID : {
                long id = Long.parseLong(uri.getLastPathSegment());
                MyData2 d = findMyData2(id);
                if (d != null) {
                    myData2List.remove(d);
                    Uri removeUri = ContentUris.withAppendedId(MyDataContract.MyData2.CONTENT_URI, id);
                    getContext().getContentResolver().notifyChange(removeUri, null);
                    return 1;
                }
            }
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }
}