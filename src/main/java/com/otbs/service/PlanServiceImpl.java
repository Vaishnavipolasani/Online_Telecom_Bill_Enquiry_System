package com.otbs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otbs.model.Plan;
import com.otbs.repository.PlanRepository;

@Service
public class PlanServiceImpl implements PlanService{
	
	@Autowired
	private PlanRepository planRepository;
	
	public Plan createPlan(Plan plan) {
	    return planRepository.save(plan);
	}

	@Override
	public List<Plan> getAllPlans() {
		 return planRepository.findAll();
	}

	@Override
	public Optional<Plan> getPlanById(int id) {
		return planRepository.findById(id);
	}
}
