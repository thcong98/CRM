package com.example.crm.controller;

import com.example.crm.entity.Attachment;
import com.example.crm.service.AttachmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@Tag(name = "Template", description = "Template Management APIs")
@RestController
@RequestMapping("/template")
@SecurityRequirement(name = "Authorization")
public class AttachmentController {
    @Autowired
    AttachmentService attachmentService;

    @Operation(summary = "Upload a template")
    @PostMapping(value = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadFile(@RequestPart(required = true) MultipartFile file)
            throws JsonMappingException, JsonProcessingException {
        Attachment attachment = attachmentService.uploadFile(file);
        return ResponseEntity.ok().body(attachment);
    }

    @Operation(summary = "Download a template")
    @GetMapping("/dowload")
    public ResponseEntity<?> dowloadfile(String id) throws Exception {
        Resource file = attachmentService.downloadFile(id);
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + file.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(file);
    }

//    @GetMapping("/convert")
//    public ResponseEntity<?> convert(String id) throws Exception {
//        Resource file = attachmentService.convertToPdf(id);
//        String contentType = "application/octet-stream";
//        String headerValue = "attachment; filename=\"" + file.getFilename() + "\"";
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(contentType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
//                .body(file);
//    }

}
