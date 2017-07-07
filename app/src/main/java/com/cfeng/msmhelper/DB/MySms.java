package com.cfeng.msmhelper.DB;

import java.sql.Date;

/**
 * Created by Administrator on 2017/7/7.
 */

public class MySms {
    private int _id;
    private int thread_id;
    private String address;
    private long date;
    private int protocl;
    private int read;
    private int type;
    private String body;

    @Override
    public String toString() {
        return "address:"+address+" date:"+new Date(date)+"\n"+body;
    }

    public MySms(int _id, int thread_id, String address, long date, int protocl, int read, int type, String body) {
        this._id = _id;
        this.thread_id = thread_id;
        this.address = address;
        this.date = date;
        this.protocl = protocl;
        this.read = read;
        this.type = type;
        this.body = body;
    }

    public int get_id() {
        return _id;
    }

    public int getThread_id() {
        return thread_id;
    }

    public String getAddress() {
        return address;
    }

    public long getDate() {
        return date;
    }

    public int getProtocl() {
        return protocl;
    }

    public int getRead() {
        return read;
    }

    public int getType() {
        return type;
    }

    public String getBody() {
        return body;
    }

    @Override
    public boolean equals(Object obj) {
        return this._id==((MySms)obj)._id;
    }
}
