package com.example.qr_code_scanner.project.scanner;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/scan")
public class ScannerController {
    private final ScannerService scannerService;

    public ScannerController(ScannerService scannerService) {
        this.scannerService = scannerService;
    }

    @PostMapping
    public ResponseEntity<?> scanImageToText(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(scannerService.scanImage(file), HttpStatus.OK);
    }
}
