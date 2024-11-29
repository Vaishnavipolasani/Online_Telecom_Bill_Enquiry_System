package com.otbs.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.otbs.model.Bill;
import com.otbs.service.*;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateBills() {
        billService.generateBillsForPostpaidConnections();
        return ResponseEntity.ok("Bills generated successfully.");
    }

    @PostMapping("/apply-late-fee/{billId}")
    public ResponseEntity<String> applyLateFee(@PathVariable int billId) {
        Optional<Bill> optionalBill = billService.getBillById(billId);
        if (optionalBill.isPresent()) {
            billService.applyLatePaymentFee(optionalBill.get());
            return ResponseEntity.ok("Late payment fee applied successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bill not found.");
        }
    }
}


