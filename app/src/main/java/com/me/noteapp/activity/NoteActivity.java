package com.me.noteapp.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.me.noteapp.NotesApplication;
import com.me.noteapp.R;
import com.me.noteapp.entity.History;
import com.me.noteapp.entity.Item;
import com.me.noteapp.fragment.HistoryFragment;
import com.me.noteapp.fragment.NoteEditFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class NoteActivity extends AppCompatActivity {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    private NoteEditFragment f1;
    private NoteEditFragment f2;
    private HistoryFragment f3;
    private Item mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Long noteId = getIntent().getLongExtra("noteId", 0);
        mItem = NotesApplication.getDataManager().read(noteId);

        if (savedInstanceState == null) {
            f1 = NoteEditFragment.newInstance(mItem, true);
            f2 = NoteEditFragment.newInstance(mItem, false);
            f3 = HistoryFragment.newInstance(mItem);
        }

        displayF1();
        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_view:
                        displayF1();
                        return true;
                    case R.id.navigation_edit:
                        displayF2();
                        return true;
                    case R.id.navigation_history:
                        displayF3();
                        return true;
                }
                return false;
            }
        };
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    protected void displayF1() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (f1.isAdded()) ft.show(f1);
        else ft.add(R.id.noteViewContainer, f1, "A");
        if (f2.isAdded()) ft.hide(f2);
        if (f3.isAdded()) ft.hide(f3);
        ft.commit();
    }

    protected void displayF2() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (f2.isAdded()) ft.show(f2);
        else ft.add(R.id.noteViewContainer, f2, "B");
        if (f1.isAdded()) ft.hide(f1);
        if (f3.isAdded()) ft.hide(f3);
        ft.commit();
    }

    protected void displayF3() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (f3.isAdded()) ft.show(f3);
        else ft.add(R.id.noteViewContainer, f3, "C");
        if (f1.isAdded()) ft.hide(f1);
        if (f2.isAdded()) ft.hide(f2);
        ft.commit();
    }
}
