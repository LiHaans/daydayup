package com.study.utils;


import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import javax.imageio.ImageIO;

public class PdfboxUtils {

    public static final String REGION_NAME = "content";


    /**
     * 根据指定文件页码的指定区域读取文字
     *
     * @param filePath PDF文件路径
     * @param iPage PDF页码
     * @param textRrect 读取文字的区域
     * @return 文字内容
     */
    public static String readRectangelText(String filePath, int iPage, Rectangle textRrect) {

        String textContent = "";

        try(PDDocument pdfDoc = PDDocument.load(new File(filePath))) {
            // 获取指定的PDF页
            PDPage pdfPage = pdfDoc.getPage(iPage);

            // 获取指定位置的文字（文字剥离器）
            PDFTextStripperByArea textStripper = new PDFTextStripperByArea();
            textStripper.setSortByPosition(true);
            textStripper.addRegion(REGION_NAME, textRrect);
            textStripper.extractRegions(pdfPage);

            textContent = textStripper.getTextForRegion(REGION_NAME);

            // 释放资源
            pdfDoc.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return textContent;

    }

    public static String readRectangelText(PDDocument pdfDoc, int iPage, Rectangle textRrect) {

        String textContent = "";

        try {
            // 获取指定的PDF页
            PDPage pdfPage = pdfDoc.getPage(iPage);

            // 获取指定位置的文字（文字剥离器）
            PDFTextStripperByArea textStripper = new PDFTextStripperByArea();
            textStripper.setSortByPosition(true);
            textStripper.addRegion(REGION_NAME, textRrect);
            textStripper.extractRegions(pdfPage);

            textContent = textStripper.getTextForRegion(REGION_NAME);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return textContent;

    }

    /**
     * 根据指定文件页码的指定区域读取图片
     *
     * @param filePath PDF文件路径
     * @param iPage PDF页码
     * @param imgRrect 读取图片的区域
     * @return 图片内容
     */
    public static BufferedImage readRectangelImage(String filePath, int iPage, Rectangle imgRrect) {

        BufferedImage bufImage = null;
        try(PDDocument pdfDoc = PDDocument.load(new File(filePath))) {
            // 获取渲染器，主要用来后面获取BufferedImage
            PDFRenderer pdfRenderer = new PDFRenderer(pdfDoc);
            // 截取指定位置生产图片
            bufImage = pdfRenderer.renderImage(iPage).getSubimage(imgRrect.x,imgRrect.y,imgRrect.width,imgRrect.height);

            // 释放资源
            pdfDoc.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return bufImage;
    }

    public static void main(String[] args) throws IOException {

        String pdfPath="D:\\lihang\\中科天玑产品管理规范.pdf";

        // 3, 507, 714, 408, 33
        //3, 507, 681, 372, 33
        //3, 507, 649, 372, 33
        //3, 507, 616, 372, 33
        //3, 507, 584, 408, 33

        //3, 99, 714, 408, 33
        //3, 135, 681, 372, 33
        //3, 135, 649, 372, 33

        //3, 99, 681, 408, 33
        //3, 135, 649, 372, 33
        //3, 135, 616, 372, 33
        //3, 135, 584, 372, 33
        Rectangle textRrect = new Rectangle(99, 681, 408, 33);

        String strContent = PdfboxUtils.readRectangelText(pdfPath, 2, textRrect);

        System.out.println(strContent);

        // 保存图片
       /* Rectangle imgRrect = new Rectangle(0, 76, 420, 240);
        BufferedImage bufImage = PdfboxUtils.readRectangelImage(pdfPath, 0, imgRrect);

        File outputfile = new File("D:\\pdfImage2.png");
        ImageIO.write(bufImage, "png", outputfile);*/

    }

}
