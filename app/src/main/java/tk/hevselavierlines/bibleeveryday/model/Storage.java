package tk.hevselavierlines.bibleeveryday.model;

public class Storage {
    private Bible bible;
    private int verseAmount;

    private Storage() {

    }

    private static Storage INSTANCE;

    public static Storage getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Storage();
        }
        return INSTANCE;
    }

    public Bible getBible() {
        return bible;
    }

    public void setBible(Bible bible) {
        this.bible = bible;
    }

    public int getVerseAmount() {
        return verseAmount;
    }

    public void setVerseAmount(int verseAmount) {
        this.verseAmount = verseAmount;
    }
}
