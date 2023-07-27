package com.example.crm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.crm.entity.Customer;
import com.example.crm.helper.ExcelHelper;
import com.example.crm.service.CustomerService;
import com.example.crm.service.ExcelService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    ExcelService excelService;
    private CustomerService customerService;

    @PostMapping("/excel/upload")
    public ResponseEntity<?> uploadExcel(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (ExcelHelper.checkExcelFormat(file)) {
            try {
                excelService.save(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (Exception e) {
                message = e.getMessage() + "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
        }

        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @GetMapping("/excel/download")
    public ResponseEntity<Resource> getFile() {
        String filename = "customer_out.xlsx";
        InputStreamResource file = new InputStreamResource(excelService.load());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        return new ResponseEntity<Customer>(customerService.getCustomerbyId(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Page<Customer>> getCustomers(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<String> sortBy) {
        return new ResponseEntity<>(customerService.getCustomers(page, pageSize, sortBy), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable Long id) {
        return new ResponseEntity<Customer>(customerService.updateCustomer(customer, id), HttpStatus.OK);
    }

    @PutMapping("/addphone/{id}/{phone}")
    public ResponseEntity<Customer> addPhone( @PathVariable String phone, @PathVariable Long id) {
        return new ResponseEntity<Customer>(customerService.addPhone(phone, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable Long id) {
        customerService.getCustomerbyId(id);
        customerService.deteleCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
