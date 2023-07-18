package com.example.crm.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.crm.entity.Customer;
import com.example.crm.repository.CustomerRepository;
import com.example.crm.service.CustomerService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer){
        return customerRepository.save(customer);
    };

    @Override
    public Customer getCustomerbyId(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        return optionalCustomer.get();
    };

    @Override
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    };

    @Override
    public Customer updateCustomer(Customer customer){
        Customer existingCustomer = customerRepository.findById(customer.getId()).get();
        existingCustomer.setAddress(customer.getAddress());
        existingCustomer.setBirthday(customer.getBirthday());;
        existingCustomer.setEmail(customer.getEmail());;
        existingCustomer.setFirstName(customer.getFirstName());;
        existingCustomer.setGender(customer.getGender());;
        existingCustomer.setLastName(customer.getLastName());;
        existingCustomer.setPhoneNumber(customer.getPhoneNumber());;
        existingCustomer.setTypeId(customer.getTypeId());;
        Customer updateCustomer = customerRepository.save(existingCustomer);
        return updateCustomer;
    };

    @Override
    public void deteleCustomer(Long id){
        customerRepository.deleteById(id);
    };

}
