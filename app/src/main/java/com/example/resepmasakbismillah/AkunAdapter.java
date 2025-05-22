package com.example.resepmasakbismillah;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AkunAdapter extends RecyclerView.Adapter<AkunAdapter.AkunViewHolder> {
    private List<String> items;

    public AkunAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public AkunViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new AkunViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AkunViewHolder holder, int position) {
        String item = items.get(position);
        holder.textView.setText(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class AkunViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        AkunViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}