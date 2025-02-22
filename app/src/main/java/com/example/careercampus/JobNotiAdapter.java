package com.example.careercampus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JobNotiAdapter extends RecyclerView.Adapter<JobNotiAdapter.JobApplicationViewHolder> {

    private List<EmployerNotificationModel> jobApplicationList;
    private Context context;

    public JobNotiAdapter(List<EmployerNotificationModel> jobApplicationList, Context context) {
        this.jobApplicationList = jobApplicationList;
        this.context = context;
    }

    @NonNull
    @Override
    public JobApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job_noti, parent, false);
        return new JobApplicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobApplicationViewHolder holder, int position) {
        EmployerNotificationModel application = jobApplicationList.get(position);
        holder.applicantNameTextView.setText("Applicant: " + application.getEmployeeName());
        holder.messageTextView.setText(application.getMessage()); // Display custom message
        holder.jobCategoryTextView.setText("Job Category: " + application.getJobCategory());

        // Display timestamp (if you want to show the exact time)
        String timestamp = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm")
                .format(new java.util.Date(application.getTimestamp()));
        holder.timestampTextView.setText("Applied on: " + timestamp);

        // Handle the Accept button click
        holder.acceptButton.setOnClickListener(v -> {
            Toast.makeText(context, "Accepted " + application.getEmployeeName(), Toast.LENGTH_SHORT).show();
            // Add your logic for accepting the application
        });

        // Handle the Reject button click
        holder.rejectButton.setOnClickListener(v -> {
            Toast.makeText(context, "Rejected " + application.getEmployeeName(), Toast.LENGTH_SHORT).show();
            // Add your logic for rejecting the application
        });
    }

    @Override
    public int getItemCount() {
        return jobApplicationList.size();
    }
    public void addNewJobApplication(EmployerNotificationModel newApplication) {
        jobApplicationList.add(0, newApplication);
        notifyItemInserted(0);
    }


    static class JobApplicationViewHolder extends RecyclerView.ViewHolder {
        TextView applicantNameTextView;
        TextView messageTextView;
        TextView jobCategoryTextView;
        TextView timestampTextView;
        Button acceptButton;
        Button rejectButton;

        public JobApplicationViewHolder(@NonNull View itemView) {
            super(itemView);
            applicantNameTextView = itemView.findViewById(R.id.applicantNameTextView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            jobCategoryTextView = itemView.findViewById(R.id.jobcategoryTextView); // Added for jobCategory
            timestampTextView = itemView.findViewById(R.id.timestampTextView); // Added for timestamp
            acceptButton = itemView.findViewById(R.id.acceptButton);
            rejectButton = itemView.findViewById(R.id.rejectButton);
        }
    }
}
