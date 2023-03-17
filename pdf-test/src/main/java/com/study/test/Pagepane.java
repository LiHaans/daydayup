package com.study.test;

import java.awt.*;
import java.io.*;
import java.util.Iterator;
import java.util.List;

import com.study.utils.PdfboxUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.action.PDPageAdditionalActions;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageXYZDestination;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.action.PDAction;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDNamedDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class Pagepane {
    public static void main(String... args) throws IOException {
        File file = new File("D:\\lihang\\test3.pdf");
        PDDocument document = PDDocument.load(file);
        int pageNum = 0;
        String Rect = "";
        //FileWriter myWriter = new FileWriter("D:\\lihang\\test2.txt");
        //BufferedWriter bw = new BufferedWriter(myWriter);
        //PrintWriter out = new PrintWriter(bw);

        Iterator<PDPage> iterator = document.getPages().iterator();


        for( PDPage page : document.getPages() )
        {
            pageNum++;
            List<PDAnnotation> annotations = page.getAnnotations();
            for (int j = 0; j < annotations.size() ; j++)
            {
                PDAnnotation annot = annotations.get(j);
                if( annot instanceof PDAnnotationLink )
                {
                    PDAnnotationLink link = (PDAnnotationLink)annot;

                    PDDestination destination = link.getDestination();

                    Integer pageNum1 = 0;
                    if(destination instanceof PDPageXYZDestination){
                        PDPageXYZDestination pd = (PDPageXYZDestination) destination;
                        pageNum1 = pd.retrievePageNumber() + 1;
                    }


                    PDRectangle rect = link.getRectangle();
                    int x = new Float(Math.floor(rect.getLowerLeftX())).intValue();
                    int y = new Float(Math.floor(rect.getLowerLeftY())).intValue();
                    int width = new Float(Math.ceil(rect.getWidth())).intValue();
                    int height = new Float(Math.ceil(rect.getHeight())).intValue();

                    System.out.println(pageNum1 + ", " + x + ", " + y + ", " + width + ", " + height);
                    //myWriter.write(pageNum + ", " + x1 + ", " + x2 + ", " + y1 + ", " + y2 + "\r\n");

                    Rectangle textRrect = new Rectangle(0, y, width, height);
                    String s = PdfboxUtils.readRectangelText(document, pageNum-1, textRrect);
                    System.out.println(s);
                }
            }
        }
        //myWriter.close();
        document.close();
    }

}
