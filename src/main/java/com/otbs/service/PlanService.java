package com.otbs.service;


import java.util.List;
import java.util.Optional;
import com.otbs.model.Plan;

public interface PlanService {

    List<Plan> getAllPlans();

    Optional<Plan> getPlanById(int id);

    Plan createPlan(Plan plan);
    
    Optional<Plan> getPlanByName(String planName);
    
    List<Plan> getPlansByFixedRate(double fixedRate);
    
    List<Plan> getPlansByPlanGroup(String planGroup);
    
    List<Plan> getPlansByDataLimit(String dataLimit);
    
//    for the admin part
    
    Plan updatePlan(int id, Plan updatedPlan);
    
    void deletePlanById(int id);

}