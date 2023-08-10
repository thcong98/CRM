package com.example.crm.service;

import com.example.crm.entity.Attachment;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {
    Attachment uploadFile(MultipartFile file);
    Resource downloadFile(String id) throws Exception;
    Resource convertToPdf(String id) throws Exception;
}
