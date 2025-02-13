package com.example.careercampus;

public class EmployerNotificationModel {
    private String employeeName;
    private String jobCategory;
    private String message;
    private long timestamp;

    // Empty constructor required for Firebase
    public EmployerNotificationModel() {
    }

    public EmployerNotificationModel(String employeeName, String jobCategory,String message, long timestamp) {
        this.message = message;
        this.employeeName = employeeName;
        this.jobCategory = jobCategory;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(String jobCategory) {
        this.jobCategory = jobCategory;
    }
    public String getMessage() {
        return message; // Getter for message
    }

    public void setMessage(String message) {
        this.message = message; // Setter for message
    }


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}



