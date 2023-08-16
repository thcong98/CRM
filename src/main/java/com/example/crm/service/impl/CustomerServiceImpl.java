package com.example.crm.service.impl;

import com.example.crm.entity.Customer;
import com.example.crm.repository.CustomerRepository;
import com.example.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findCustomerById(UUID id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer updateCustomer(Customer customer, UUID id) {
        Customer existCustomer = customerRepository.findById(id).get();

        existCustomer.setFirstname(customer.getFirstname());
        existCustomer.setLastname(customer.getLastname());
        existCustomer.setEmail(customer.getEmail());
        existCustomer.setPhoneNumber(customer.getPhoneNumber());
        existCustomer.setBirthday(customer.getBirthday());
        existCustomer.setAddress(customer.getAddress());
        existCustomer.setGender(customer.getGender());
        customerRepository.save(existCustomer);
        return existCustomer;
    }

    @Override
    public void deleteUser(UUID id) {
        customerRepository.deleteById(id);
    }
}
