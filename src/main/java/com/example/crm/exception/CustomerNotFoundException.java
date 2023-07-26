package com.example.crm.exception;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(Long id) {
        super("The Customer id '" + id + "' does not exist in our records");
    }
}
