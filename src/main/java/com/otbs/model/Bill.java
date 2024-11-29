package com.otbs.model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int billId;

    // Relationship to Connection
    @ManyToOne
    @JoinColumn(name = "connectionId", nullable = false)
    private Connection connection;  // Foreign key to Connection

    // Relationship to Usage
    @OneToOne
    @JoinColumn(name = "usageId", nullable = false)
    private UsageInfo usage;  // Foreign key to Usage

    @Column(name = "date", columnDefinition = "DATE")
    private LocalDate date;

    private int additionalCharges;
    private int discount;
    private double tax;
    private double totalAmount;
    public UsageInfo getUsage() {
		return usage;
	}

	public void setUsage(UsageInfo usage) {
		this.usage = usage;
	}

	private String status;

    @Column(name = "DueDate", columnDefinition = "DATE")
    private LocalDate dueDate;

    // Getters and Setters
    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

//    public Usage getUsage() {
//        return usage;
//    }
//
//    public void setUsage(Usage usage) {
//        this.usage = usage;
//    }

    public int getAdditionalCharges() {
        return additionalCharges;
    }

    public void setAdditionalCharges(int additionalCharges) {
        this.additionalCharges = additionalCharges;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
