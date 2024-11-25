package com.otbs.service;

import java.util.List;
import java.util.Optional;

import com.otbs.model.Plan;

public interface PlanService {
	
	public List<Plan> getAllPlans();
	
	public Optional<Plan> getPlanById(int id);
	
////	this method is only for the admin
//	public Plan createPlan(Plan plan);

}
