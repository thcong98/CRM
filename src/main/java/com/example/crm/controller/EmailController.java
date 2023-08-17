package com.example.crm.controller;

import com.example.crm.entity.EmailDetails;
import com.example.crm.service.EmailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "Email", description = "Email Management APIs")
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public String sendMail(@RequestBody EmailDetails details) {
        String status = emailService.sendSimpleEmail(details);
        return status;
    }
}
