package com.example.crm.service;

import com.example.crm.entity.EmailDetails;

public interface EmailService {
    String sendSimpleEmail(EmailDetails details);
}
