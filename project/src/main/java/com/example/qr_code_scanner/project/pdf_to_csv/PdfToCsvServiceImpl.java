package com.example.qr_code_scanner.project.pdf_to_csv;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

@Service
public class PdfToCsvServiceImpl implements PdfToCsvService{
    @Override
    public void scanPdf(MultipartFile pdfFile) {
        if (pdfFile == null || pdfFile.isEmpty()) {
            System.out.println("File is empty");
            return;
        }

        try (InputStream inputStream = pdfFile.getInputStream();
             PDDocument document = PDDocument.load(inputStream)) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("/usr/share/tesseract-ocr/5/tessdata");
            tesseract.setLanguage("eng");
            StringBuilder csvBuilder = new StringBuilder();
            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                BufferedImage image = pdfRenderer.renderImageWithDPI(page, 300);
                File tempImage = new File("temp_page_" + page + ".png");
                ImageIO.write(image, "png", tempImage);

                String rawText = tesseract.doOCR(tempImage);
                String[] lines = rawText.split("\\r?\\n");
                for (String line : lines) {
                    String csvRow = line.trim().replaceAll("\\s{2,}|\t", ",");
                    csvBuilder.append(csvRow).append("\n");
                }
                tempImage.delete();
            }
            File outputFile = new File("scanned_table.csv");
            try (FileWriter writer = new FileWriter(outputFile)) {
                writer.write(csvBuilder.toString());
                System.out.println("CSV saved to: " + outputFile.getAbsolutePath());
            }
        } catch (IOException | TesseractException e) {
            e.printStackTrace();
        }
    }
}
