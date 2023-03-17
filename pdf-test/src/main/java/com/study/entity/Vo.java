package com.study.entity;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vo {

    private String title;

    private Integer pageNum;

    private Integer level;

    private String content;

    public static void main(String[] args) {
        PdfDocument pdfDocument = new PdfDocument();
        pdfDocument.loadFromFile("D:\\lihang\\test4.pdf");
        pdfDocument.saveToFile("D:\\lihang\\test4-1.docx", FileFormat.DOCX);
        pdfDocument.close();
    }
}
