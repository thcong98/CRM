package com.example.crm.service;

import com.example.crm.entity.Template;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface TemplateService {
    Template create(Template template, MultipartFile file);
    List<Template> getAll();
    Template getById(String id);
    Page<Template> paginationTemplate (Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy);
    void deleteById(String id);
}
