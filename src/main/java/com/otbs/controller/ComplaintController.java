package com.otbs.controller;

import com.otbs.model.Complaint;
import com.otbs.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @GetMapping
    public List<Complaint> getAllComplaints() {
        return complaintService.getAllComplaints();
    }

    @PostMapping
    public Complaint addComplaint(@RequestBody Complaint complaint) {
        return complaintService.addComplaint(complaint);
    }

    @DeleteMapping("/{id}")
    public String deleteComplaint(@PathVariable int id) {
        complaintService.deleteComplaint(id);
        return "Complaint with ID " + id + " deleted.";
    }

    @PutMapping
    public Complaint updateComplaint(@RequestBody Complaint complaint) {
        return complaintService.updateComplaint(complaint);
    }
    @GetMapping("/{id}")
    public String viewcomplaint(@PathVariable int id) {
    	complaintService.viewComplaint(id);
    	return "complaint with id"+id;
    }  
    @PostMapping(value = "/resolve", consumes = "application/x-www-form-urlencoded")
    public Complaint resolveComplaint(
            @RequestParam int complaintId,
            @RequestParam int executiveId,
            @RequestParam String resolutionDetails) {
        return complaintService.solveComplaint(complaintId, executiveId, resolutionDetails);
    }
    
}
