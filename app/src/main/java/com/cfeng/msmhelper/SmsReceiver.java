package com.cfeng.msmhelper;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.widget.Toast;

import com.cfeng.msmhelper.DB.MySms;
import com.cfeng.msmhelper.DB.MySmsDbApater;

import static android.R.id.list;
import static android.R.id.mask;

public class SmsReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Intent in=new Intent(context,MsmService.class);
        context.startService(in);
    }

}
