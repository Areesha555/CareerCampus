package com.example.careercampus;
import android.util.Log;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class FcmSender {
    private static final String FCM_URL = "https://fcm.googleapis.com/v1/projects/careercampus-3e43a/messages:send";

    public static void sendNotification(String employerToken, String employeeName) throws Exception {

        try {
            // Get the access token from the AccessTokenProvider class
            String accessToken = AccessToken.getAccessToken();
            if (accessToken == null) {
                System.out.println("Failed to obtain access token");
                return;
            }

            // JSON payload for FCM including employeeName and jobName in the notification body
            String jsonMessage = "{"
                    + "\"message\": {"
                    + "\"token\": \"" + employerToken + "\","
                    + "\"notification\": {"
                    + "\"title\": \"New Job Application\","
                    + "\"body\": \"" + employeeName + " applied for the job: " + "\""
                    + "},"
                    + "\"data\": {"
                    + "\"employeeName\": \"" + employeeName + "\","
                    + "\"jobName\": \"" + "\""
                    + "}"
                    + "}"
                    + "}";

            Log.d("NOTI_TAG", "sendNotification: JSON: " + jsonMessage);

            // Prepare the connection
            URL url = new URL(FCM_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Write the JSON payload
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonMessage.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Check response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Notification sent successfully.");
            } else {
                System.out.println("Failed to send notification. Response code: " + responseCode);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}



