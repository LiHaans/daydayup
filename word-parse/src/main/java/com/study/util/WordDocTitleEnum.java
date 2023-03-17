package com.study.util;

public enum WordDocTitleEnum {

    ONE_LEVEL_TITLE("heading 1", "一级标题"),
    TWO_LEVEL_TITLE("heading 2", "二级标题"),
    THREE_LEVEL_TITLE("heading 3", "三级标题"),
    FOUR_LEVEL_TITLE("heading 4", "四级标题"),
    FIVE_LEVEL_TITLE("heading 5", "五级标题"),
    SIX_LEVEL_TITLE("heading 6", "六级标题"),
    SEVEN_LEVEL_TITLE("heading 7", "七级标题"),
    EIGHT_LEVEL_TITLE("heading 8", "八级标题"),
    NINE_LEVEL_TITLE("heading 9", "九级标题");

    private final String name;

    private final String desc;

    WordDocTitleEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
