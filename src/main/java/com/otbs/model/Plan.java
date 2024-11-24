package com.otbs.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Plan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int planId;
	private String planName;
	private double fixedRate;
	private String dataLimit;
	private String callLint;
	private String smsLimit;
	
	
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
	public String getCallLint() {
		return callLint;
	}
	public void setCallLint(String callLint) {
		this.callLint = callLint;
	}
	public String getSmsLimit() {
		return smsLimit;
	}
	public void setSmsLimit(String smsLimit) {
		this.smsLimit = smsLimit;
	}	

}
