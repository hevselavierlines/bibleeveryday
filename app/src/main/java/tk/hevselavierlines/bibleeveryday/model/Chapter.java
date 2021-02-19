package tk.hevselavierlines.bibleeveryday.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Chapter implements Serializable {
    private int number;
    private Map<Integer, Verse> verses;
    private Book book;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Map<Integer, Verse> getVerses() {
        return verses;
    }

    public Verse getFirstVerse() {
        return verses.get(1);
    }

    public Verse getLastVerse(int verseAmount) {
        int verseNumber = verses.size();
        int amount = verseNumber % verseAmount;
        if(amount == 0) {
            amount = verseAmount;
        }
        verseNumber -= (amount - 1);
        return verses.get(verseNumber);
    }

    public void setVerses(Map<Integer, Verse> verses) {
        this.verses = verses;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int countVerses() {
        return verses.size();
    }

    public static Chapter getPreviousChapter(Chapter currentChapter) {
        int chapterNumber = currentChapter.getNumber();
        Book currentBook = currentChapter.getBook();
        if(chapterNumber == 1) {
            currentBook = currentBook.getBible().getBooks().get(currentBook.getNumber() - 1);
            if(currentBook == null) {
                // current book is Genesis, no previous book
                return null;
            }
            chapterNumber = currentBook.getChapters().size();
        } else {
            chapterNumber--;
        }
        return currentBook.getChapters().get(chapterNumber);
    }

    public static Chapter getNextChapter(Chapter currentChapter) {
        int chapterNumber = currentChapter.getNumber();
        Book currentBook = currentChapter.getBook();
        if(chapterNumber == currentBook.countChapters()) {
            currentBook = currentBook.getBible().getBooks().get(currentBook.getNumber() + 1);
            if(currentBook == null) {
                // current book is revelation, no further book.
                return null;
            }
            chapterNumber = 1;
        } else {
            chapterNumber++;
        }
        return currentBook.getChapters().get(chapterNumber);
    }

    public String getVersesRange(int start, int amount) {
        StringBuilder sb = new StringBuilder();
        sb.append("<p>");
        int curr = start;
        for(int i = 0; i < amount; i++) {
            Verse currVerse = verses.get(curr);
            if(currVerse != null) {
                sb.append("<span><b>");
                sb.append(curr);
                sb.append("</b></sup> ");
                sb.append(currVerse.getText());
                sb.append("<br/>");
            }
            curr++;
        }
        sb.append("</p>");
        return sb.toString();
    }

}
