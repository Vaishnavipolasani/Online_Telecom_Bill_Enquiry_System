package com.otbs.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

 

@Entity
@Table(name="connection")
public class Connection {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int connectionId;
	
	// Many connections can belong to one customer
	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customerObj;
	
	@Column(nullable = false)
	private String connectionType;
	
	@Column(nullable = false)
	private String networkType;
	
	@Column(nullable = false)
	private float processingFee;
	
	// Many connections can be associated with one outlet
	@ManyToOne
	@JoinColumn(name = "outlet_id", nullable = false)
	private Outlet outletObj;
	
	@ManyToOne
	@JoinColumn(name = "planId")
		private Plan plan;

	@OneToMany(mappedBy = "connection", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties("connection")
	private List<Complaint> complaintsRaised;
	
	 public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	@Column(nullable = false)
	private String status;

	public int getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(int connectionId) {
		this.connectionId = connectionId;
	}

	public Customer getCustomerObj() {
		return customerObj;
	}

	public void setCustomerObj(Customer customerObj) {
		this.customerObj = customerObj;
	}

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public float getProcessingFee() {
		return processingFee;
	}

	public void setProcessingFee(float processingFee) {
		this.processingFee = processingFee;
	}

	public Outlet getOutletObj() {
		return outletObj;
	}

	public void setOutletObj(Outlet outletObj) {
		this.outletObj = outletObj;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	 
	 
}
	