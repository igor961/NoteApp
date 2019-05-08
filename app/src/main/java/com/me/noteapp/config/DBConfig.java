package com.me.noteapp.config;

import com.me.noteapp.annotation.Column;
import com.me.noteapp.annotation.Id;
import com.me.noteapp.annotation.Reference;
import com.me.noteapp.entity.History;
import com.me.noteapp.entity.Item;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DBConfig {
    private static final DBConfig ourInstance = new DBConfig();
    public String DB_NAME = "notes_app";
    private List<String> tNames;
    private List<String> tables;

    public static DBConfig getInstance() {
        return ourInstance;
    }

    private DBConfig() {
        tables = new ArrayList<>();
        tNames = new ArrayList<>();
        String s2 = createTable(History.class);
        String s1 = createTable(Item.class);
        if (s1 != null) tables.add(s1);
        if (s2 != null) tables.add(s2);
    }

    private String createTable(Class c) {
        if (c != null) {
            Field[] fs = c.getDeclaredFields();
            if (fs.length == 0) return null;
            String primaryKey = "";
            Class foreignKeyClass = null;
            String foreignKeyC = "";
            String foreignKeyP = "";
            StringBuilder s1 = new StringBuilder();
            String sName = c.getSimpleName();
            String tName = genTName(sName);
            tNames.add(tName);
            s1.append("CREATE TABLE IF NOT EXISTS " + tName + " (");
            for (Field f : fs) {
                f.setAccessible(true);
                if (f.isAnnotationPresent(Column.class)) {
                    String name = f.getName();
                    DBType type = f.getAnnotation(Column.class).type();
                    if (f.isAnnotationPresent(Id.class)) primaryKey = name;
                    else if (f.isAnnotationPresent(Reference.class)) {
                        foreignKeyClass = f.getAnnotation(Reference.class).table();
                        foreignKeyP = findId(foreignKeyClass);
                        foreignKeyC = name;
                    }
                    s1.append(name + " " + type + ", ");
                }
            }
            if (primaryKey.isEmpty()) for (int i = 0; i < 2; ++i) s1.deleteCharAt(s1.length() - 1);
            else s1.append("PRIMARY KEY(" + primaryKey + ")");
            if (!foreignKeyP.isEmpty()) s1.append(", FOREIGN KEY(" + foreignKeyC
                    + ") REFERENCES " + genTName(foreignKeyClass.getSimpleName())
                    + "(" + foreignKeyP + ")");
            return s1.append(")").toString();
        }
        return null;
    }

    private String findId(Class c) {
        Field[] fs = c.getDeclaredFields();
        for (Field f : fs) {
            f.setAccessible(true);
            String name = f.getName();
            if (f.isAnnotationPresent(Id.class)) return name;
        }
        return null;
    }

    private String genTName(String cName) {
        String res = cName.toLowerCase();
        res = (res.charAt(res.length() - 1) == 'y') ? res.substring(0, res.length() - 1).concat("ies") : res + "s";
        return res;
    }


    public List<String> getCreateTablesQuery() {
        return tables;
    }

    public List<String> getDeleteTablesQuery() {
        List<String> res = new ArrayList<>();
        for (String n : tNames) res.add("DROP TABLE IF EXISTS " + n);
        return res;
    }

    public List<String> getTableNames() {
        return tNames;
    }
}
