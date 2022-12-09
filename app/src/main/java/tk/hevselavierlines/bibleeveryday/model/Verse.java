package tk.hevselavierlines.bibleeveryday.model;

import java.io.Serializable;

public class Verse implements Serializable {
    private int number;
    private String text;
    private Chapter chapter;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public Verse getNextVerse(int increment) {
        Verse ret = getChapter().getVerses().get(number + increment);
        if(ret == null) {
            Chapter nextChapter = Chapter.getNextChapter(chapter);
            if(nextChapter == null) {
                ret = getChapter().getVerses().get(number);
            } else {
                ret = nextChapter.getFirstVerse();
            }
        }
        return ret;
    }

    public Verse getPreviousVerse(int decrement) {
        Verse ret = getChapter().getVerses().get(number - decrement);
        if(ret == null) {
            Chapter previousChapter = Chapter.getPreviousChapter(chapter);
            if(previousChapter == null) {
                ret = getChapter().getVerses().get(number);
            } else {
                ret = previousChapter.getLastVerse(decrement);
            }
        }
        return ret;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(chapter.getBook().getName())
                .append(' ').append(chapter.getNumber())
                .append(' ').append(number);
        return sb.toString();
    }
}
