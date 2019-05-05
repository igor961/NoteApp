package com.me.noteapp.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Item implements Serializable {
    private Date dAt;
    private String content;
    private transient String date, time;

    public Item(Date dAt, String content) {
        this.dAt = dAt;
        this.content = content;
        this.time = new SimpleDateFormat("HH:mm").format(dAt);
        this.date = new SimpleDateFormat("dd/MM/yyyy").format(dAt);
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public Date getdAt() {
        return dAt;
    }

    public String getContent() {
        return content;
    }
}
