package com.example.crm.service;

import com.example.crm.entity.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    Customer addCustomer(Customer customer);
    List<Customer> findAllCustomers();
    Optional<Customer> findCustomerById(UUID id);
    Customer updateCustomer(Customer customer, UUID id);
    void deleteUser(UUID id);
}
