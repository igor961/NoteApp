package com.me.noteapp.utility;

import android.util.Xml;

import com.me.noteapp.entity.Item;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteParser {
    private static final String ns = null;

    public List<Item> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readNode(parser, "list");
        } finally {
            in.close();
        }
    }

    private List<Item> readNode(XmlPullParser parser, String node) throws IOException, XmlPullParserException {
        List<Item> items = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, node);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) continue;
            String name = parser.getName();

            if (name.equals("item")) items.add(parseItem(parser));
            else skip(parser);

        }
        return items;
    }

    private Item parseItem(XmlPullParser parser) throws IOException, XmlPullParserException {
        long d = 0;
        String str = "";
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) continue;
            String name = parser.getName().toLowerCase();
            switch (name) {
                case "dat":
                    d = Long.parseLong(readText(parser));
                    break;
                case "content":
                    str = readText(parser);
                    break;
                default:
                    skip(parser);
            }

        }

        return new Item(new Date(d), str);
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

}
