package com.otbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.otbs.model.Plan;
import com.otbs.service.PlanService;
import com.otbs.service.PlanServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Plan")
public class PlanController {
	
	@Autowired
    private final PlanServiceImpl planService;
//	private final PlaServiceIml planService = PlanServiceIml();


    // Constructor-based dependency injection
	// if i uncomment this then object is not created 
    public PlanController(PlanServiceImpl planService) {
        this.planService = planService;
    }
    
    
//    only use by the admin
// // Create a new plan
    @PostMapping("/addData")
    public ResponseEntity<Plan> createPlan(@RequestBody Plan plan) {
        Plan createdPlan = planService.createPlan(plan);
        return new ResponseEntity<Plan>(createdPlan, HttpStatus.OK);
    }
    
    // Get all plans
//    @GetMapping("/getAll")
//    public ResponseEntity<List<Plan>> getAllPlans() {
//        List<Plan> plans = planService.getAllPlans();
////        return ResponseEntity.ok(plans);
//        return new ResponseEntity<>(plans, HttpStatus.OK)
//    }
    
//    
    @GetMapping("/getAll")
    public ResponseEntity<List<Plan>> getAllPlans() {
        List<Plan> plans = planService.getAllPlans();
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }

    // Get a plan by ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Plan>> getPlanById(@PathVariable int id) {
        Optional<Plan> plan = planService.getPlanById(id);
        return ResponseEntity.ok(plan);
    }

   }

