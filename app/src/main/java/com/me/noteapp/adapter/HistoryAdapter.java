package com.me.noteapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.me.noteapp.R;

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
        private TextView captionTextView;

        public HistoryVH(@NonNull View itemView) {
            super(itemView);
            historyTextView = itemView.findViewById(R.id.cont);
            captionTextView = itemView.findViewById(R.id.cap);
        }

        public void setHistory(String txt) {
            this.historyTextView.setText(txt);
        }

        public void setCaption(String txt) {
            this.captionTextView.setText(txt);
        }
    }

    @NonNull
    @Override
    public HistoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_view, parent, false);
        return new HistoryVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryVH holder, int position) {
        if (position == 0) holder.setCaption("Created:");
        holder.setHistory(historyList.get(position));
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
}
