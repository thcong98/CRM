package com.example.crm.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.crm.entity.Customer;
import com.example.crm.helper.ExcelHelper;
import com.example.crm.repository.CustomerRepository;

@Service
public class ExcelService {
    @Autowired
    CustomerRepository customerRepository;

    public void save(MultipartFile file) {
        try {
            List<Customer> customers = ExcelHelper.excelToCustomer(file.getInputStream());
            customerRepository.saveAll(customers);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public ByteArrayInputStream load() {
        List<Customer> customer = customerRepository.findAll();

        ByteArrayInputStream in = ExcelHelper.customersToExcel(customer);
        return in;
    }
}
