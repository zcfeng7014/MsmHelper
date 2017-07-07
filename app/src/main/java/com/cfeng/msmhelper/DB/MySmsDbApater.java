package com.cfeng.msmhelper.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/7.
 */

public class MySmsDbApater {
    MySmsDBHelper helper;
    Context context;
    public MySmsDbApater(Context context) {
        this.context=context;
        this.helper = new MySmsDBHelper(context,"db",1);
    }
    public void Insert(MySms mySms){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("_id",mySms.get_id());
        cv.put("thread_id",mySms.getThread_id());
        cv.put("address",mySms.getAddress());
        cv.put("date",mySms.getDate());
        cv.put("protocl",mySms.getProtocl());
        cv.put("read",mySms.getRead());
        cv.put("type",mySms.getType());
        cv.put("body",mySms.getBody());
        db.replace("sms",null,cv);
    }
    public void update(ArrayList<MySms> list){
        for (MySms sms:list) {
            Insert(sms);
        }
    }
    public ArrayList<MySms> getAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayList<MySms> list = new ArrayList<>();
        Cursor cur = db.query("sms", new String[]{"_id", "thread_id", "address", "date", "protocl", "read", "type", "body"}, null, null, null, null, "date desc");
        if (cur.moveToFirst()) {
            do {
                MySms info = new MySms(cur.getInt(cur.getColumnIndex("_id")), cur.getInt(cur.getColumnIndex("thread_id")), cur.getString(cur.getColumnIndex("address")),
                        cur.getLong(cur.getColumnIndex("date")), cur.getInt(cur.getColumnIndex("protocl")), cur.getInt(cur.getColumnIndex("read")),
                        cur.getInt(cur.getColumnIndex("type")), cur.getString(cur.getColumnIndex("body")));
                list.add(info);
            } while (cur.moveToNext());
        }
        return list;
    }
    public ArrayList<MySms> getNoExit(ArrayList<MySms> smslist) {
        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayList<MySms> list = new ArrayList<>();
        Cursor cur = db.query("sms", new String[]{"_id", "thread_id", "address", "date", "protocl", "read", "type", "body"}, null, null, null, null, "date desc");
        if (cur.moveToFirst()) {
            do {
                MySms info = new MySms(cur.getInt(cur.getColumnIndex("_id")), cur.getInt(cur.getColumnIndex("thread_id")), cur.getString(cur.getColumnIndex("address")),
                        cur.getLong(cur.getColumnIndex("date")), cur.getInt(cur.getColumnIndex("protocl")), cur.getInt(cur.getColumnIndex("read")),
                        cur.getInt(cur.getColumnIndex("type")), cur.getString(cur.getColumnIndex("body")));
                list.add(info);
            } while (cur.moveToNext());

        }
        list.removeAll(smslist);
        return list;
    }
}
