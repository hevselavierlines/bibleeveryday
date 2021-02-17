package tk.hevselavierlines.bibleeveryday.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Bible implements Serializable {
    private String language;
    private String version;
    private Map<Integer, Book> books;

    private static Bible INSTANCE;

    public static Bible getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Bible();
        }
        return INSTANCE;
    }

    private Bible(){}

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
