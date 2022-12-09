package tk.hevselavierlines.bibleeveryday.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import tk.hevselavierlines.bibleeveryday.model.Bible;
import tk.hevselavierlines.bibleeveryday.model.Book;
import tk.hevselavierlines.bibleeveryday.model.Chapter;
import tk.hevselavierlines.bibleeveryday.model.Storage;
import tk.hevselavierlines.bibleeveryday.model.Verse;

public class Searcher {
    private class SearchElems {
        String book;
        String chapter;
        String vers;

        public String toString() {
            return book + chapter + vers;
        }
    }
    private Searcher() {

    }

    private static Searcher INSTANCE = new Searcher();

    public static Searcher getInstance() {
        return INSTANCE;
    }

    public List<Verse> search(String input) {
        Bible bible = Storage.getInstance().getBible();

        SearchElems elems = splitSearchElements(input);

        List<Book> bookList = bible.getBooks().values().stream()
                .filter(book ->
                        removeUmlauts(book.getName())
                                .contains(elems.book))
                .collect(Collectors.toList());
        if(bookList.size() == 0) {
            return Collections.EMPTY_LIST;
        }
        if(elems.chapter.length() == 0) {
            List<Verse> verseList = new ArrayList<>();
            bookList.forEach(book -> {
                verseList.add(book.getChapters().get(1).getFirstVerse());
            });
            return verseList;
        }
        List<Chapter> chapterList = new ArrayList<>();
        for(Book book : bookList) {
            chapterList.addAll(book.getChapters().values().stream().filter(chp -> String.valueOf(chp.getNumber()).contains(elems.chapter)).collect(Collectors.toList()));
        }
        if(elems.vers.length() == 0) {
            List<Verse> verseList = new ArrayList<>();
            chapterList.forEach(chp -> {
                verseList.add(chp.getFirstVerse());
            });
            return verseList;
        }
        List<Verse> verseList = new ArrayList<>();
        for(Chapter chapter : chapterList) {
            verseList.addAll(chapter.getVerses().values().stream().filter(verse -> String.valueOf(verse).contains(elems.vers)).collect(Collectors.toList()));
        }
        return verseList;
    }

    private SearchElems splitSearchElements(String input) {
        StringBuilder bookElem = new StringBuilder();
        StringBuilder chapElem = new StringBuilder();
        StringBuilder versElem = new StringBuilder();
        int mode = 0;
        char chars[] = input.toCharArray();
        for (int i = 0; i < input.toCharArray().length; i++) {
            char c = chars[i];
            if(c >= 'A' && c <= 'Z') {
                mode = 1;
                bookElem.append(c);
            } else if(c >= '0' && c <= '9') {
                if(mode == 1) {
                    mode = 2;
                    chapElem.append(c);
                } else if(mode == 0) {
                    bookElem.append(c);
                } else if(mode == 2) {
                    chapElem.append(c);
                } else if(mode == 3) {
                    versElem.append(c);
                }
            } else if(c == ' ') {
                if(mode > 0) {
                    mode++;
                }
            }
        }
        SearchElems searchElems = new SearchElems();
        searchElems.book = bookElem.toString();
        searchElems.chapter = chapElem.toString();
        searchElems.vers = versElem.toString();
        return searchElems;
    }

    private String removeUmlauts(String originalString) {
        StringBuilder newString = new StringBuilder(originalString.length());
        for (char c : originalString.toCharArray()) {
            char nC = Character.toUpperCase(c);
            switch(nC) {
                case '\u00c4':
                    nC = 'A';
                    break;
                case '\u00d6':
                    nC = 'O';
                    break;
                case '\u00dc':
                    nC = 'U';
                    break;
                case '\u00df':
                    nC = 'S';
            }
            if(nC != '.' && nC != ' ') {
                newString.append(nC);
            }
        }
        return newString.toString();
    }
}
