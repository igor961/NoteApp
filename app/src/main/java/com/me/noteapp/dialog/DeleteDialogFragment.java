package com.me.noteapp.dialog;

import android.os.Bundle;

import com.me.noteapp.NotesApplication;
import com.me.noteapp.R;

//R.layout.fragment_delete_dialog

public class DeleteDialogFragment extends DefaultDialog {


    public static DeleteDialogFragment newInstance(long id) {
        Bundle b = new Bundle();
        b.putLong("id", id);
        DeleteDialogFragment ddf = new DeleteDialogFragment();
        ddf.setArguments(b);
        return ddf;
    }

    @Override
    protected boolean finish() {
        return true;
    }

    @Override
    protected int getRes() {
        return R.layout.fragment_delete_dialog;
    }

    @Override
    void action() {
        NotesApplication.getDataManager().deleteItem(getArguments().getLong("id"));
        getActivity().finish();
    }
}
