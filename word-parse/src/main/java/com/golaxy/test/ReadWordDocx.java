package com.golaxy.test;

import com.alibaba.fastjson.JSONObject;
import com.golaxy.entity.Vo;
import com.golaxy.util.XwpfStyleUtils;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

public class ReadWordDocx {

    public static void main(String[] args) throws Exception {
//        String filePath = "D:\\lihang\\中科天玑产品管理规范.docx";
        String filePath = "D:\\lihang\\文件信息查询下载接口说明文档-1.1.doc";
         test1(filePath);
        //test2(filePath);

    }


    /**
     *  只获取段落 不处理表格 目录
     * @param filePath
     * @throws Exception
     */
    public static void test2(String filePath) throws Exception  {
        // 读取Word文件

        FileInputStream fis = new FileInputStream(filePath);

        XWPFDocument doc = new XWPFDocument(fis);


        List<XWPFParagraph> paragraphsList = doc.getParagraphs();
        LinkedList<Vo> vos = new LinkedList<>();
        boolean flag = false;
        StringBuffer sb = new StringBuffer();
        StringBuffer other = new StringBuffer();
        Vo vo = null;
        for (XWPFParagraph paragraph : paragraphsList) {

            // 获取页码

            // 判断paragraph是不是页眉页脚

            //判断该段落是否设置了大纲级别
            if (paragraph.getCTP().getPPr().getOutlineLvl() != null || isTitle(paragraph, doc.getStyles())) {
                if (flag) {
                    vo.setContent(sb.toString());
                    vos.add(vo);

                    vo = Vo.builder().title(paragraph.getText()).build();
                    sb = new StringBuffer();
                } else {
                    flag = true;
                    vo = Vo.builder().title(paragraph.getText()).build();
                }

            } else {
                if (flag) {
                    sb.append(paragraph.getText()).append("\r\n");
                } else {
                    other.append(paragraph.getText()).append("\r\n");
                }
            }

        }

        if (vo!= null) {
            vo.setContent(sb.toString());
            vos.add(vo);
        }
        vos.add(Vo.builder().content(other.toString()).title("other").build());

        System.out.println(JSONObject.toJSONString(vos));
    }


    /**
     *  获取 段落 表格 目录
     * @param filePath
     * @throws Exception
     */
    public static void test1(String filePath) throws Exception  {
        // 读取Word文件

        FileInputStream fis = new FileInputStream(filePath);

        XWPFDocument doc = new XWPFDocument(fis);

        // 获得Word中的目录信息
        List<IBodyElement> bodyElements = doc.getBodyElements();

        LinkedList<Vo> vos = new LinkedList<>();
        boolean flag = false;
        StringBuffer sb = new StringBuffer();
        StringBuffer other = new StringBuffer();
        Vo vo = null;

        for (IBodyElement bodyElement : bodyElements) {
            if (bodyElement instanceof XWPFParagraph) {
                XWPFParagraph paragraph = (XWPFParagraph)bodyElement;

                if (isTitle(paragraph, doc.getStyles())) {
                    Integer level = getTitleLevel(paragraph, doc.getStyles());
                    if (flag) {
                        vo.setContent(sb.toString());
                        vos.add(vo);

                        vo = Vo.builder().title(paragraph.getText()).level(level).build();
                        sb = new StringBuffer();
                    } else {
                        flag = true;
                        vo = Vo.builder().title(paragraph.getText()).level(level).build();
                    }

                } else {
                    if (flag) {
                        sb.append(paragraph.getText()).append("\r\n");
                    } else {
                        other.append(paragraph.getText()).append("\r\n");
                    }
                }
            } else if (bodyElement instanceof XWPFSDT) {
                XWPFSDT xwpfsdt = (XWPFSDT)bodyElement;
                ISDTContent content = xwpfsdt.getContent();
                System.out.println(content);

            } else if (bodyElement instanceof XWPFTable) {
                XWPFTable table = (XWPFTable)bodyElement;
                // 遍历表格, 取出表格中各行内容
                List<XWPFTableRow> rows = table.getRows();
                for (int i = 0; i < rows.size(); i++) {
                    XWPFTableRow row = rows.get(i);
                    List<XWPFTableCell> cells = row.getTableCells();
                    for (int j = 0; j < cells.size(); j++) {
                        XWPFTableCell cell = cells.get(j);
                        System.out.println("row:" + i + " col:" + j + " content:" + cell.getText());

                        if (flag) {
                            sb.append(cell.getText()).append("  ");
                        } else {
                            other.append(cell.getText()).append("   ");
                        }
                    }

                    if (flag) {
                        sb.append("\r\n");
                    } else {
                        other.append("\r\n");
                    }
                }
            } else {

            }
        }

        if (vo!= null) {
            vo.setContent(sb.toString());
            vos.add(vo);
        }
        vos.add(Vo.builder().content(other.toString()).title("other").build());

        System.out.println(JSONObject.toJSONString(vos));
    }

    /**
     * 判断段落是否为标题段落
     * @param paragraph 段落
     * @param styles 文档中定义的样式
     * @return
     */
    public static boolean isTitle(XWPFParagraph paragraph, XWPFStyles styles) {

        // 判断是否有outlineLvl属性若有则为标题
        if (paragraph != null &&
                paragraph.getCTP() != null &&
                paragraph.getCTP().getPPr() != null &&
                paragraph.getCTP().getPPr().getOutlineLvl() != null
        ) {
            return true;
        }

        // 判断样式名称是否是“heading 1”、“heading 2”、“heading 3”、“heading 4”、“heading 5”、“heading 6”、“heading 7”、“heading 8”、“heading 9”，若是则为标题
        String styleId = paragraph.getStyleID();
        while(styleId != null) {
            final XWPFStyle style = styles.getStyle(styleId);
            if(style == null){
                break;
            }
            boolean isTitle = XwpfStyleUtils.isTitleStyle(style);
            if(isTitle){
                return true;
            }
            styleId = style.getBasisStyleID();
        }
        return false;
    }

    public static Integer getTitleLevel(XWPFParagraph paragraph, XWPFStyles styles) {
        if (paragraph.getCTP().getPPr().getOutlineLvl() != null) {
            return paragraph.getCTP().getPPr().getOutlineLvl().getVal().intValue();
        }
        String styleId = paragraph.getStyleID();
        while(styleId != null) {
            final XWPFStyle style = styles.getStyle(styleId);
            if(style == null){
                break;
            }
            final BigInteger outlineLvl = XwpfStyleUtils.getOutlineLvl(style);
            if(outlineLvl != null && BigInteger.ZERO.compareTo(outlineLvl) <= 0){
                return outlineLvl.intValue();
            }
            styleId = style.getBasisStyleID();
        }
        return null;
    }
}
