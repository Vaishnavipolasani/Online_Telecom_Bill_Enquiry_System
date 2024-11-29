package com.otbs.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import com.otbs.model.*;
import com.otbs.repository.*;

import jakarta.transaction.Transactional;

@Service
public class BillService {

//    @Autowired
//    private ConnectionRepository connectionRepository;
//
//    @Autowired
//    private UsageRepository usageRepository;
//
//    @Autowired
//    private BillRepository billRepository;
//
//    @Transactional
//    public void generateBillsForPostpaidConnections() {
//        List<Connection> postpaidConnections = connectionRepository.findByPlanObjPlanTypeAndStatus("postpaid", "active");
//
//        for (Connection connection : postpaidConnections) {
//            Plan plan = connection.getPlanObj();
//            List<UsageRec> usageRecords = usageRepository.findByConnectionObj(connection);
//            LocalDate today = LocalDate.now();
//
//            for (UsageRec usage : usageRecords) {
//                if (usage.getDate().isEqual(connection.getActivateDate()) || usage.getDate().isBefore(today)) {
//                    float additionalCharges = calculateAdditionalCharges(usage, plan);
//
//                    // Create Bill
//                    Bill bill = new Bill();
//                    bill.setConnectionObj(connection);
//                    bill.setBillingDate(today);
//                    bill.setAdditionalCharges((int) additionalCharges);
//                    bill.setTax((float) ((additionalCharges + plan.getFixedRate()) * 0.18f)); // 18% GST
//                    bill.setDiscount(0); // Apply discounts as needed
//                    bill.setTotalAmount((float) (plan.getFixedRate() + additionalCharges + bill.getTax()));
//                    bill.setStatus("Pending");
//                    bill.setDueDate(today.plusDays(10));
//                    billRepository.save(bill);
//
//                    // Update connection activation date for the next billing cycle
//                    connection.setActivateDate(today.plusMonths(1));
//                }
//            }
//        }
//    }
//
//    private float calculateAdditionalCharges(UsageRec usage, Plan plan) {
//        float additionalDataCharge = (float) (Math.max(0, usage.getData() - plan.getDataLimit()) * plan.getAdditionalChargeRatePerMB());
//        float additionalCallCharge = (float) (Math.max(0, usage.getCalls() - plan.getCallLint()) * plan.getAdditionalChargeRatePerCall());
//        float additionalSmsCharge = (float) (Math.max(0, usage.getSms() - plan.getSmsLimit()) * plan.getAdditionalChargeRatePerSMS());
//        return (float) (additionalDataCharge + additionalCallCharge + additionalSmsCharge);
//    }
//
//    public void applyLatePaymentFee(Bill bill) {
//        if (bill.getStatus().equalsIgnoreCase("Paid") && LocalDate.now().isAfter(bill.getDueDate())) {
//            bill.setLatePaymentFee(50.0f); // Flat late fee
//            billRepository.save(bill);
//        }
//    }
//
//    public Optional<Bill> getBillById(int billId) {
//        return billRepository.findById(billId);
//    }

	
}

