package com.study.test;

import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageXYZDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;

import java.io.File;
import java.io.IOException;

public class PDFOutlineParser {
    public static void main(String[] args) {
        try {
            // Load the PDF document.
            File file = new File("D:\\lihang\\test-2.pdf");
            PDDocument document = PDDocument.load(file);

            // Get the document outline.
            PDDocumentOutline outline = document.getDocumentCatalog().getDocumentOutline();

            // Iterate through each outline item.
            if (outline != null) {
                for (PDOutlineItem item : outline.children()) {
                    // Get the destination of the outline item.
                    if (item.getDestination() != null) {
                        System.out.println("Outline item: " + item.getTitle() + ", destination: " + item.getDestination().toString());
                    }

                    // Recursively iterate through the children of the outline item.
                    if (item.hasChildren()) {
                        iterateOutlineItems(item);
                    }
                }
            }

            // Close the PDF document.
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Recursively iterate through the children of the outline item.
    private static void iterateOutlineItems(PDOutlineItem item) throws IOException {
        for (PDOutlineItem child : item.children()) {
            if (child.getDestination() != null) {
                System.out.println("Outline item: " + child.getTitle() + ", destination: " + child.getDestination().toString());

                PDDestination destination = child.getDestination();


                // Recursively iterate through the children of the outline item.
                if (item.hasChildren()) {
                    iterateOutlineItems(item);
                }
            }

            if (child.hasChildren()) {
                iterateOutlineItems(child);
            }
        }
    }
}

