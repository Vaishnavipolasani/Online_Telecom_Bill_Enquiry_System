package com.otbs.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otbs.model.Plan;
import com.otbs.repository.PlanRepository;

@Service
public class PlanServiceImpl implements PlanService {

    private static final Logger logger = LoggerFactory.getLogger(PlanServiceImpl.class);
    private final PlanRepository planRepository;

    @Autowired
    public PlanServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

        
    @Override
    public Plan createPlan(Plan plan) {
        if (planRepository.findByPlanName(plan.getPlanName()).isPresent()) {
            throw new RuntimeException("Plan with name " + plan.getPlanName() + " already exists.");
        }
        return planRepository.save(plan);
    }
    

    @Override
    public List<Plan> getAllPlans() {
        logger.info("Fetching all plans");
        return planRepository.findAll();
    }

    @Override
    public Optional<Plan> getPlanById(int id) {
        logger.info("Fetching plan by ID: {}", id);
    	
        return planRepository.findById(id);
    }
    
    
    @Override
    public Optional<Plan> getPlanByName(String planName) {
        return planRepository.findByPlanName(planName);
    }
    
    
    @Override
    public List<Plan> getPlansByFixedRate(double fixedRate) {
        logger.info("Fetching plans with fixed rate: {}", fixedRate);
        List<Plan> plans = planRepository.findByFixedRate(fixedRate);
        if (plans.isEmpty()) {
            logger.warn("No plans found with fixed rate: {}", fixedRate);
        }
        return plans;
    }
    
    
    @Override
    public List<Plan> getPlansByPlanGroup(String planGroup) {
        logger.info("Fetching plans in group: {}", planGroup);
        return planRepository.findByPlanGroup(planGroup);
    }
    
    
    @Override
    public List<Plan> getPlansByDataLimit(String dataLimit) {
        logger.info("Fetching plans with data limit: {}", dataLimit);
        return planRepository.findByDataLimit(dataLimit);
    }
    
    
//    update plan
    @Override
    public Plan updatePlan(int id, Plan updatedPlan) {
        return planRepository.findById(id).map(existingPlan -> {
            existingPlan.setPlanName(updatedPlan.getPlanName());
            existingPlan.setFixedRate(updatedPlan.getFixedRate());
            existingPlan.setDataLimit(updatedPlan.getDataLimit());
            existingPlan.setCallLimit(updatedPlan.getCallLimit());
            existingPlan.setSmsLimit(updatedPlan.getSmsLimit());
            existingPlan.setPlanGroup(updatedPlan.getPlanGroup());
            return planRepository.save(existingPlan);
        }).orElseThrow(() -> new IllegalArgumentException("Plan with ID " + id + " not found."));
    }
    
   
//    delete plan
    @Override
    public void deletePlanById(int id) {
        if (planRepository.existsById(id)) {
            planRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Plan with ID " + id + " does not exist.");
        }
    }
    
}

