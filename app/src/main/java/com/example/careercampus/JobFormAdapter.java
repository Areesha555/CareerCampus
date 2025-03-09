package com.example.careercampus;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class JobFormAdapter extends RecyclerView.Adapter<JobFormAdapter.JobViewHolder> {

    private List<JobModel> jobList;
    private OnJobActionListener onJobActionListener;

    public void setJobList(List<JobModel> jobList) {
        this.jobList = jobList;
        notifyDataSetChanged();
    }

    public JobFormAdapter(List<JobModel> jobList, OnJobActionListener onJobActionListener) {
        this.jobList = (jobList != null) ? jobList : new ArrayList<>();
        this.onJobActionListener = onJobActionListener;
    }

    @Override
    public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employer_job_item, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JobViewHolder holder, int position) {
        JobModel job = jobList.get(position);

        holder.companyName.setText(job.getCompanyName());
        holder.companyProfilePic.setImageResource(job.getCompanyProfilePic()); // Assuming it's a resource ID
        holder.jobCategory.setText(job.getJobCategory());
        holder.designation.setText(job.getDesignation());
        holder.skills.setText(job.getSkills());

        // Edit button
        holder.editButton.setOnClickListener(v -> onJobActionListener.onEditJob(position));

        // Delete button
        holder.deleteButton.setOnClickListener(v -> onJobActionListener.onDeleteJob(position));
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public void updateJob(int position, JobModel updatedJob) {
        jobList.set(position, updatedJob); // Update the job locally
        notifyItemChanged(position); // Notify the adapter about the change

        // Update the job in Firebase
        DatabaseReference jobRef = FirebaseDatabase.getInstance().getReference("jobs").child(updatedJob.getJobID());
        jobRef.setValue(updatedJob)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        // Handle the error, e.g., show a toast or log the error
                    }
                });
    }

    public void deleteJob(int position) {
        if (position < 0 || position >= jobList.size()) {
            Log.e("DeleteJobError", "Invalid position: " + position);
            return;
        }

        JobModel jobToDelete = jobList.get(position);
        String jobID = jobToDelete.getJobID();

        if (jobID == null || jobID.isEmpty()) {
            Log.e("DeleteJobError", "Job ID is null or empty");
            return;
        }

        Log.d("DeleteJobDebug", "Attempting to delete job with ID: " + jobID);

        // Remove the job from Firebase
        DatabaseReference jobRef = FirebaseDatabase.getInstance().getReference("jobs").child(jobID);
        Log.d("FirebaseDebug", "Deleting job from path: " + jobRef.toString());

        jobRef.removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("DeleteJobDebug", "Job deleted successfully from Firebase");

                        // Remove the job from the local list only if Firebase deletion is successful
                        jobList.remove(position);
                        Log.d("DeleteJobDebug", "Job removed from local list. New list size: " + jobList.size());

                        // Notify the adapter that the item has been removed
                        notifyItemRemoved(position);
                        Log.d("DeleteJobDebug", "RecyclerView notified of item removal at position: " + position);
                    } else {
                        Log.e("DeleteJobError", "Failed to delete job from Firebase: " + task.getException().getMessage());
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("DeleteJobError", "Firebase delete operation failed: " + e.getMessage());
                });
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView companyName, jobCategory, designation, skills;
        ImageView companyProfilePic;
        AppCompatImageButton editButton, deleteButton;

        public JobViewHolder(View itemView) {
            super(itemView);
            companyName = itemView.findViewById(R.id.company_name);
            companyProfilePic = itemView.findViewById(R.id.company_profile_pic);
            jobCategory = itemView.findViewById(R.id.job_category);
            designation = itemView.findViewById(R.id.job_designation);
            skills = itemView.findViewById(R.id.job_skills);
            editButton = itemView.findViewById(R.id.btn_edit);
            deleteButton = itemView.findViewById(R.id.btn_delete);
        }
    }

    public interface OnJobActionListener {
        void onEditJob(int position);
        void onDeleteJob(int position);
    }
}

