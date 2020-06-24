package com.digitalacademy.customer.repository;

import com.digitalacademy.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    List<Customer> findByFirstName(String firstName);
    Customer findAllById(Long id);
}
