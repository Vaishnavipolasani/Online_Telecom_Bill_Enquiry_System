package com.otbs.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity

public class Plan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int planId;
	
	@Column(nullable=false)
	private String planName;
	
	@Column(nullable=false)
	private double fixedRate;
	
	@Column(nullable=false)
	private String dataLimit;
	
//	@Column(nullable=false)
	private String callLimit;
	
	@Column(nullable=false)
	private String smsLimit;
	
	//need by bill management team for the bill additiona cost calculation
	private double additionalChargeRatePerMB;
	private double additionalChargeRatePerCall;
	private double additionalChargeRatePerSMS;
	

	
	
	//Getter and setter
	
	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public double getFixedRate() {
		return fixedRate;
	}

	public void setFixedRate(double fixedRate) {
		this.fixedRate = fixedRate;
	}

	public String getDataLimit() {
		return dataLimit;
	}

	public void setDataLimit(String dataLimit) {
		this.dataLimit = dataLimit;
	}

	public String getCallLimit() {
		return callLimit;
	}

	public void setCallLimit(String callLimit) {
		this.callLimit = callLimit;
	}

	public String getSmsLimit() {
		return smsLimit;
	}

	public void setSmsLimit(String smsLimit) {
		this.smsLimit = smsLimit;
	}

	
	
}
