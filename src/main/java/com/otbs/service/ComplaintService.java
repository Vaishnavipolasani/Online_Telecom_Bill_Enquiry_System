package com.otbs.service;

import com.otbs.model.Complaint;
import com.otbs.model.Connection;
import com.otbs.model.HelpDeskExecutive;
import com.otbs.repository.ComplaintRepository;
import com.otbs.repository.ConnectionRepository;
import com.otbs.repository.HelpDeskExecutiveRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;
    
    @Autowired
    private ConnectionRepository customerRepository;

    @Autowired
    private HelpDeskExecutiveRepository helpDeskExecutiveRepository;

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Complaint addComplaint(Complaint complaint) {
    	int custId = complaint.getConnection().getConnectionId();
        int helpDeskId = complaint.getAssignedTo().getExecutiveId();

        System.out.println("Customer ID: " + custId + " | HelpDeskExecutive ID: " + helpDeskId);

        //Fetch Customer by ID
        Connection cobj=customerRepository.findById(custId).get();
		HelpDeskExecutive hobj=helpDeskExecutiveRepository.findById(helpDeskId).get();

        // Assign the fetched Customer and HelpDeskExecutive to the Complaint
        complaint.setConnection(cobj);
        complaint.setAssignedTo(hobj);

        // Save the Complaint to the database
        return complaintRepository.save(complaint);
    }
    

    public void deleteComplaint(int complaintID) {
        complaintRepository.deleteById(complaintID);
    }

    public Complaint updateComplaint(Complaint complaint) {
        return complaintRepository.save(complaint);
    }

    // View a specific complaint by ID
    public Complaint viewComplaint(int id) {
        Optional<Complaint> complaint = complaintRepository.findById(id);
        if (complaint.isEmpty()) {
            throw new RuntimeException("Complaint with ID " + id + " not found");
        }
        return complaint.get();
    }

    public Complaint solveComplaint(int complaintId, int executiveId, String resolutionDetails) {
        Optional<Complaint> complaintOpt = complaintRepository.findById(complaintId);
        Optional<HelpDeskExecutive> executiveOpt = helpDeskExecutiveRepository.findById(executiveId);

        if (complaintOpt.isPresent() && executiveOpt.isPresent()) {
            Complaint complaint = complaintOpt.get();
            HelpDeskExecutive executive = executiveOpt.get();

            if (complaint.getAssignedTo().getExecutiveId() != executiveId) {
                throw new IllegalStateException("This complaint is not assigned to the specified executive!");
            }
            complaint.setStatus("Resolved");
            complaint.setResolvedDate(new Date());
            complaint.setDescription(resolutionDetails);

            return complaintRepository.save(complaint);
        }
        throw new RuntimeException("Complaint or Executive not found!");
}
}

