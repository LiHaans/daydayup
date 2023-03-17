package com.stuty.word;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.StyleDescription;
import org.apache.poi.hwpf.model.StyleSheet;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.List;

public class PoiTest {


    public static void main(String[] args) throws Exception {
        String fileName = "D:\\lihang\\中科天玑产品管理规范.docx";
        //readDoc(fileName);
        //readDocx(fileName);
        getTitle(fileName);

    }

    public static void readDoc(String fileName) throws Exception {
        // 读取doc格式的word文档
        HWPFDocument document = new HWPFDocument(new FileInputStream(fileName));
        Range range = document.getRange();
        StyleSheet styleSheet = document.getStyleSheet();
        for(
                int i = 0; i<range.numParagraphs();i++)

        {
            Paragraph paragraph = range.getParagraph(i);
            StyleDescription styleDescription = styleSheet.getStyleDescription(paragraph.getStyleIndex());
            String styleName = styleDescription.getName();
            String text = paragraph.text();
            System.out.println(styleName + ": " + text);
        }
    }
    public static void readDocx(String fileName) throws Exception {
        // 读取docx格式的word文档
        XWPFDocument document = new XWPFDocument(new FileInputStream(fileName));
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for(
                XWPFParagraph paragraph :paragraphs)

        {
            String styleName = paragraph.getStyle();
            String text = paragraph.getText();
            System.out.println(styleName + ": " + text);
        }
    }

    public static void getTitle(String fileName) throws Exception {
        // 读取word文档中的大纲信息
        XWPFDocument document = new XWPFDocument(new FileInputStream(fileName));
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for(
                XWPFParagraph paragraph :paragraphs)

        {
            CTPPr ppr = paragraph.getCTP().getPPr(); // 获取段落属性
            if (ppr != null) {
                CTDecimalNumber outlineLvlElement = ppr.getOutlineLvl(); // 获取大纲级别元素
                if (outlineLvlElement != null) {
                    BigInteger outlineLvlValueElementVal = outlineLvlElement.getVal(); // 获取大纲级别值
                    int level = outlineLvlValueElementVal.intValue(); // 转换为int类型
                    System.out.println("level: " + level); // 打印输出
                }
            }
        }
    }



}
