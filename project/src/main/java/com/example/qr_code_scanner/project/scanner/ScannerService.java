package com.example.qr_code_scanner.project.scanner;

import org.springframework.web.multipart.MultipartFile;

public interface ScannerService {
    String scanImage(MultipartFile file);
}
