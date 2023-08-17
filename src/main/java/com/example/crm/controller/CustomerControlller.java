package com.example.crm.controller;

import com.example.crm.entity.Customer;
import com.example.crm.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Tag(name = "Customer", description = "Customers Management APIs")
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/customer")
public class CustomerControlller {
    @Autowired
    CustomerService customerService;

    @Operation(summary = "Add a new customer", description = "Save information of a new customer: first name, last name, email and phone number are compulsory, " +
            "birthday, address and gender.\n" +
            " user can do this.")
    @PostMapping("/add")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.addCustomer(customer), HttpStatus.CREATED);
    }

    @Operation(summary = "Find all customers", description = "List all customers with their whole information." +
            "user can do this.")
    @GetMapping("/all")
    public ResponseEntity<List<Customer>> findAllCustomers () {
        return new ResponseEntity<>(customerService.findAllCustomers(), HttpStatus.OK);
    }

    @Operation(summary = "Find a customer by ID", description = "Find a customer by specifying ID. " +
            " user can do this. ")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Customer>> findCustomerById (@PathVariable("id")UUID id) {
        return new ResponseEntity<>(customerService.findCustomerById(id),HttpStatus.OK);
    }

    @Operation(summary = "Update information for a customer", description = "Update: first name, last name, email, phone, birthday, address and gender.\n" +
            "The response is customer's information after being updated.\n" +
            "user can do this.")
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") UUID id, @RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.updateCustomer(customer, id), HttpStatus.OK);
    }

    @Operation(summary = "Delete a customer by ID", description = "Delete whole information of a customer by their specifying ID." +
            " user can do this.")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCustomerById(@PathVariable("id") UUID id) {
        customerService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
