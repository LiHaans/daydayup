package com.study.pdf;


import com.study.entity.MyXWPFDocument;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Pdf2wordNew {

    public static void main(String[] args) throws Exception {

        try {

            String pdfFileName = "D:\\lihang\\test4.pdf";

            PDDocument pdf = PDDocument.load(new File(pdfFileName));
            int pageNumber = pdf.getNumberOfPages();

            String docFileName = pdfFileName.substring(0, pdfFileName.lastIndexOf(".")) + ".doc";

            File file = new File(docFileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            MyXWPFDocument document = new MyXWPFDocument();
            FileOutputStream fos = new FileOutputStream(docFileName);

            //提取每一页的图片和文字，添加到 word 中
            for (int i = 0; i < pageNumber; i++) {
                PDPage page = pdf.getPage(i);
                PDResources resources = page.getResources();
                Iterable<COSName> names = resources.getXObjectNames();
                Iterator<COSName> iterator = names.iterator();
                while (iterator.hasNext()) {
                    COSName cosName = iterator.next();
                    if (resources.isImageXObject(cosName)) {
                        PDImageXObject imageXObject = (PDImageXObject) resources.getXObject(cosName);
                        File outImgFile = new File("D:\\lihang\\"
                                + System.currentTimeMillis() + ".jpg");
                        Thumbnails.of(imageXObject.getImage()).scale(1).rotate(0).toFile(outImgFile);
                        BufferedImage bufferedImage = ImageIO.read(outImgFile);
                        int width = bufferedImage.getWidth();
                        int height = bufferedImage.getHeight();
                        if (width > 600) {
                            double ratio = Math.round((double) width / 550.0);
                            System.out.println("缩放比ratio：" + ratio);
                            width = (int) (width / ratio);
                            height = (int) (height / ratio);
                        }
                        System.out.println("width: " + width + ",  height: " + height);
                        FileInputStream in = new FileInputStream(outImgFile);
                        byte[] ba = new byte[in.available()];
                        in.read(ba);
                        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(ba);
                        XWPFParagraph picture = document.createParagraph();
                        //添加图片
                        document.addPictureData(byteInputStream, MyXWPFDocument.PICTURE_TYPE_JPEG);
                        //图片大小、位置
                        document.createPicture(document.getAllPictures().size() - 1, width, height, picture);
                    }
                }
                PDFTextStripper stripper = new PDFTextStripper();
                stripper.setSortByPosition(true);
                stripper.setStartPage(i);
                stripper.setEndPage(i);
                //当前页中的文字
                String text = stripper.getText(pdf);
                System.out.println("  ==========  " + text);
                XWPFParagraph textParagraph = document.createParagraph();
                XWPFRun textRun = textParagraph.createRun();
                // 处理换行问题
                if (text.contains("\r\n")) {
                    String[] split = text.split("\r\n");
                    List<String> strsToList1 = Arrays.asList(split);
                    for (String str : strsToList1) {
                        System.out.println(str);
                        textRun.setText(str);
                        textRun.addCarriageReturn();
                    }
                }
//                textRun.setText(text);
                textRun.setFontFamily("仿宋");
                textRun.setFontSize(10);
                //换行
                // 插入换行符
                textParagraph.setWordWrap(true);
            }
            document.write(fos);
            fos.close();
            pdf.close();
            System.out.println("pdf转换解析结束！！----");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
