package tk.hevselavierlines.bibleeveryday.model;

import java.util.List;
import java.util.Map;

public class Book {
    private int number;
    private String name;
    private Map<Integer, Chapter> chapters;
    private Bible bible;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Integer, Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(Map<Integer, Chapter> chapters) {
        this.chapters = chapters;
    }

    public Bible getBible() {
        return bible;
    }

    public void setBible(Bible bible) {
        this.bible = bible;
    }

    public int countChapters() {
        return chapters.size();
    }
}
