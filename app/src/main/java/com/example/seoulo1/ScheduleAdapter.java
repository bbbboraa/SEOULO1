package com.example.seoulo1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    private final List<String> scheduleList;

    public ScheduleAdapter(List<String> scheduleList) {
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String scheduleText = scheduleList.get(position);
        holder.scheduleTextView.setText(scheduleText);
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    // 추가된 메서드: 데이터 갱신을 위한 메서드
    public void updateData(List<String> updatedList) {
        scheduleList.clear();
        scheduleList.addAll(updatedList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView scheduleTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            scheduleTextView = itemView.findViewById(R.id.scheduleTextView);
        }
    }
}
