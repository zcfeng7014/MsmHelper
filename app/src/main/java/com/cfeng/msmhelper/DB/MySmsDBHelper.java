package com.cfeng.msmhelper.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/7/7.
 */

public class MySmsDBHelper  extends SQLiteOpenHelper{
    public MySmsDBHelper(Context context, String name, int version) {
        super(context, name,null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table sms (" +
                "_id integer primary key," +
                "thread_id," +
                "address," +
                "date," +
                "protocl," +
                "read," +
                "type," +
                "body);" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
