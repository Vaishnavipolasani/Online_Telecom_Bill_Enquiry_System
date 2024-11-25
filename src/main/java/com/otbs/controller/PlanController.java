package com.otbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.otbs.model.Plan;
import com.otbs.service.PlanService;
import com.otbs.service.PlanServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/plan")
public class PlanController {
	
	@Autowired
    private final PlanServiceImpl planService;

    // Constructor-based dependency injection
    public PlanController(PlanServiceImpl planService) {
        this.planService = planService;
    }
    
    
//    only use by the admin
// // Create a new plan
//    @PostMapping
//    public ResponseEntity<Plan> createPlan(@RequestBody Plan plan) {
//        Plan createdPlan = planService.createPlan(plan);
//        return ResponseEntity.ok(createdPlan);
//    }
    


    // Get all plans
    @GetMapping("/")
    public ResponseEntity<List<Plan>> getAllPlans() {
        List<Plan> plans = planService.getAllPlans();
        return ResponseEntity.ok(plans);
    }

    // Get a plan by ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Plan>> getPlanById(@PathVariable int id) {
        Optional<Plan> plan = planService.getPlanById(id);
        return ResponseEntity.ok(plan);
    }

   }

