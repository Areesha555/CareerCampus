package com.example.careercampus;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<NotificationModel> notificationsList;

    public NotificationAdapter(List<NotificationModel> notificationsList) {
        this.notificationsList = notificationsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationModel notification = notificationsList.get(position);
        holder.jobTitleTextView.setText(notification.getJobTitle());
        holder.statusTextView.setText(notification.getStatus());
        holder.messageTextView.setText(notification.getMessage());

        // Set different colors or styles depending on the status
        if (notification.getStatus().equals("Accepted")) {
            holder.statusTextView.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.green));
        } else {
            holder.statusTextView.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView jobTitleTextView, statusTextView, messageTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            jobTitleTextView = itemView.findViewById(R.id.jobTitleTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
        }
    }
}
