package com.me.noteapp.entity;

import com.me.noteapp.annotation.Column;
import com.me.noteapp.annotation.Id;
import com.me.noteapp.config.DBType;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Item implements Serializable {
    @Id
    @Column(type = DBType.INTEGER)
    private Date dAt;
    @Column(type = DBType.TEXT)
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
