package com.example.crm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.crm.entity.Customer;
import com.example.crm.exception.CustomerNotFoundException;
import com.example.crm.repository.CustomerRepository;
import com.example.crm.service.CustomerService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    };

    @Override
    public Customer getCustomerbyId(Long id) {
        Optional<Customer> mayOptionalCustomer = customerRepository.findById(id);
        return unwrapCustomer(mayOptionalCustomer, id);
    };

    @Override
    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepository.findAll();
    };

    @Override
    public Page<Customer> getCustomers(
            Optional<Integer> page,
            Optional<Integer> pageSize,
            Optional<String> sortBy) {
        return customerRepository.findAll(
                PageRequest.of(page.orElse(0), pageSize.orElse(5), Sort.Direction.ASC, sortBy.orElse("id")));
    };

    @Override
    public Customer updateCustomer(Customer customer, Long id) {
        Optional<Customer> mayExitCustomer = customerRepository.findById(id);
        Customer existingCustomer = unwrapCustomer(mayExitCustomer, id);
        existingCustomer.setCode(customer.getCode());
        existingCustomer.setAddress(customer.getAddress());
        existingCustomer.setBirthday(customer.getBirthday());
        ;
        existingCustomer.setEmail(customer.getEmail());
        ;
        existingCustomer.setFirstName(customer.getFirstName());
        ;
        existingCustomer.setGender(customer.getGender());
        ;
        existingCustomer.setLastName(customer.getLastName());
        ;
        existingCustomer.setPhoneNumber(customer.getPhoneNumber());
        ;
        existingCustomer.setTypeId(customer.getTypeId());
        ;
        return customerRepository.save(existingCustomer);
    };

    @Override
    public void deteleCustomer(Long id) {
        customerRepository.deleteById(id);
    };

    static Customer unwrapCustomer(Optional<Customer> entity, Long id) {
        if (entity.isPresent())
            return entity.get();
        else
            throw new CustomerNotFoundException(id);
    }

    @Override
    public Customer addPhone(String phone, Long id) {
        Optional<Customer> mayExitCustomer = customerRepository.findById(id);
        Customer existingCustomer = unwrapCustomer(mayExitCustomer, id);
        List<String> phoneNumbers = new ArrayList<>(existingCustomer.getPhoneNumber());
        phoneNumbers.add(phone);
        existingCustomer.setPhoneNumber(phoneNumbers);
        return customerRepository.save(existingCustomer);
    };
}

