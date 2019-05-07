package com.me.noteapp;

import android.app.Application;

import com.me.noteapp.data.DBDataManager;

public class NotesApplication extends Application {
    private static DBDataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();
        dataManager = new DBDataManager(this);
        dataManager.reloadData();
    }

    public static DBDataManager getDataManager() {
        return dataManager;
    }

}
