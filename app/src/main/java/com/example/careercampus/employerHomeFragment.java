package com.example.careercampus;

import static com.example.careercampus.R.id.jobformRecyclerView11;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class employerHomeFragment extends Fragment {

    private List<JobModel> jobList = new ArrayList<>();
    private JobFormAdapter jobFormAdapter;
    private RecyclerView jobRecyclerView;
    private ImageButton createJobBtn;
    Context context;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employer_home, container, false);

        jobRecyclerView = view.findViewById(jobformRecyclerView11);
        createJobBtn = view.findViewById(R.id.createjob_btn);

        context = requireContext();

        FirebaseDatabase.getInstance().getReference("jobs")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        jobList.clear();
                        if (snapshot.exists()){
                            for (DataSnapshot ds: snapshot.getChildren()){
                                jobList.add(ds.getValue(JobModel.class));
                            }
                            jobFormAdapter.setJobList(jobList);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        jobFormAdapter = new JobFormAdapter(jobList, new JobFormAdapter.OnJobActionListener() {
            @Override
            public void onEditJob(int position) {
                showJobFormDialog(position);
            }

            @Override
            public void onDeleteJob(int position) {
                jobList.remove(position);
                jobFormAdapter.notifyItemRemoved(position);
            }
        });

        jobRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        jobRecyclerView.setAdapter(jobFormAdapter);

        createJobBtn.setOnClickListener(v -> showJobFormDialog(-1));  // Pass -1 for new job creation

        return view;
    }

    private void showJobFormDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_job_form, null);
        builder.setView(dialogView);

        EditText jobCategoryInput = dialogView.findViewById(R.id.edit_job_category);
        EditText designationInput = dialogView.findViewById(R.id.edit_designation);
        EditText skillsInput = dialogView.findViewById(R.id.edit_skills);
        Button saveButton = dialogView.findViewById(R.id.btn_save_job);

        AlertDialog dialog = builder.create();

        if (position != -1) {
            JobModel job = jobList.get(position);
            jobCategoryInput.setText(job.getJobCategory());
            designationInput.setText(job.getDesignation());
            skillsInput.setText(job.getSkills());
        }

        saveButton.setOnClickListener(v -> {
            String jobCategory = jobCategoryInput.getText().toString();
            String designation = designationInput.getText().toString();
            String skills = skillsInput.getText().toString();
            String jobID = FirebaseDatabase.getInstance().getReference("jobs").push().getKey();
            String employerID = FirebaseAuth.getInstance().getCurrentUser().getUid();

            if (!jobCategory.isEmpty() && !designation.isEmpty() && !skills.isEmpty()) {
                // Create a new JobModel instance
                JobModel newJob = new JobModel(  jobID, employerID,"Company Name", R.drawable.profilephoto, jobCategory, designation, skills);

                // Get a reference to Firebase Realtime Database
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("jobs");

                // Generate a unique key for each job
                String jobId = databaseReference.push().getKey();
                if (jobId != null) {
                    // Store the new job in the database
                    databaseReference.child(jobId).setValue(newJob)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(context, "Job saved successfully!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(context, "Failed to save job. Try again.", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(context, "Failed to generate job ID.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "All fields are required!", Toast.LENGTH_SHORT).show();
            }
        });


        dialog.show();
    }
}

