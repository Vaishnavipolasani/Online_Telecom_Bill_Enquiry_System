package com.otbs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.otbs.model.*;

@Repository
public interface UsageInfoRepository extends JpaRepository<UsageInfo, Integer> {
	 List<UsageInfo> findByConnection(Connection connection);
}


