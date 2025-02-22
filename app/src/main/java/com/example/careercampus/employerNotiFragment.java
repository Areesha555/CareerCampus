package com.example.careercampus;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class employerNotiFragment extends Fragment {

    private RecyclerView recyclerView;
    private JobNotiAdapter adapter; // Use the correct adapter name
    private List<EmployerNotificationModel> notificationList; // Correct model class
    private DatabaseReference notificationsRef;
    private FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employer_noti, container, false);

        recyclerView = view.findViewById(R.id.jobnotiRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        notificationList = new ArrayList<>();
        adapter = new JobNotiAdapter(notificationList, getContext()); // Pass both the list and context to the adapter
        recyclerView.setAdapter(adapter);

        // Get Firebase Authentication instance
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            String currentEmployerID = currentUser.getUid(); // Get current user's UID

            // Reference to the current employer's notifications in the database
            notificationsRef = FirebaseDatabase.getInstance().getReference("Users")
                    .child(currentEmployerID).child("Notifications");

            // Fetch notifications from Firebase
            notificationsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    notificationList.clear();
                    List<EmployerNotificationModel> tempList = new ArrayList<>();
                    for (DataSnapshot notificationSnapshot : snapshot.getChildren()) {
                        EmployerNotificationModel notification = notificationSnapshot.getValue(EmployerNotificationModel.class); // Correct model class
                        if (notification != null) {
                            tempList.add(notification);
                        }
                    }
                    for (int i = tempList.size() - 1; i >= 0; i--) {
                        notificationList.add(tempList.get(i));
                    }
                    adapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(0);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("NotificationFragment", "Failed to fetch notifications: " + error.getMessage());
                }
            });
        } else {
            Log.e("NotificationFragment", "No authenticated user found");
        }

        return view;
    }
}
