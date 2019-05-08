package com.me.noteapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.me.noteapp.NotesApplication;
import com.me.noteapp.R;
import com.me.noteapp.adapter.HistoryAdapter;
import com.me.noteapp.entity.History;
import com.me.noteapp.entity.Item;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryFragment extends Fragment {
    private HistoryAdapter mAdapter;
    private RecyclerView histCont;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        Item note = (Item) getArguments().getSerializable("note");
        List<String> historyCont = NotesApplication.getDataManager().getHistories(note.getdAt().getTime());
        historyCont.add(0, note.getDate() + " " + note.getTime());
        mAdapter = new HistoryAdapter(historyCont);
        histCont = v.findViewById(R.id.history_container);
        histCont.setLayoutManager(new LinearLayoutManager(v.getContext()));
        histCont.setAdapter(mAdapter);
        return v;
    }

    public static HistoryFragment newInstance(Item i) {
        HistoryFragment f = new HistoryFragment();
        Bundle b = new Bundle();
        b.putSerializable("note", i);
        f.setArguments(b);
        return f;
    }
}
