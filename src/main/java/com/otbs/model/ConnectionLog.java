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

@Getter
@Setter
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
	
	
	

}
