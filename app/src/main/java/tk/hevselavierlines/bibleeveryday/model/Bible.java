package tk.hevselavierlines.bibleeveryday.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Bible {
    private String language;
    private String version;
    private Map<Integer, Book> books;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<Integer, Book> getBooks() {
        return books;
    }

    public void setBooks(Map<Integer, Book> books) {
        this.books = books;
    }
}
