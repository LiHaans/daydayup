package com.golaxy.test;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.golaxy.entity.Vo;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;

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


}
