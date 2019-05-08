package com.me.noteapp.data.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.me.noteapp.config.DBConfig;
import com.me.noteapp.entity.History;
import com.me.noteapp.entity.Item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper sInstance;
    private static DBConfig cfg = DBConfig.getInstance();
    private static final List<String> SQL_CREATE_TABLES = cfg.getCreateTablesQuery();
    private static final List<String> SQL_DELETE_TABLES = cfg.getDeleteTablesQuery();
    private static final String name = cfg.DB_NAME;
    private static int version = 1;
    private ContentValues values;

    public static synchronized DBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public synchronized boolean insertItem(Item item) {
        SQLiteDatabase db = getWritableDatabase();
        values = new ContentValues();
        values.put("dAt", item.getdAt().getTime());
        values.put("content", item.getContent());
        long res = 0;
        res = db.insert("items", null, values);
        return res != -1;
    }

    public boolean updateItem(Item item) {
        SQLiteDatabase db = getWritableDatabase();
        values = new ContentValues();
        String id = String.valueOf(item.getdAt().getTime());
        values.put("dAt", id);
        values.put("content", item.getContent());

        db.beginTransaction();
        long res;
        try {
            res = db.update("items", values, "dAt LIKE ?", new String[]{id});
            insertHistory(new History(item.getdAt().getTime(), new Date()));
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
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

    public boolean insertHistory(History h) {
        SQLiteDatabase db = getWritableDatabase();
        values = new ContentValues();
        values.put("itemId", h.getItemId());
        values.put("id", h.getId().getTime());
        long res = 0;
        res = db.insert("histories", null, values);
        return res != -1;
    }

    public List<History> getHistories(String id) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor;
        List<History> histories = new ArrayList<>();
        cursor = db.rawQuery("select * from histories where itemId = ?;", new String[]{id});
        Date hId;
        long itemId;
        if (cursor.moveToFirst()) {
            itemId = cursor.getLong(cursor.getColumnIndex("itemId"));
            while (cursor.isAfterLast() == false) {
                hId = new Date(cursor.getLong(cursor.getColumnIndex("id")));
                histories.add(new History(itemId, hId));
                cursor.moveToNext();
            }
        }
        return histories;
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

    private DBHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String sql : SQL_CREATE_TABLES) db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (String sql : SQL_DELETE_TABLES) db.execSQL(sql);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
