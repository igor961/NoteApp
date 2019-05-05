package com.me.noteapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import com.me.noteapp.NotesApplication;
import com.me.noteapp.data.helper.DBHelper;
import com.me.noteapp.entity.Item;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DBDataManager {
    private DBHelper dbHelper;

    public DBDataManager(Context ctx) {
        dbHelper = new DBHelper(ctx);
    }

    public void createSampleData() {
        for (int i = 0; i < 10; i++) {
            dbHelper.insertItem(new Item(new Date(Calendar.getInstance().getTimeInMillis() - i * 3600 * 1000), "Hello, World! " + i));
        }
    }

    public void reloadData() {
        List res = dbHelper.readAllItems();
        if (res.isEmpty()) createSampleData();
        else NotesApplication.setTestData(res);
    }

    public boolean insertItem(@NotNull Item item) throws SQLiteConstraintException {
        boolean res = dbHelper.insertItem(item);
        reloadData();
        return res;
    }

    @Nullable
    public Item read(@NotNull Long id) {
        return dbHelper.read(id.toString());
    }

    public boolean updateItem(@NotNull Item item) {
        boolean res = dbHelper.updateItem(item);
        reloadData();
        return res;
    }

    public boolean deleteItem(Long itemId) {
        boolean res = dbHelper.deleteItem(itemId.toString());
        reloadData();
        return res;
    }

    public void destroy() {
        dbHelper.close();
    }
}
