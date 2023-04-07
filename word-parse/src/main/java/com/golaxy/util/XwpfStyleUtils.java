package com.golaxy.util;

import org.apache.poi.xwpf.usermodel.XWPFStyle;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyle;

import java.math.BigInteger;
import java.util.Arrays;

public class XwpfStyleUtils {

    /**
     * 获取大纲级别
     * @param xwpfStyle 样式
     * @return
     */
    public static BigInteger getOutlineLvl(XWPFStyle xwpfStyle) {
        final CTStyle ctStyle = xwpfStyle.getCTStyle();
        if(ctStyle != null) {
            final CTPPr pPr = ctStyle.getPPr();
            if (pPr != null) {
                final CTDecimalNumber outlineLvl = pPr.getOutlineLvl();
                if (outlineLvl != null) {
                    return outlineLvl.getVal();
                }
            }
        }
        return null;
    }

    /**
     * 判断是否是标题样式
     * @param xwpfStyle 样式
     * @return
     */
    public static boolean isTitleStyle(XWPFStyle xwpfStyle){
        final BigInteger outlineLvl = getOutlineLvl(xwpfStyle);
        if(outlineLvl != null && BigInteger.ZERO.compareTo(outlineLvl) <= 0){
            return true;
        }
        return Arrays.stream(WordDocTitleEnum.values()).anyMatch(item -> item.getName().equals(xwpfStyle.getName()));
    }

}
