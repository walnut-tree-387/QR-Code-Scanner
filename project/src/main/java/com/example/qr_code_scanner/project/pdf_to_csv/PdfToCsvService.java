package com.example.qr_code_scanner.project.pdf_to_csv;

import org.springframework.web.multipart.MultipartFile;

public interface PdfToCsvService {
    void scanPdf(MultipartFile pdf);
}
