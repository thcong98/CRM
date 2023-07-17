package com.example.crm.service;
import java.util.List;

import com.example.crm.entity.Customer;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer getCustomerbyId(Long id);
    List<Customer> getAllCustomers();
    Customer updateCustomer(Customer customer);
    void deteleCustomer(Long id);
}
