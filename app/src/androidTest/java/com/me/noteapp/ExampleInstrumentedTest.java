package com.me.noteapp;

import android.content.Context;

import com.me.noteapp.data.DBDataManager;
import com.me.noteapp.entity.Item;
import com.me.noteapp.utility.NoteParser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void usingTestData() {
        // Context of the app under test.
        List<Item> ex1 = new ArrayList<>();
        ex1.add(new Item(new Date(1556650266), "Hello, world!"));

        String sampleContents = "<list>";
        sampleContents += "<item>";
        sampleContents += "<dAt>";
        sampleContents += "1556650266";
        sampleContents += "</dAt>";
        sampleContents += "<content>";
        sampleContents += "Hello, world!";
        sampleContents += "</content>";
        sampleContents += "</item>";
        sampleContents += "</list>";

        InputStream is = new ByteArrayInputStream(sampleContents.getBytes());
        List<Item> ex2Arr = null;
        try {
            ex2Arr = new NoteParser().parse(is);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Item ex2 = null;
        if (!ex2Arr.isEmpty())
            ex2 = ex2Arr.get(0);
        assertNotNull(ex2Arr);
        assertEquals(ex1, ex2);
    }

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
