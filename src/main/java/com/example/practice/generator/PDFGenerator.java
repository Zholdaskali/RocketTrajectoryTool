package com.example.practice.generator;

import com.example.practice.format.FormatDocs;
import com.example.practice.format.FormatDouble;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.IOException;
import java.io.InputStream;

public class PDFGenerator {

    private static final int MAX_CHARS_PER_LINE = 43;

    public void generatePDF(String content, String maxHText, double maxH, String distanceText, double maxDistance, String angleText, double angle, String filePath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            InputStream fontStream = getClass().getResourceAsStream("/com/example/practice/font/DejaVuSans.ttf");
            PDType0Font font = PDType0Font.load(document, fontStream);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(font, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700);

                String fullText = content + maxHText +  FormatDouble.format(maxH) + "      "  + distanceText + FormatDouble.format(maxDistance) + "     " +  angleText + FormatDouble.format(angle);
                String[] lines = splitTextIntoLines(fullText, MAX_CHARS_PER_LINE);

                for (String line : lines) {
                    contentStream.showText(line);
                    contentStream.newLineAtOffset(0, -15); // Смещение для новой строки
                }

                contentStream.endText();
            }

            document.save(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] splitTextIntoLines(String text, int maxCharsPerLine) {
        int textLength = text.length();
        int linesCount = (int) Math.ceil((double) textLength / maxCharsPerLine);
        String[] lines = new String[linesCount];

        for (int i = 0; i < linesCount; i++) {
            int startIdx = i * maxCharsPerLine;
            int endIdx = Math.min(startIdx + maxCharsPerLine, textLength);
            lines[i] = text.substring(startIdx, endIdx);
        }

        return lines;
    }
}
