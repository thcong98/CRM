package com.example.crm.service.impl;

import com.example.crm.entity.Attachment;
import com.example.crm.exception.CustomerNotFoundException;
import com.example.crm.file.FilesStorageService;
import com.example.crm.repository.AttachmentRepository;
import com.example.crm.service.AttachmentService;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AttachmentImpl implements AttachmentService {
    @Autowired
    private FilesStorageService filesStorageService;
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Override
    public Attachment uploadFile(MultipartFile file) {
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS_");
        String currentDateTime = dateFormatter.format(new Date());
        String path = "uploads/" + currentDateTime + file.getOriginalFilename();
        filesStorageService.saveAs(file, path );
        Attachment attachment = new Attachment();
        attachment.setName(file.getOriginalFilename());
        attachment.setContent_type(file.getContentType());
        attachment.setPhysical_path(path);
        attachmentRepository.save(attachment);
        return attachment;
    }

    @Override
    public Resource downloadFile(String id) throws Exception {
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(
                ()-> new Exception("not found")
        );
        Resource resource = filesStorageService.load(attachment.getPhysical_path());
        return resource;
    }

//    @Override
//    public Resource convertToPdf(String id) throws Exception {
//        Attachment attachment = attachmentRepository.findById(id).orElseThrow(
//                ()-> new Exception("not found")
//        );
//        Resource resource = filesStorageService.load(attachment.getPhysical_path());
//        return resource;
//    }
}
