package com.example.careercampus;

import java.io.Serializable;

public class JobModel implements Serializable {
    private String jobID;
    private String employerID;
    private String companyName;
    private int companyProfilePic;
    private String jobCategory;
    private String designation;
    private String skills;

    // Constructor
    public JobModel( String jobID, String employerID,String companyName, int companyProfilePic, String jobCategory, String designation, String skills) {
        this.jobID = jobID;
        this.employerID = employerID;
        this.companyName = companyName;
        this.companyProfilePic = companyProfilePic;
        this.jobCategory = jobCategory;
        this.designation = designation;
        this.skills = skills;
    }

    public JobModel() {
    }

    // Getters and Setters
    public String getJobID() {return jobID;}

    public String getEmployerID() {return employerID;}

    public String getCompanyName() {
        return companyName;
    }

    public int getCompanyProfilePic() {
        return companyProfilePic;
    }

    public String getJobCategory() {
        return jobCategory;
    }

    public String getDesignation() {
        return designation;
    }

    public String getSkills() {
        return skills;
    }
    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public void setEmployerID(String employerID) {
        this.employerID = employerID;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyProfilePic(int companyProfilePic) {
        this.companyProfilePic = companyProfilePic;
    }

    public void setJobCategory(String jobCategory) {
        this.jobCategory = jobCategory;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }


}



