package com.study.test;

import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ItTextPdfTest {

    public static void main(String[] args) throws Exception {
        PdfReader reader = new PdfReader("D:\\lihang\\中科天玑产品管理规范-1.pdf");

        //创建pdf解析类
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);

        PRAcroForm acroForm = reader.getAcroForm();

        AcroFields acroFields = reader.getAcroFields();

        PdfDictionary catalog = reader.getCatalog();

        HashMap<Object, PdfObject> namedDestination = reader.getNamedDestination();

        HashMap<String, String> info = reader.getInfo();

        String s = new String(reader.getMetadata());
        System.out.println(s);


        List<HashMap<String, Object>> list = SimpleBookmark.getBookmark(reader);


        for (Iterator<HashMap<String, Object>> i = list.iterator(); i.hasNext(); ) {
            HashMap<String, Object> next = i.next();
            showBookmark(next);
            //getPageNumbers(next);

        }

    }

    //递归获取标题及子目录标题
    private static void showBookmark(HashMap<String, Object> bookmark) {
        System.out.println(bookmark.get("Title"));
        if ("GoTo".equals(bookmark.get("Action"))) {
            String page = (String) bookmark.get("Page");
            if (page != null) {

                page = page.trim();

                int idx = page.indexOf(' ');

                int pageNum;

                if (idx < 0) {

                    pageNum = Integer.parseInt(page);
                    System.out.println("pageNum :" + pageNum);
                } else {

                    pageNum = Integer.parseInt(page.substring(0, idx));
                    System.out.println("pageNum:" + pageNum);
                }
            }
        }
        @SuppressWarnings("unchecked")
        ArrayList<HashMap<String, Object>> kids = (ArrayList<HashMap<String, Object>>) bookmark.get("Kids");
        if (kids == null)
            return;
        for (Iterator<HashMap<String, Object>> i = kids.iterator(); i.hasNext(); ) {

            showBookmark(i.next());
        }
    }

    //获取页码
    public static void getPageNumbers(HashMap<String, Object> bookmark) {
        if (bookmark == null)
            return;

        if ("GoTo".equals(bookmark.get("Action"))) {

            String page = (String) bookmark.get("Page");
            if (page != null) {

                page = page.trim();

                int idx = page.indexOf(' ');

                int pageNum;

                if (idx < 0) {

                    pageNum = Integer.parseInt(page);
                    System.out.println("pageNum :" + pageNum);
                } else {

                    pageNum = Integer.parseInt(page.substring(0, idx));
                    System.out.println("pageNum:" + pageNum);
                }
            }
            @SuppressWarnings("unchecked")
            ArrayList<HashMap<String, Object>> kids = (ArrayList<HashMap<String, Object>>) bookmark.get("Kids");
            if (kids == null)
                return;
            for (Iterator<HashMap<String, Object>> i = kids.iterator(); i.hasNext(); ) {

                getPageNumbers(i.next());
            }

        }
    }

}


