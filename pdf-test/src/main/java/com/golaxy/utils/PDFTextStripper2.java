package com.golaxy.utils;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.List;

public class PDFTextStripper2  extends PDFTextStripper {
    public PDFTextStripper2() throws IOException {
    }

    @Override
    public List<List<TextPosition>> getCharactersByArticle() {
        return super.getCharactersByArticle();
    }
}
