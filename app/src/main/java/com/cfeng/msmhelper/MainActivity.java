package com.cfeng.msmhelper;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cfeng.msmhelper.DB.MySms;
import com.cfeng.msmhelper.DB.MySmsDbApater;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Uri SMS_INBOX = Uri.parse("content://sms/");
    ArrayList<MySms> list=new ArrayList<>();
    BaseAdapter ba=new BaseAdapter() {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv=new TextView(getApplicationContext());
            tv.setText(list.get(position).toString());
            tv.setTextColor(Color.BLACK);
            return tv;
        }
    };
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            list=(ArrayList<MySms>) msg.obj;
            ba.notifyDataSetChanged();
            pb.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),"共找到"+list.size()+"个已删除短信",Toast.LENGTH_SHORT).show();
            super.handleMessage(msg);
        }
    };
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView lv= (ListView) findViewById(R.id.table);
        pb = (ProgressBar) findViewById(R.id.loading);
        initdata();
        lv.setAdapter(ba);
        Intent in=new Intent(this,MsmService.class);
        this.startService(in);
    }

    private void initdata() {
        pb.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                MySmsDbApater msda=new MySmsDbApater(getApplicationContext());
                ArrayList<MySms> list=new ArrayList<>();
                Cursor cur = managedQuery(SMS_INBOX, null, null, null, null);
                if (cur.moveToFirst()) {
                    do{
                        MySms info=new MySms(cur.getInt(cur.getColumnIndex("_id")),cur.getInt(cur.getColumnIndex("thread_id")),cur.getString(cur.getColumnIndex("address")),
                                cur.getLong(cur.getColumnIndex("date")),cur.getInt(cur.getColumnIndex("protocol")),cur.getInt(cur.getColumnIndex("read")),
                                cur.getInt(cur.getColumnIndex("type")),cur.getString(cur.getColumnIndex("body")));
                        list.add(info);
                    }while(cur.moveToNext());
                    msda.update(list);
                    list=msda.getNoExit(list);
                    Message msg=new Message();
                    msg.obj=list;
                    handler.sendMessage(msg);
                }
            }
        }).start();

    }
}
