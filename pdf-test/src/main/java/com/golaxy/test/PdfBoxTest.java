package com.golaxy.test;

import com.alibaba.fastjson.JSONObject;
import com.golaxy.entity.Vo;
import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageXYZDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * 使用pdfbox 通过pdf标签解析目录并获取目录所在页码， 通过具体页码每行内容与对应标题比对确定段落 会存在错误
 */
public class PdfBoxTest {


    /**
     *  获取pdf标签构成目录层级
     * @param bookmark
     * @param indentation
     * @param level
     * @param doc
     * @param vos
     * @throws Exception
     */
    public static void printBookmarks(PDOutlineNode bookmark, String indentation, Integer level, PDDocument doc,LinkedList<Vo> vos) throws Exception{
        PDOutlineItem current = bookmark.getFirstChild();

        while(current != null){
            int pages = 0;

            if(current.getDestination() instanceof PDPageXYZDestination){
                PDPageXYZDestination pd = (PDPageXYZDestination) current.getDestination();
                pages = pd.retrievePageNumber() + 1;
            }
            if (current.getAction()  instanceof PDActionGoTo) {
                PDActionGoTo gta = (PDActionGoTo) current.getAction();
                if (gta.getDestination() instanceof PDPageDestination) {
                    PDPageDestination pd = (PDPageDestination) gta.getDestination();
                    pages = pd.retrievePageNumber() + 1;
                }
            }
            if (pages == 0) {
                System.out.println(indentation+current.getTitle());
            }else{
                vos.add(Vo.builder().pageNum(pages).title(current.getTitle()).level(level).build());
                System.out.println(indentation+current.getTitle()+"  "+pages + "     " + level);
            }
            printBookmarks( current, indentation + " ", level + 1 , doc, vos);
            current = current.getNextSibling();
        }
    }
    public static void main(String[] args) throws Exception {
        String file = "D:\\lihang\\test\\test4.pdf";
         //test1(file);
         test3(file);
        //test2(file);
    }

    /*public static void test2(String fileStr) throws Exception {
        File file = new File(fileStr);
        PDDocument doc = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            PDFParser parser = new PDFParser(new RandomAccessBuffer(fis));
            parser.parse();
            doc = parser.getPDDocument();

            PDDocumentCatalog catalog = doc.getDocumentCatalog();

            PDDocumentOutline outline = catalog.getDocumentOutline();
            LinkedList<Vo> vos = new LinkedList<>();
            if (outline != null) {
                printBookmarks(outline, "", 1, doc, vos);
            }

            // 处理vo获取标题内容
            System.out.println(JSONObject.toJSONString(vos));

            for (int i = 0; i < vos.size(); i++) {
                Vo vo = vos.get(i);
                //文本剥离器
                PDFTextStripper2 stripper = new PDFTextStripper2();
                //按页进行读取，页码从1开始
                stripper.setStartPage(vo.getPageNum());

                Vo secondVo = null;
                if (i+1 < vos.size()) {
                    secondVo = vos.get(i + 1);
                    stripper.setEndPage(secondVo.getPageNum());
                }
                //按位置进行排序
                //stripper.setSortByPosition(true);

                //这里可以自己for循环处理，我为了过滤PDF page的头和页脚，直接读body
                //注意，body里面遇到表格，图片，会放到ls的最后面，方便处理
                List<List<TextPosition>> ll = stripper.getCharactersByArticle();
//          List<TextPosition> ls = ll.get(0);//读取PDF page的header
                List<TextPosition> ls = ll.get(1);//读取pdf page的body内容
//          List<TextPosition> ls = ll.get(2);//读取PDF page的页码部分

                float y = 0;
                int buttom;//每行距离下面一行的距离

                StringBuffer sentence  = new StringBuffer();
                for (TextPosition tp : ls) {
                    String c = tp.getUnicode();

                    //根据高度来判断是否是一句话
                    if (y != tp.getY()) {
                        System.out.print(sentence.toString());

                        buttom = (int) (tp.getY() - y);
                        if (buttom > 11 || buttom < -10) {
                            System.out.println();
                        }
                        y = tp.getY();
                        sentence.setLength(0);
                    }

                    sentence.append(c);

                    //特殊处理符号
                    if (c.equals("•")) {
                        sentence.append(" ");
                    }

                    //遇到表格不打印出来
                    if (sentence.toString().indexOf("表格 ") == 0 || sentence.toString().indexOf("Table ") == 0) {
                        break;
                    }
                }

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }*/


