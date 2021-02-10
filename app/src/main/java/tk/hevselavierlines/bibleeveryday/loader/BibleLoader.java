package tk.hevselavierlines.bibleeveryday.loader;

import android.os.AsyncTask;

import java.io.InputStream;

import tk.hevselavierlines.bibleeveryday.model.Bible;

public class BibleLoader extends AsyncTask<InputStream, Void, Bible> {
    private BibleObserver bibleObserver;
    private MapXmlToBible mapper;

    public BibleLoader(BibleObserver bibleObserver) {
        this.bibleObserver = bibleObserver;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mapper = new MapXmlToBible();
    }

    @Override
    protected Bible doInBackground(InputStream... inputStreams) {
        return mapper.execute(inputStreams[0]);
    }

    @Override
    protected void onPostExecute(Bible bible) {
        super.onPostExecute(bible);
        if(this.bibleObserver != null) {
            this.bibleObserver.bibleLoaded(bible);
        }
    }
}
