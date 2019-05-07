package com.me.noteapp;

import android.content.Context;

import com.me.noteapp.data.DBDataManager;
import com.me.noteapp.entity.Item;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void readingItem() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DBDataManager dbDataManager = new DBDataManager(appContext);
        Date d1 = new Date();
        Item i1 = new Item(d1, "Hello");
        dbDataManager.insertItem(i1);
        Item i2 = dbDataManager.read(d1.getTime());
        assertEquals(i1.getdAt(), i2.getdAt());
        assertEquals(i1.getContent(), i2.getContent());
    }

}
