package com.otbs.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
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
	
	@Column(nullable=false)
	private String callLint;
	
	@Column(nullable=false)
	private String smsLimit;
	
}
