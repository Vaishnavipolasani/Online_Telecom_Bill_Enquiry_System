package com.otbs.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.otbs.model.Connection;
import com.otbs.model.*;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Integer>{


	
}

