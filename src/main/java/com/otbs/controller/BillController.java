package com.otbs.controller;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.otbs.model.Bill;
import com.otbs.model.BillDTO;
import com.otbs.service.*;

@RestController
@RequestMapping("/api/bills")
@CrossOrigin(origins = "http://localhost:8080")
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
    
    @GetMapping("/")
    public ResponseEntity<List<BillDTO>> getAllBills() {
        List<Bill> bills = billService.getAllBills();
        if (bills.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content if the list is empty
        }
        List<BillDTO> billDTOs = bills.stream()
                .map(bill -> new BillDTO(
                    bill.getBillId(),
                    bill.getConnection().getConnectionId(),
                    bill.getDate(),
                    bill.getTotalAmount(),
                    bill.getStatus()
                ))
                .toList();

            return ResponseEntity.ok(billDTOs);
        
//        return ResponseEntity.ok(bills);
    }
    
    @GetMapping("/{billId}/download")
    public ResponseEntity<byte[]> downloadBill(@PathVariable int billId) {
        try {
            BillDTO bill = new BillDTO(billId, 12345, LocalDate.now(), 150.75, "Paid"); // Replace with real database fetch
            byte[] pdfBytes = billPDFService.generateBillPDF(bill);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bill_" + billId + ".pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
   
}


