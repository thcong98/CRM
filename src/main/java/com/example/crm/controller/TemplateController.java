package com.example.crm.controller;

import com.example.crm.entity.Template;
import com.example.crm.exception.FileResponse;
import com.example.crm.service.PDFConverter;
import com.example.crm.service.TemplateService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.apache.commons.io.FilenameUtils;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.document.DocumentFormat;
import org.jodconverter.core.office.OfficeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/template")
public class TemplateController {
    @Autowired
    TemplateService templateService;
    @Autowired
    PDFConverter pdfConverter;

    private final String PDF_FORMAT = "pdf";

    @PostMapping("/upload")
    public ResponseEntity<FileResponse> uploadTemplate(@RequestBody Template template, @RequestParam("file")MultipartFile file) {
        String message = "";
        try {
            templateService.create(template, file);
            message = "Upload the template successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.CREATED).body(new FileResponse(message));
        } catch (Exception e) {
            message = "Could not upload the template.";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileResponse(message));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Template>> listAllTemplates() {
        List<Template> templates = templateService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(templates);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Template>> getTemplateById (@PathVariable("id") String id) {
        Template template = templateService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(Optional.ofNullable(template));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadTemplate (@PathVariable("id") String id) {
        Template template = templateService.getById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + template.getName() + "\"")
                .body(template.getName());
    }

    @GetMapping("/paging")
    public ResponseEntity<Page<Template>> paginationTemplate(
            @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam Optional<String> sortBy) {
        return new  ResponseEntity<>(templateService.paginationTemplate(page, size, sortBy), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTemplateById (@PathVariable("id") String id) {
        templateService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/pdf")
    public ResponseEntity<?> convert(@RequestParam("file") final MultipartFile inputFile) throws IOException, OfficeException{
        final DocumentFormat conversionTargetFormat = DefaultDocumentFormatRegistry.getFormatByExtension(PDF_FORMAT);

        ByteArrayOutputStream convertedFile = pdfConverter.doConvert(conversionTargetFormat, inputFile.getInputStream());

        final HttpHeaders httpHeaders = new HttpHeaders();
        String targetFilename = String.format("%s.%s",
                FilenameUtils.getBaseName(inputFile.getOriginalFilename()),
                conversionTargetFormat.getExtension());

        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + targetFilename);
        httpHeaders.setContentType(MediaType.parseMediaType(conversionTargetFormat.getMediaType()));
        return ResponseEntity.ok().header(String.valueOf(httpHeaders)).body(convertedFile.toByteArray());
    }
}
