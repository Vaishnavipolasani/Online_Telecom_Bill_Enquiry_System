package com.otbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.otbs.model.Bill;
import com.otbs.model.Connection;
import com.otbs.model.UsageInfo;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    List<Bill> findByConnection_CustomerObj_CustomerId(int customerId);
    Bill findByConnectionAndUsage(Connection connection, UsageInfo usage);
}
