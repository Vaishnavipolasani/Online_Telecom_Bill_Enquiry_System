package com.otbs.model;

import java.time.LocalDate;

public class Bill {
	
	private Connection connectionObj;
	private int billId;
    private String billingDate;
    private int additionalCharges;
    private float discount;
    private float tax;
    private float totalAmount;
    private String status;
    private LocalDate dueDate;


}
