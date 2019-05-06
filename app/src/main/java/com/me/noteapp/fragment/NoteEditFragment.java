package com.me.noteapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.me.noteapp.NotesApplication;
import com.me.noteapp.R;
import com.me.noteapp.dialog.DeleteDialogFragment;
import com.me.noteapp.entity.Item;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NoteEditFragment extends Fragment {
    private EditText editNoteView;
    private boolean VIEW_FLAG = false;
    private Item mItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mItem = (Item) getArguments().getSerializable("note");
        if (VIEW_FLAG) return inflateViewFrag(inflater, container);
        setHasOptionsMenu(true);
        return inflateEditFrag(inflater, container);
    }

    private View inflateViewFrag(LayoutInflater inflater, ViewGroup container) {
        View v = inflater.inflate(R.layout.fragment_note_view, container, false);
        ((TextView) v.findViewById(R.id.noteView)).setText(mItem.getContent());
        return v;
    }

    private View inflateEditFrag(LayoutInflater inflater, ViewGroup container) {
        View v = inflater.inflate(R.layout.fragment_note_edit, container, false);
        editNoteView = v.findViewById(R.id.editNoteView);
        editNoteView.setText(mItem.getContent());
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_note, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                final String newContent = editNoteView.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NotesApplication.getDataManager().updateItem(new Item(mItem.getdAt(), newContent));
                        getActivity().finish();
                    }
                }).run();
                return true;
            case R.id.action_delete:
                deleteDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static NoteEditFragment newInstance(Item note, boolean VIEW) {
        NoteEditFragment f = new NoteEditFragment();
        if (VIEW) f.setViewOnlyMode();
        Bundle b = new Bundle();
        b.putSerializable("note", note);
        f.setArguments(b);
        return f;
    }

    private void setViewOnlyMode() {
        this.VIEW_FLAG = true;
    }

    protected void deleteDialog() {
        DeleteDialogFragment ddf = DeleteDialogFragment.newInstance(mItem.getdAt().getTime());
        ddf.show(getActivity().getSupportFragmentManager(), "Delete item");
    }
}
