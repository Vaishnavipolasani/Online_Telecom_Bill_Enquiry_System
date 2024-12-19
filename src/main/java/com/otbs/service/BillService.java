package com.otbs.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.*;

import com.otbs.model.*;
import com.otbs.repository.*;

import jakarta.transaction.Transactional;

@Service
public class BillService {

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private UsageInfoRepository usageRepository;

    @Autowired
    private BillRepository billRepository;

    @Transactional
//    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(cron = "0 0 0 1 * ?")
    public void generateBillsForPostpaidConnections() {
        List<Connection> postpaidConnections = connectionRepository.findByConnectionTypeAndStatus("postpaid", "active");

        for (Connection connection : postpaidConnections) {
        	LocalDate today = LocalDate.now();
            if (today.isAfter(connection.getActivateDate().plusMonths(1))) {

            Plan plan = connection.getPlan();
            List<UsageInfo> usageRecords = usageRepository.findByConnection(connection);
            
            
            float additionalCharges=0;
            for (UsageInfo usage : usageRecords) {
                if (usage.getDate().isEqual(connection.getActivateDate()) || usage.getDate().isBefore(today)) {
                     additionalCharges = calculateAdditionalCharges(usage, plan);
            }
                    // Create Bill
                    Bill bill = new Bill();
                    bill.setConnection(connection);
                    bill.setDate(today);
                    bill.setAdditionalCharges((int) additionalCharges);
                    bill.setTax((float) ((additionalCharges + plan.getFixedRate()) * 0.18f)); // 18% GST
                    bill.setDiscount(0); // Apply discounts as needed
                    bill.setTotalAmount((float) (plan.getFixedRate() + additionalCharges + bill.getTax()));
                    bill.setStatus("Pending");
                    bill.setDueDate(today.plusDays(10));
                    bill.setUsage(usage);
                    billRepository.save(bill);

                    // Update connection activation date for the next billing cycle
                    connection.setActivateDate(today.plusMonths(1));
                }
            }
        }
    }
    

    private float calculateAdditionalCharges(UsageInfo usage, Plan plan) {
        float additionalDataCharge = (float) (Math.max(0, usage.getDataUsed() - plan.getDataLimit()) * plan.getAdditionalChargeRatePerMB());
        float additionalCallCharge = (float) (Math.max(0, usage.getCalls() - plan.getCallLimit()) * plan.getAdditionalChargeRatePerCall());
        float additionalSmsCharge = (float) (Math.max(0, usage.getSms() - plan.getSmsLimit()) * plan.getAdditionalChargeRatePerSMS());
        return (float) (additionalDataCharge + additionalCallCharge + additionalSmsCharge);
    }

    public void applyLatePaymentFee(Bill bill) {
        if (bill.getStatus().equalsIgnoreCase("Pending") && LocalDate.now().isAfter(bill.getDueDate())) {
            bill.setLatePaymentFee(50); // Flat late fee
            billRepository.save(bill);
        }
    }

    public Optional<Bill> getBillById(int billId) {
        return billRepository.findById(billId);
    }
    public List<Bill> getAllBills() {
        return billRepository.findAll();}

	
}

