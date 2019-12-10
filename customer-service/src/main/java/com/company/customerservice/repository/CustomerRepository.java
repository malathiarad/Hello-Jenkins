package com.company.customerservice.repository;

import com.company.customerservice.model.Customer;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@RefreshScope
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
