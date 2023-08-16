package com.example.crm.exception;

import java.util.UUID;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(UUID id) {
        super("The Customer id '" + id + "' does not exist in our records");
    }
}
