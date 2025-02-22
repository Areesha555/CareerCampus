package com.example.careercampus;

public class NotificationModel {
    private String jobTitle;
    private String status;  // "Accepted" or "Rejected"
    private String message;
    public NotificationModel() {
    }


    public NotificationModel(String jobTitle, String status, String message) {
        this.jobTitle = jobTitle;
        this.status = status;
        this.message = message;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
