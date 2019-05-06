package com.me.noteapp.config;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class DBConfig {
    private static final DBConfig ourInstance = new DBConfig();
    public String DB_NAME = "notes_app";
    public List<Pair<String, String>> tables;

    public static DBConfig getInstance() {
        return ourInstance;
    }

    private DBConfig() {
        tables = new ArrayList<>();
        String t1 = new String();
        t1 += "dAt INTEGER, ";
        t1 += "content TEXT, ";
        t1 += "PRIMARY KEY(dAt)";
        tables.add(new Pair("items", t1));
    }


    public String createTablesQuery() {
        return "";
    }

    public String deleteTablesQuery() {
        return "";
    }
}
