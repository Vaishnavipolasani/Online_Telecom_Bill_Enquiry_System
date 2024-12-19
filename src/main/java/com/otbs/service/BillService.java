package com.otbs.service;

import java.util.List;
import java.util.Optional;

import com.otbs.model.Bill;

public interface BillService {
    void generatePrepaidBill(int connectionId);
    void generatePostpaidBill(int connectionId);
    void generateBillsForAllConnections(); // Scheduler method
    List<Bill> getBillsByCustomerId(int customerId);
    
    //new
	List<Bill> getAllBills();
	Optional<Bill> getBillById(int billId);
}
