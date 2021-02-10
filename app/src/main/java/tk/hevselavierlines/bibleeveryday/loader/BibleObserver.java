package tk.hevselavierlines.bibleeveryday.loader;

import tk.hevselavierlines.bibleeveryday.model.Bible;

public interface BibleObserver {
    void bibleLoaded(Bible bible);
}
