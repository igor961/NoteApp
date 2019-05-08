package com.me.noteapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.me.noteapp.R;
import com.me.noteapp.activity.NoteActivity;
import com.me.noteapp.entity.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import androidx.recyclerview.widget.RecyclerView;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.MyViewHolder> implements Filterable, Observer {
    private List<Item> mDataSet;
    private List<Item> mDataSetFiltered;

    private boolean DESK_FLAG = true;

    public NotesListAdapter(List<Item> dataSet) {
        this.mDataSet = dataSet;
        this.mDataSetFiltered = dataSet;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchStr = constraint.toString().trim().toLowerCase();
                if (searchStr.isEmpty()) mDataSetFiltered = mDataSet;
                else {
                    List<Item> fList = new ArrayList<>();
                    for (Item obj : mDataSet) {
                        if (obj.getContent().toLowerCase().contains(searchStr)) fList.add(obj);
                    }
                    mDataSetFiltered = fList;
                }
                FilterResults fr = new FilterResults();
                fr.values = mDataSetFiltered;
                return fr;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDataSetFiltered = (List<Item>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void sort() {
        if (DESK_FLAG) {
            Collections.sort(mDataSetFiltered, new Comparator<Item>() {
                @Override
                public int compare(Item o1, Item o2) {
                    return o1.getdAt().compareTo(o2.getdAt());
                }
            });
            DESK_FLAG = false;
        } else {
            Collections.sort(mDataSetFiltered, new Comparator<Item>() {
                @Override
                public int compare(Item o1, Item o2) {
                    return o2.getdAt().compareTo(o1.getdAt());
                }
            });
            DESK_FLAG = true;
        }
        notifyDataSetChanged();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof List) {
            this.mDataSet = (List<Item>) arg;
            this.mDataSetFiltered = (List<Item>) arg;
            notifyDataSetChanged();
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView[] tv;

        public MaterialButton getBtn() {
            return btn;
        }

        private MaterialButton btn;

        public MyViewHolder(View v) {
            super(v);
            tv = new TextView[4];
            tv[0] = v.findViewById(R.id.tv1);
            tv[1] = v.findViewById(R.id.tv2);
            tv[2] = v.findViewById(R.id.tv3);
            btn = v.findViewById(R.id.select_btn);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), NoteActivity.class);
                    i.putExtra("noteId", (Long) v.getTag());
                    v.getContext().startActivity(i);
                }
            });
        }

        public void mSetText(String[] txt) {
            this.tv[0].setText(txt[0]);
            this.tv[1].setText(txt[1]);
            this.tv[2].setText(txt[2]);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_view, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item i = mDataSetFiltered.get(position);
        holder.getBtn().setTag(i.getdAt().getTime());
        String cont = i.getContent();
        final String contRes = cont.length() > 15 ? cont.substring(0, 15) + "..." : cont;
        holder.mSetText(new String[]{i.getDate(), i.getTime(), contRes});
    }

    @Override
    public int getItemCount() {
        return mDataSetFiltered.size();
    }


}
