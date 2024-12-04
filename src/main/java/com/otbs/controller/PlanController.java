package com.otbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.otbs.model.Plan;
import com.otbs.service.PlanService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/plans") // Standard REST naming convention
public class PlanController {

    private final PlanService planService;

    @Autowired
    public PlanController(PlanService planService) {
        this.planService = planService;
    }


    // Get all plans
    @GetMapping("/getAll")
    public ResponseEntity<List<Plan>> getAllPlans() {
        List<Plan> plans = planService.getAllPlans();
        if (plans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }

//  Get a plan by ID
    @GetMapping("/getId/{id}")
    public ResponseEntity<Plan> getPlanById(@PathVariable int id) {
        Optional<Plan> plan = planService.getPlanById(id);
        return plan.map(ResponseEntity::ok)
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
//  Get a plan by Name
    @GetMapping("/name/{planName}")
    public ResponseEntity<Plan> getPlanByName(@PathVariable String planName) {
        Optional<Plan> plan = planService.getPlanByName(planName);
        return plan.map(ResponseEntity::ok)
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
//  Get a plan by fixedRate
    @GetMapping("/fixedRate/{fixedRate}")
    public ResponseEntity<List<Plan>> getPlansByFixedRate(@PathVariable double fixedRate) {
        List<Plan> plans = planService.getPlansByFixedRate(fixedRate);
        if (plans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }
 
//  Get all the plan based on the group
    @GetMapping("/planGroup/{planGroup}")
    public ResponseEntity<List<Plan>> getPlansByPlanGroup(@PathVariable String planGroup) {
        List<Plan> plans = planService.getPlansByPlanGroup(planGroup);
        if (plans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }
    
    
//  Get all the plan based on the dataLimit   
    @GetMapping("/dataLimit/{dataLimit}")
    public ResponseEntity<List<Plan>> getPlansByDataLimit(@PathVariable String dataLimit) {
        List<Plan> plans = planService.getPlansByDataLimit(dataLimit);
        if (plans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }
    
    
    
 // Create a new plan (Only for the Admin...)    
    @PostMapping("/add")
    public ResponseEntity<Plan> createPlan(@RequestBody Plan plan) {
        Plan createdPlan = planService.createPlan(plan);
        return new ResponseEntity<>(createdPlan, HttpStatus.CREATED);
    }
    
    
  
//  update the plan based on the plan id (Only for the Admin...)
    @PutMapping("/updatById/{id}")
    public ResponseEntity<Plan> updatePlan(@PathVariable int id, @RequestBody Plan updatedPlan) {
        try {
            Plan plan = planService.updatePlan(id, updatedPlan);
            return new ResponseEntity<>(plan, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    
//   delete the plan based on the plan id (Only for the Admin...)
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deletePlan(@PathVariable int id) {
        try {
            planService.deletePlanById(id);
            return new ResponseEntity<>("Plan deleted successfully!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
}

