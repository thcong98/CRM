package com.example.crm.service;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.crm.entity.Customer;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer getCustomerbyId(Long id);
    List<Customer> getAllCustomers();
    Page<Customer> getCustomers(
        Optional<Integer> page,
        Optional<Integer> pageSize,
        Optional<String> sortBy);
    Customer updateCustomer(Customer customer, Long id);
    void deteleCustomer(Long id);
}
