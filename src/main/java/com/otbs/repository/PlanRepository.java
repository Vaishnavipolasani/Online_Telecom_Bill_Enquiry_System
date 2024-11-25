package com.otbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.otbs.model.Plan;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer>{

	List<Plan> findAll();
	
}
