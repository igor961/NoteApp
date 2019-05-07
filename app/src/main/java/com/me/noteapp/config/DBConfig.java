package com.me.noteapp.config;

import com.me.noteapp.annotation.Column;
import com.me.noteapp.annotation.Id;
import com.me.noteapp.entity.Item;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DBConfig {
    private static final DBConfig ourInstance = new DBConfig();
    public String DB_NAME = "notes_app";
    private List<String> tNames;
    private String tables;

    public static DBConfig getInstance() {
        return ourInstance;
    }

    private DBConfig() {
        tables = "";
        tNames = new ArrayList<>();
        String s1 = createTable(Item.class);
        //String s2 = createTable(Item.class);
        if (s1 != null) tables += s1;
        //tables.concat(s2);
    }

    private String createTable(Class c) {
        Field[] fs = c.getDeclaredFields();
        if (fs.length == 0) return null;
        String primaryKey = "";
        String s1;
        String tName = c.getSimpleName().toLowerCase()+"s";
        tNames.add(tName);
        s1 = "CREATE TABLE " + tName + " (";
        for (Field f : fs) {
            f.setAccessible(true);
            if (f.isAnnotationPresent(Column.class)) {
                String name = f.getName();
                DBType type = f.getAnnotation(Column.class).type();
                if (f.isAnnotationPresent(Id.class)) primaryKey = name;
                s1 += name + " " + type + ", ";
            }
        }
        if (primaryKey.isEmpty()) s1 = s1.substring(0, s1.length() - 2).concat(");");
        else s1 = s1.concat("PRIMARY KEY(").concat(primaryKey).concat("));");
        return s1;
    }


    public String getCreateTablesQuery() {
        return tables;
    }

    public String getDeleteTablesQuery() {
        String res = "";
        for (String n : tNames) res += "DROP TABLE " + n + ";";
        return res;
    }
}
