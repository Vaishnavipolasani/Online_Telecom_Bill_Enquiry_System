package com.otbs.controller;

import com.otbs.exception.InvalidEntityException;
import com.otbs.model.Plan;
import com.otbs.service.PlanService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans")
public class PlanController {

    private final PlanService planService;

    @Autowired
    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    // Get all plans
    @GetMapping("/getAll")
    public ResponseEntity<List<Plan>> getAllPlans() throws InvalidEntityException {
        List<Plan> plans = planService.getAllPlans();
        if (plans.isEmpty()) {
            throw new InvalidEntityException("No plans found in the database.");
        }
        return ResponseEntity.ok(plans);
    }

    // Get a plan by ID
    @GetMapping("/getId/{id}")
    public ResponseEntity<Plan> getPlanById(@PathVariable int id) throws InvalidEntityException {
        return ResponseEntity.ok(
                planService.getPlanById(id)
                        .orElseThrow(() -> new InvalidEntityException("Plan with ID " + id + " does not exist."))
        );
    }

    // Get a plan by name
    @GetMapping("/getName/{planName}")
    public ResponseEntity<Plan> getPlanByName(@PathVariable String planName) throws InvalidEntityException {
        return ResponseEntity.ok(
                planService.getPlanByName(planName)
                        .orElseThrow(() -> new InvalidEntityException("Plan with name " + planName + " does not exist."))
        );
    }

    // Get plans by fixed rate
    @GetMapping("/getFixedRate/{fixedRate}")
    public ResponseEntity<List<Plan>> getPlansByFixedRate(@PathVariable double fixedRate) throws InvalidEntityException {
        List<Plan> plans = planService.getPlansByFixedRate(fixedRate);
        if (plans.isEmpty()) {
            throw new InvalidEntityException("No plans found with fixed rate: " + fixedRate);
        }
        return ResponseEntity.ok(plans);
    }

    // Get plans by plan group
    @GetMapping("/getPlanGroup/{planGroup}")
    public ResponseEntity<List<Plan>> getPlansByPlanGroup(@PathVariable String planGroup) throws InvalidEntityException {
        List<Plan> plans = planService.getPlansByPlanGroup(planGroup);
        if (plans.isEmpty()) {
            throw new InvalidEntityException("No plans found in group: " + planGroup);
        }
        return ResponseEntity.ok(plans);
    }

    // Get plans by data limit
    @GetMapping("/getDataLimit/{dataLimit}")
    public ResponseEntity<List<Plan>> getPlansByDataLimit(@PathVariable String dataLimit) throws InvalidEntityException {
        List<Plan> plans = planService.getPlansByDataLimit(dataLimit);
        if (plans.isEmpty()) {
            throw new InvalidEntityException("No plans found with data limit: " + dataLimit);
        }
        return ResponseEntity.ok(plans);
    }

    // Get a plan by number of days
    @GetMapping("/getNumberOfDay/{numberOfDay}")
    public ResponseEntity<Plan> getPlansByNumberOfDay(@PathVariable int numberOfDay) throws InvalidEntityException {
        return ResponseEntity.ok(
                planService.getPlansByNumberOfDay(numberOfDay)
                        .orElseThrow(() -> new InvalidEntityException("Plan with " + numberOfDay + " days does not exist."))
        );
    }

    // Create a new plan (Admin only)
    @PostMapping("/add")
    public ResponseEntity<Plan> createPlan(@Valid @RequestBody Plan plan) throws InvalidEntityException {
        return ResponseEntity.status(201).body(planService.createPlan(plan));
    }

    // Update a plan by ID (Admin only)
    @PutMapping("/updateById/{id}")
    public ResponseEntity<Plan> updatePlan(@PathVariable int id, @Valid @RequestBody Plan updatedPlan) throws InvalidEntityException {
        Plan plan = planService.updatePlan(id, updatedPlan);
        return ResponseEntity.ok(plan);
    }

    // Delete a plan by ID (Admin only)
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deletePlan(@PathVariable int id) throws InvalidEntityException {
        planService.deletePlanById(id);
        return ResponseEntity.ok("Plan with ID " + id + " deleted successfully.");
    }
}