    /**
     *  根据indexOf判断标题位置
     * @param fileStr
     * @throws Exception
     */
    public static void test1(String fileStr) throws Exception {
        File file = new File(fileStr);
        PDDocument doc = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            PDFParser parser = new PDFParser(new RandomAccessBuffer(fis));
            parser.parse();
            doc = parser.getPDDocument();

            PDDocumentCatalog catalog = doc.getDocumentCatalog();

            PDDocumentOutline outline = catalog.getDocumentOutline();
            LinkedList<Vo> vos = new LinkedList<>();
            if (outline != null) {
                printBookmarks(outline, "", 1, doc, vos);
            }

            // 处理vo获取标题内容
            System.out.println(JSONObject.toJSONString(vos));


            for (int i = 0; i < vos.size(); i++) {
                Vo vo = vos.get(i);
                //文本剥离器
                PDFTextStripper stripper = new PDFTextStripper();
                //按页进行读取，页码从1开始
                stripper.setStartPage(vo.getPageNum());
                stripper.setWordSeparator("");
                Vo secondVo = null;
                if (i+1 < vos.size()) {
                    secondVo = vos.get(i + 1);
                    stripper.setEndPage(secondVo.getPageNum());
                }
                //按位置进行排序
                stripper.setSortByPosition(true);
                //获取文本
                String text = stripper.getText(doc);

                Integer star = text.indexOf(vo.getTitle());
                Integer end = -1;

                if (secondVo != null) {
                    end = text.lastIndexOf(secondVo.getTitle());
                }

                if (end != -1) {
                    vo.setContent(text.substring(star + vo.getTitle().length(), end));
                } else {
                    vo.setContent(text.substring(star + vo.getTitle().length()));
                }


            }

            System.out.println(JSONObject.toJSONString(vos));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param fileStr
     * @throws Exception
     */
    public static void test3(String fileStr) throws Exception {
        File file = new File(fileStr);
        PDDocument doc = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            PDFParser parser = new PDFParser(new RandomAccessBuffer(fis));
            parser.parse();
            doc = parser.getPDDocument();

            PDDocumentCatalog catalog = doc.getDocumentCatalog();

            PDDocumentOutline outline = catalog.getDocumentOutline();
            LinkedList<Vo> vos = new LinkedList<>();


            // 获取所有标签构成的目录
            if (outline != null) {
                printBookmarks(outline, "", 1, doc, vos);
            }

            // 处理vo获取标题内容
            System.out.println(JSONObject.toJSONString(vos));


            // 获取每个页码下的每行内容
            HashMap<Integer, LinkedList<String>> dataMap = new HashMap<>();

            for (int i = 0; i <= doc.getNumberOfPages(); i++) {

                //文本剥离器
                PDFTextStripper stripper = new PDFTextStripper();
                //按页进行读取，页码从1开始
                stripper.setStartPage(i);
                stripper.setWordSeparator("");
                stripper.setEndPage(i);

                //按位置进行排序
                stripper.setSortByPosition(true);
                //获取文本
                String text = stripper.getText(doc);

                String[] split = text.split("\\r\\n");
                dataMap.put(i, new LinkedList<String>(Arrays.asList(split)));
            }


            StringBuffer content = null;

            // 根据目录标题获取对应的每行内容
            for (int i = 0; i < vos.size(); i++) {
                content = new StringBuffer();
                Vo vo = vos.get(i);
                Vo secondVo = null;
                if (i+1 < vos.size()) {
                    secondVo = vos.get(i + 1);
                }

                Integer star = vo.getPageNum();
                Integer end = secondVo == null ? null : secondVo.getPageNum();

                if (star.equals(end)) {
                    LinkedList<String> strings = dataMap.get(star);

                    Iterator<String> iterator = strings.iterator();
                    boolean flag = false;
                    while (iterator.hasNext()) {
                        String next = iterator.next();
                        if (secondVo.getTitle().trim().equals(next.trim())) {

                            break;
                        }
                        if (flag) {
                            content.append(next).append("\r\n");
                        }
                        if (vo.getTitle().trim().equals(next.trim())) {
                            flag = true;
                        } else {
                            iterator.remove();
                        }
                    }


                } else if (end != null) {
                    for (int j = star; j < end+1; j++) {
                        LinkedList<String> strings = dataMap.get(j);

                        Iterator<String> iterator = strings.iterator();

                        if (j == star) {
                            boolean flag = false;
                            while (iterator.hasNext()) {
                                String next = iterator.next();
                                if (flag) {
                                    content.append(next).append("\r\n");
                                }
                                if (vo.getTitle().trim().equals(next.trim())) {
                                    flag = true;
                                } else {
                                    iterator.remove();
                                }
                            }
                        } else if (j == end) {
                            while (iterator.hasNext()) {
                                String next = iterator.next();
                                if (secondVo.getTitle().trim().equals(next.trim())) {
                                    vo.setContent(content.toString());
                                    break;
                                } else {
                                    content.append(next).append("\r\n");
                                }
                            }
                        } else {
                            while (iterator.hasNext()) {
                                String next = iterator.next();
                                content.append(next).append("\r\n");
                            }
                        }


                    }
                } else {
                    boolean flag = false;
                    for (int j = star; j < doc.getNumberOfPages()+1; j++) {
                        LinkedList<String> strings = dataMap.get(j);

                        Iterator<String> iterator = strings.iterator();
                        if (j == star) {
                            while (iterator.hasNext()) {
                                String next = iterator.next();
                                if (flag) {
                                    content.append(next).append("\r\n");
                                }
                                if (vo.getTitle().trim().equals(next.trim())) {
                                   flag = true;
                                }
                            }
                        } else {
                            while (iterator.hasNext()) {
                                String next = iterator.next();
                                content.append(next).append("\r\n");
                            }
                        }
                    }
                }
                System.out.println(content.toString());
                vo.setContent(content.toString());

            }

            System.out.println(JSONObject.toJSONString(vos));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }




}


