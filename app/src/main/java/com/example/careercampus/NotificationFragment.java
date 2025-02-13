package com.example.careercampus;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    private RecyclerView notificationsRecyclerView;
    private NotificationAdapter notificationAdapter;
    private List<NotificationModel> notificationsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        // Initialize RecyclerView and adapter
        notificationsRecyclerView = view.findViewById(R.id.notificationsRecyclerView);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the list of notifications
        notificationsList = new ArrayList<>();
        notificationsList.add(new NotificationModel("Software Engineer", "Accepted", "Congratulations! You have been accepted for the Software Engineer position."));
        notificationsList.add(new NotificationModel("Data Analyst", "Rejected", "Sorry, your application for the Data Analyst position was rejected."));
        notificationsList.add(new NotificationModel("Software Engineer", "Accepted", "Congratulations! You have been accepted for the Software Engineer position."));
        notificationsList.add(new NotificationModel("Data Analyst", "Rejected", "Sorry, your application for the Data Analyst position was rejected."));
        notificationsList.add(new NotificationModel("Software Engineer", "Accepted", "Congratulations! You have been accepted for the Software Engineer position."));
        notificationsList.add(new NotificationModel("Data Analyst", "Rejected", "Sorry, your application for the Data Analyst position was rejected."));
        notificationAdapter = new NotificationAdapter(notificationsList);
        notificationsRecyclerView.setAdapter(notificationAdapter);

        return view;
    }
}
