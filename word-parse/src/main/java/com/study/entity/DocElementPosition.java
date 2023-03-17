package com.study.entity;

public class DocElementPosition {
    private final Object element;
    private final int index;
    private final int startOffset;

    public DocElementPosition(Object element, int index, int startOffset) {
        this.element = element;
        this.index = index;
        this.startOffset = startOffset;
    }

    public Object getElement() {
        return element;
    }

    public int getIndex() {
        return index;
    }

    public int getStartOffset() {
        return startOffset;
    }
}
