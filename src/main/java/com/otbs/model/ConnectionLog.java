package com.otbs.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="connectionlog")
public class ConnectionLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int logId;
	
	@ManyToOne
	@JoinColumn(name = "connection_id", nullable = false)
	private Connection connectionObj;
	
	@Column(nullable = false)
	private String connectionType;
	
	@Column(nullable = false)
	private String changeType;
	
	@Column(nullable = false)
	private LocalDateTime datetime;

	public int getLogId() {
		return logId;
	}

	public void setLogId(int logId) {
		this.logId = logId;
	}

	public Connection getConnectionObj() {
		return connectionObj;
	}

	public void setConnectionObj(Connection connectionObj) {
		this.connectionObj = connectionObj;
	}

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}
	
	
	

}
