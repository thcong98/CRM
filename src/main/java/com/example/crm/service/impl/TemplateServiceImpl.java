package com.example.crm.service.impl;

import com.example.crm.entity.Template;
import com.example.crm.repository.TemplateRepository;
import com.example.crm.service.TemplateService;
import org.jodconverter.core.office.OfficeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class TemplateServiceImpl implements TemplateService {
    @Autowired
    TemplateRepository templateRepository;
    @Override
    public Template create(Template template, MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Template saveTemplate = new Template(fileName, file.getContentType(), template.getSubject(), template.getContent());
        templateRepository.save(saveTemplate);
        return saveTemplate;
    }

    @Override
    public List<Template> getAll() {
        return templateRepository.findAll();
    }

    @Override
    public Template getById(String id) {
        return templateRepository.findById(id).get();
    }

    @Override
    public Page<Template> paginationTemplate(Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy) {
        return templateRepository.findAll(
                PageRequest.of(page.orElse(0), size.orElse(3), Sort.Direction.ASC, sortBy.orElse("id"))
        );
    }

    @Override
    public void deleteById(String id) {
        templateRepository.deleteById(id);
    }

}
