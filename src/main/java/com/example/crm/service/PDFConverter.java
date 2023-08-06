package com.example.crm.service;

import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.document.DocumentFormat;
import org.jodconverter.core.office.OfficeException;
import org.jodconverter.core.office.OfficeManager;
import org.jodconverter.local.LocalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Service
public class PDFConverter {
//    @Autowired
    private OfficeManager officeManager;

//    public PDFConverter(OfficeManager officeManager) {
//        this.officeManager = officeManager;
//    }

    public ByteArrayOutputStream doConvert(final DocumentFormat targetFormat, final InputStream inputFile) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        final DocumentConverter converter = LocalConverter.builder()
                .officeManager(officeManager)
                .build();

        try {
            converter.convert(inputFile)
                    .to(outputStream)
                    .as(targetFormat)
                    .execute();
        } catch (OfficeException e) {
            e.printStackTrace();
        }
        return outputStream;
    }
}
