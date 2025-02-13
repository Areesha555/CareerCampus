package com.example.careercampus;

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
        jobList.set(position, updatedJob);
        notifyItemChanged(position);
    }

    public void deleteJob(int position) {
        jobList.remove(position);
        notifyItemRemoved(position);
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

