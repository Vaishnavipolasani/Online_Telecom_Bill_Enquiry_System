package com.otbs.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class HelpDeskExecutive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int executiveID;

    private String name;
    private String email;
    private String contactNumber;

    @OneToMany(mappedBy = "assignedTo", cascade = CascadeType.ALL)
    private List<Complaint> complaintsAssigned;

    // Getters and Setters
    public int getExecutiveID() {
        return executiveID;
    }

    public void setExecutiveID(int executiveID) {
        this.executiveID = executiveID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public List<Complaint> getComplaintsAssigned() {
        return complaintsAssigned;
    }

    public void setComplaintsAssigned(List<Complaint> complaintsAssigned) {
        this.complaintsAssigned = complaintsAssigned;
    }
}
