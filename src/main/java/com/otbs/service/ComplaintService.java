package com.otbs.service;

import com.otbs.model.Complaint;
import com.otbs.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Complaint addComplaint(Complaint complaint) {
        return complaintRepository.save(complaint);
    }

    public void deleteComplaint(int complaintID) {
        complaintRepository.deleteById(complaintID);
    }

    public Complaint updateComplaint(Complaint complaint) {
        return complaintRepository.save(complaint);
    }

	public void viewcomplaint(int id) {
		// TODO Auto-generated method stub
		complaintRepository.findById(id);
	}

	

	//public List<Complaint> getAllComplaints() {
		// TODO Auto-generated method stub
		//return null;
	//}
}
