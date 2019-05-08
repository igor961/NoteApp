package com.me.noteapp.dialog;

import android.os.Bundle;
import android.widget.EditText;

import com.me.noteapp.NotesApplication;
import com.me.noteapp.R;
import com.me.noteapp.entity.Item;

import java.util.Date;

public class ItemDialogFragment extends DefaultDialog {

    @Override
    protected boolean finish() {
        return false;
    }

    @Override
    protected int getRes() {
        return R.layout.fragment_item_dialog;
    }

    @Override
    void action() {
        String contentStr = ((EditText) getDialog().findViewById(R.id.contentInput)).getText().toString();
        NotesApplication.getDataManager().insertItem(new Item(new Date(), contentStr));
    }

    public static ItemDialogFragment newInstance() {
        ItemDialogFragment f = new ItemDialogFragment();
        return f;
    }
}
