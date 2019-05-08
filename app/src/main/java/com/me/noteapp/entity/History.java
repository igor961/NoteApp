package com.me.noteapp.entity;

import com.me.noteapp.annotation.Column;
import com.me.noteapp.annotation.Id;
import com.me.noteapp.annotation.Reference;
import com.me.noteapp.config.DBType;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class History implements Serializable {
    @Reference(table = Item.class)
    @Column(type = DBType.INTEGER)
    private long itemId;
    @Id
    @Column(type = DBType.INTEGER)
    private Date id;

    private transient String date, time;

    public History(long itemId, Date id) {
        this.itemId = itemId;
        this.id = id;
        this.time = new SimpleDateFormat("HH:mm").format(id);
        this.date = new SimpleDateFormat("dd/MM/yyyy").format(id);
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public Date getId() {
        return id;
    }

    public void setId(Date date) {
        this.id = date;
    }

    public String getHistory() {
        return date + " " + time;
    }
}
