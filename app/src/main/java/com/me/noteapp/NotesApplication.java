package com.me.noteapp;

import android.app.Application;

import com.me.noteapp.data.DBDataManager;
import com.me.noteapp.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class NotesApplication extends Application {
    private static DBDataManager dataManager;
    private static List<Item> testData;

    @Override
    public void onCreate() {
        super.onCreate();
        testData = new ArrayList<>();

        dataManager = new DBDataManager(this);
        dataManager.reloadData();
    }

    public static DBDataManager getDataManager() {
        return dataManager;
    }

    public static List<Item> getTestData() {
        return testData;
    }

    public static void setTestData(List<Item> testData) {
        NotesApplication.testData.clear();
        NotesApplication.testData.addAll(testData);
    }

}
