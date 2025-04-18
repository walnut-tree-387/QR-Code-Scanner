package com.example.qr_code_scanner.project.scanner;

import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ScannerServiceImpl implements ScannerService {
    @Override
    public String scanImage(MultipartFile multipartFile) {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            BufferedImage bufferedImage = ImageIO.read(inputStream);

            if (bufferedImage == null) {
                return "Error: Could not read image content.";
            }

            // Convert to grayscale for better decoding
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();

        } catch (NotFoundException e) {
            return "Error: QR Code not found in image.";
        } catch (IOException e) {
            return "Error reading image: " + e.getMessage();
        } catch (Exception e) {
            return "Unexpected error: " + e.getMessage();
        }
    }
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = File.createTempFile("upload_", "_" + file.getOriginalFilename());
        file.transferTo(convFile);
        return convFile;
    }

}
