package com.cfeng.msmhelper;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.widget.Toast;

import com.cfeng.msmhelper.DB.MySms;
import com.cfeng.msmhelper.DB.MySmsDbApater;

public class MsmService extends Service {
    private Uri SMS_INBOX = Uri.parse("content://sms/");
    private SmsObserver smsObserver;
    Context context;
    public Handler smsHandler = new Handler() {
        //这里可以进行回调的操作
        //TODO

    };
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(context,"短信备份开启",Toast.LENGTH_SHORT).show();
        smsObserver = new SmsObserver(context, smsHandler);
        context.getContentResolver().registerContentObserver(SMS_INBOX, true,
                smsObserver);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        this.context=getApplicationContext();
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    class SmsObserver extends ContentObserver {

        public SmsObserver(Context context, Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Toast.makeText(context,"开始备份",Toast.LENGTH_SHORT).show();
            ContentResolver cr= context.getContentResolver();
            String[] projection = new String[]{"_id", "thread_id", "address", "date", "protocl", "read", "type", "body"};
            String where = "date >  "
                    + (System.currentTimeMillis() - 10 * 60 * 1000);
            Cursor cur = cr.query(SMS_INBOX, projection, where, null, "date desc");
            MySmsDbApater msa=new MySmsDbApater(context);
            if (cur.moveToFirst()) {
                do {
                    MySms info = new MySms(cur.getInt(cur.getColumnIndex("_id")), cur.getInt(cur.getColumnIndex("thread_id")), cur.getString(cur.getColumnIndex("address")),
                            cur.getLong(cur.getColumnIndex("date")), cur.getInt(cur.getColumnIndex("protocol")), cur.getInt(cur.getColumnIndex("read")),
                            cur.getInt(cur.getColumnIndex("type")), cur.getString(cur.getColumnIndex("body")));
                    msa.Insert(info);
                } while (cur.moveToNext());
            }
        }
    }
}
