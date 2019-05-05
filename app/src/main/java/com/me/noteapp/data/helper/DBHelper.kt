package com.me.noteapp.data.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.me.noteapp.config.DBConfig
import com.me.noteapp.entity.Item
import java.util.*
import kotlin.collections.ArrayList

class DBHelper(ctx: Context) : SQLiteOpenHelper(ctx, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_TABLES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertItem(item: Item): Boolean {
        val db = writableDatabase

        val values = ContentValues()
        values.put("dAt", item.getdAt().time)
        values.put("content", item.content)

        val res = db.insert("items", null, values)
        return !res.equals(-1)
    }

    fun updateItem(item: Item): Boolean {
        val db = writableDatabase

        val values = ContentValues()
        val id: Long = item.getdAt().time
        values.put("dAt", id)
        values.put("content", item.content)

        val res = db.update("items", values, "dAt LIKE ?", arrayOf(id.toString()))
        return res > 0
    }

    fun deleteItem(itemId: String): Boolean {
        val db = writableDatabase

        val res = db.delete("items", "dAt LIKE ?", arrayOf(itemId))
        return res > 0
    }

    fun read(id: String): Item? {
        val db = readableDatabase
        var cursor: Cursor? = null
        cursor = db.rawQuery("select * from items where dAt = ?;", Array<String>(1) { id })
        if (cursor!!.moveToFirst()) {
            val dAt = Date(cursor.getLong(cursor.getColumnIndex("dAt")))
            val content = cursor.getString(cursor.getColumnIndex("content"))
            return Item(dAt, content)
        }
        return null
    }

    fun readAllItems(): ArrayList<Item> {
        val items = ArrayList<Item>()
        val db = readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from items i order by i.dAt desc;", null)
        } catch (e: SQLiteException) {
            return ArrayList()
        }

        var dAt: Date
        var content: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                dAt = Date(cursor.getLong(cursor.getColumnIndex("dAt")))
                content = cursor.getString(cursor.getColumnIndex("content"))

                items.add(Item(dAt, content))
                cursor.moveToNext()
            }
        }
        return items
    }

    companion object {
        private var cfg: DBConfig = DBConfig.getInstance()

        fun createTablesQuery(): String {
            var SQL_CREATE_TABLES = ""
            cfg.tables.forEach {
                SQL_CREATE_TABLES += "CREATE TABLE ${it.first} (${it.second});"
            }
            return SQL_CREATE_TABLES
        }

        fun deleteTablesQuery(): String {
            var SQL_DELETE_TABLES = ""
            cfg.tables.forEach {
                SQL_DELETE_TABLES += "DROP TABLE IF EXISTS ${it.first};"
            }
            return SQL_DELETE_TABLES
        }


        val DATABASE_VERSION = 1
        val DATABASE_NAME = cfg.DB_NAME
        private val SQL_CREATE_TABLES = createTablesQuery()
        private val SQL_DELETE_TABLES = deleteTablesQuery()
    }
}