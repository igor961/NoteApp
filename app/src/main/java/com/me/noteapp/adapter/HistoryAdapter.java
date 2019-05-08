package com.me.noteapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryVH> {

    private List<String> historyList;

    public HistoryAdapter(List<String> items) {
        this.historyList = items;
    }

    class HistoryVH extends RecyclerView.ViewHolder {
        private TextView historyTextView;

        public HistoryVH(@NonNull View itemView) {
            super(itemView);
            historyTextView = (TextView) itemView;
        }

        public void setHistory(String txt) {
            this.historyTextView.setText(txt);
        }
    }

    @NonNull
    @Override
    public HistoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = new TextView(parent.getContext());
        return new HistoryVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryVH holder, int position) {
        holder.setHistory(historyList.get(position));
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
}
