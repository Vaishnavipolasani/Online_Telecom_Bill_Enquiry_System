package com.otbs.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Complaint {
	@Id
	
	private int complaintId;
	private User userObj;
	
	//missed
	private Customer customerObj;
	
	private String description;
    private String complaintType;
    private String status;
    private String priority;
@ManyToOne
@JoinColumn(name="raised_by",nullable=false)
private Customer raisedBy;
@ManyToOne
@JoinColumn(name="assigned_to",nullable=false)
private HelpDeskExecutive assignedTo;

}
