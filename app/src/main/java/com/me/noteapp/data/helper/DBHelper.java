package com.me.noteapp.data.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.me.noteapp.config.DBConfig;
import com.me.noteapp.entity.Item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static DBConfig cfg = DBConfig.getInstance();
    private static final String SQL_CREATE_TABLES = cfg.createTablesQuery();
    private static final String SQL_DELETE_TABLES = cfg.deleteTablesQuery();
    private static final String name = cfg.DB_NAME;
    private static final int version = 1;
    private ContentValues values;

    public boolean insertItem(Item item) {
        SQLiteDatabase db = getWritableDatabase();
        values = new ContentValues();
        values.put("dAt", item.getdAt().getTime());
        values.put("content", item.getContent());

        long res = db.insert("items", null, values);
        return res != -1;
    }

    public boolean updateItem(Item item) {
        SQLiteDatabase db = getWritableDatabase();
        values = new ContentValues();
        String id = String.valueOf(item.getdAt().getTime());
        values.put("dAt", id);
        values.put("content", item.getContent());

        long res = db.update("items", values, "dAt LIKE ?", new String[]{id});
        return res > 0;
    }

    public boolean deleteItem(String itemId) {
        SQLiteDatabase db = getWritableDatabase();
        long res = db.delete("items", "dAt LIKE ?", new String[]{itemId});
        return res > 0;
    }

    public Item read(String id) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("select * from items where dAt = ?;", new String[]{id});
        if (cursor.moveToFirst()) {
            Date dAt = new Date(cursor.getLong(cursor.getColumnIndex("dAt")));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            return new Item(dAt, content);
        }
        return null;
    }

    public List<Item> readAllItems() {
        SQLiteDatabase db = getWritableDatabase();
        List<Item> items = new ArrayList<>();
        Cursor cursor;
        try {
            cursor = db.rawQuery("select * from items i order by i.dAt desc;", null);
        } catch (SQLiteException e) {
            return new ArrayList();
        }

        Date dAt;
        String content;

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                dAt = new Date(cursor.getLong(cursor.getColumnIndex("dAt")));
                content = cursor.getString(cursor.getColumnIndex("content"));

                items.add(new Item(dAt, content));
                cursor.moveToNext();
            }
        }
        return items;
    }

    public DBHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
