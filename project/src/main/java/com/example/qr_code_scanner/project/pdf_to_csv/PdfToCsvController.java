package com.example.qr_code_scanner.project.pdf_to_csv;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/scan-pdf")
public class PdfToCsvController {
    private final PdfToCsvService pdfToCsvService;

    public PdfToCsvController(PdfToCsvService pdfToCsvService) {
        this.pdfToCsvService = pdfToCsvService;
    }

    @PostMapping
    public ResponseEntity<?> scanImageToText(@RequestParam("file") MultipartFile file) {
        pdfToCsvService.scanPdf(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
