package com.me.noteapp.config;

public class FileConfig {
    private static final FileConfig ourInstance = new FileConfig();
    public String FILE_NAME = "db.xml";


    public static FileConfig getInstance() {
        return ourInstance;
    }

    private FileConfig() {
    }
}
