package com.example.restapitest.model;

import java.util.List;

public class TextParagraph {

    private String header;
    private List<String> strings;
    private int levelParagraph;

    public TextParagraph(String header, List<String> strings, int levelParagraph) {
        this.header = header;
        this.strings = strings;
        this.levelParagraph = levelParagraph;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void addString(String str){
        strings.add(str);
    }

    public int getLevelParagraph() {
        return levelParagraph;
    }

    public void setLevelParagraph(int levelParagraph) {
        this.levelParagraph = levelParagraph;
    }

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

}
