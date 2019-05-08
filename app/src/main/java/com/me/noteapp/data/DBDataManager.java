package com.me.noteapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import com.me.noteapp.data.helper.DBHelper;
import com.me.noteapp.entity.History;
import com.me.noteapp.entity.Item;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Observable;

public class DBDataManager extends Observable {
    private DBHelper dbHelper;

    public DBDataManager(Context ctx) {
        dbHelper = DBHelper.getInstance(ctx);
    }

    public void createSampleData() {
        for (int i = 0; i < 10; i++) {
            dbHelper.insertItem(new Item(new Date(Calendar.getInstance().getTimeInMillis() - i * 3600 * 1000), "Hello, World! " + i));
        }
    }

    public void reloadData() {
        List res = dbHelper.readAllItems();
        if (res.isEmpty()) createSampleData();
        setChanged();
        notifyObservers(res);
    }

    public boolean insertItem(Item item) throws SQLiteConstraintException {
        boolean res = dbHelper.insertItem(item);
        reloadData();
        return res;
    }

    public Item read(Long id) {
        return dbHelper.read(id.toString());
    }

    public List<Item> readAllItems() {
        return dbHelper.readAllItems();
    }

    public List<String> getHistories(long id) {
        List<History> h = dbHelper.getHistories(String.valueOf(id));
        List<String> res = new ArrayList<>();
        for (History i : h) res.add(i.getHistory());
        return res;
    }

    public boolean updateItem(Item item) {
        boolean res = dbHelper.updateItem(item);
        reloadData();
        return res;
    }

    public synchronized boolean deleteItem(Long itemId) {
        boolean res = dbHelper.deleteItem(itemId.toString());
        reloadData();
        return res;
    }

    public void destroy() {
        dbHelper.close();
    }
}
