package tk.hevselavierlines.bibleeveryday.loader;

import android.os.AsyncTask;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import tk.hevselavierlines.bibleeveryday.model.Bible;
import tk.hevselavierlines.bibleeveryday.model.Book;
import tk.hevselavierlines.bibleeveryday.model.Chapter;
import tk.hevselavierlines.bibleeveryday.model.Verse;

public class MapXmlToBible extends DefaultHandler {
    private Bible bible;
    private Book currentBook;
    private int currentBookNumber;
    private Map<Integer, Book> books;
    private Chapter currentChapter;
    private Map<Integer, Chapter> chapters;
    private Verse currentVerse;
    private Map<Integer, Verse> verses;
    private StringBuilder currentText;

    public Bible execute(InputStream inputStream) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        books = new LinkedHashMap<>();
        chapters = new LinkedHashMap<>();
        verses = new LinkedHashMap<>();
        currentText = new StringBuilder();
        try {
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(inputStream, this);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bible;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if("bible".equals(localName)) {
            bible = new Bible();
        }
        if("b".equals(localName)) {
            currentBook = new Book();
            currentBook.setNumber(++currentBookNumber);
            currentBook.setName(attributes.getValue("n"));
        } else if("c".equals(localName)) {
            currentChapter = new Chapter();
            currentChapter.setNumber(Integer.parseInt(attributes.getValue("n")));
        } else if("v".equals(localName)) {
            currentText.setLength(0);
            currentVerse = new Verse();
            currentVerse.setNumber(Integer.parseInt(attributes.getValue("n")));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if("b".equals(localName)) {
            currentBook.setChapters(chapters);
            currentBook.setBible(bible);
            books.put(currentBook.getNumber(), currentBook);
            chapters = new LinkedHashMap<>();
        } else if("c".equals(localName)) {
            currentChapter.setVerses(verses);
            currentChapter.setBook(currentBook);
            chapters.put(currentChapter.getNumber(), currentChapter);
            verses = new LinkedHashMap<>();
        } else if("v".equals(localName)) {
            currentVerse.setText(currentText.toString());
            currentVerse.setChapter(currentChapter);
            verses.put(currentVerse.getNumber(), currentVerse);
        } else if("bible".equals(localName)) {
            bible.setBooks(books);
            bible.setLanguage("ENG");
            bible.setVersion("NIV");
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        currentText.append(String.valueOf(ch, start, length));
    }
}
