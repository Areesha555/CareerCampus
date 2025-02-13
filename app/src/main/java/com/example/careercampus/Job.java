package com.example.careercampus;

public class Job {
    private String jobTitle;
    private String companyName;
    private String designation2;
    private String skills2;
    private int imageResId;  // Resource ID of the image

    // Constructor
    public Job(String jobTitle, String companyName, String designation2, String skills2, int imageResId) {
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.designation2 = designation2;
        this.skills2 = skills2;
        this.imageResId = imageResId;
    }

    // Getters
    public String getJobTitle() {
        return jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getDesignation2() { return designation2;}
    public String getskills2() { return skills2;}

    public int getImageResId() {
        return imageResId;
    }
}
