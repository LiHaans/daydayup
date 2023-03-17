package com.stuty.word;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import com.study.entity.PicturesSource;
import com.study.entity.Vo;
import com.study.util.XwpfStyleUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.*;
import sun.nio.ch.IOUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ReadWordDoc {

    public static void main(String[] args) throws Exception {
//        String filePath = "D:\\lihang\\中科天玑产品管理规范.docx";
        String filePath = "D:\\lihang\\中科天玑产品管理规范.doc";
         test1(filePath);
        //test2(filePath);

    }




    /**
     *  获取 段落 表格 目录
     * @param filePath
     * @throws Exception
     */
    public static void test1(String filePath) throws Exception  {
        // 读取Word文件

        FileInputStream fis = new FileInputStream(filePath);

        HWPFDocument doc = new HWPFDocument(fis);

        ByteArrayOutputStream baos = null;//字节流，用来存储图片
        PicturesSource pictures = new PicturesSource(doc);
        PicturesTable pictureTable = doc.getPicturesTable();


        // 获取文档读取范围
        Range range = doc.getRange();

        LinkedList<Vo> vos = new LinkedList<>();
        boolean flag = false;
        StringBuffer sb = new StringBuffer();
        StringBuffer other = new StringBuffer();
        Vo vo = null;

        // 遍历段落
        for (int i = 0; i < range.numParagraphs(); i++) {
            Paragraph paragraph = range.getParagraph(i);


            for (int j = 0; j < paragraph.numCharacterRuns(); j++) {
                CharacterRun cr = paragraph.getCharacterRun(j);  //字符
                if(pictureTable.hasPicture(cr)){
                    baos = new ByteArrayOutputStream();
                    Picture picture = pictures.getFor(cr);
                    //如果是在页面显示图片，可转换为base64编码的图片
                    picture.writeImageContent(baos);//将图片写入字节流
                    // String base64Image = "<img src='data:image/png;base64,"+new BASE64Encoder().encode(baos.toByteArray())+"'/>";
                    FileUtil.writeBytes(picture.getContent(), new File("D:\\lihang\\"+i+"_"+j+".png"));
                }

            }


            // 判断是否是标题 0-8 为九级标题 9为正文
            Integer level = paragraph.getLvl();
            if (level < 9) {

                if (flag) {
                    vo.setContent(sb.toString());
                    vos.add(vo);

                    vo = Vo.builder().title(paragraph.text()).level(level).build();
                    sb = new StringBuffer();
                } else {
                    flag = true;
                    vo = Vo.builder().title(paragraph.text()).level(level).build();
                }

            }
            // 处理表格内容
            else if (paragraph.isInTable()) {

                if (flag) {
                    sb.append(paragraph.text().replace("\u0007", ""));

                    if (paragraph.isTableRowEnd()) {
                        sb.append("\r\n");
                    } else {
                        sb.append("     ");
                    }
                } else {
                    other.append(paragraph.text().replace("\u0007", ""));

                    if (paragraph.isTableRowEnd()) {
                        other.append("\r\n");
                    } else {
                        other.append("     ");
                    }
                }


            }
            else {
                if (flag) {
                    sb.append(paragraph.text()).append("\r\n");
                } else {
                    other.append(paragraph.text()).append("\r\n");
                }
            }
        }

        if (vo!= null) {
            vo.setContent(sb.toString());
            vos.add(vo);
        }
        vos.add(Vo.builder().content(other.toString()).title("other").build());

        System.out.println(JSONObject.toJSONString(vos));

        for (Vo vo1 : vos) {

            System.out.println(vo1.getTitle());
            System.out.println("");
            System.out.println(vo1.getContent());

            System.out.println("==========================================");
        }

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
