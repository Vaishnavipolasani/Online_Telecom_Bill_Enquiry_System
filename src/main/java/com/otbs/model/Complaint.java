package com.otbs.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int complaintID;

    @ManyToOne
    @JoinColumn(name = "raised_by", nullable = false)
    private Customer raisedBy;

    @ManyToOne
    @JoinColumn(name = "assigned_to", nullable = true) 
    private HelpDeskExecutive assignedTo;

    private String complaintType;
    private String description;
    private String status;
    private String priority;
    private Date createdDate;
    private Date resolvedDate;

    // Getters and Setters
    public int getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(int complaintID) {
        this.complaintID = complaintID;
    }

    public Customer getRaisedBy() {
        return raisedBy;
    }

    public void setRaisedBy(Customer raisedBy) {
        this.raisedBy = raisedBy;
    }

    public HelpDeskExecutive getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(HelpDeskExecutive assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getResolvedDate() {
        return resolvedDate;
    }

    public void setResolvedDate(Date resolvedDate) {
        this.resolvedDate = resolvedDate;
    }
}
