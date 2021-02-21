package tk.hevselavierlines.bibleeveryday.ui.main;

import android.text.Layout;
import android.text.Spannable;
import android.text.Spanned;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.util.HashMap;

public class PaginationController {
    private static final String TAG = tk.hevselavierlines.bibleeveryday.ui.main.PaginationController.class.getSimpleName();

    private final TextView mTextView1;
    private final TextView mTextView2;
    private int activeTextView = 1;

    private int mPageIndex;
    private CharSequence mText;
    private HashMap<Integer, Boundary> mBoundaries;
    private int mLastPageIndex;
    private boolean mFoundVerse;
    private PageUpdateListener updateListener;

    public TextView getActiveTextView() {
        if(activeTextView == 1) {
            return mTextView1;
        } else {
            return mTextView2;
        }
    }

    public PaginationController(TextView textView1, TextView textView2, PageUpdateListener pageUpdateListener) {
        mTextView1 = textView1;
        mTextView2 = textView2;
        this.updateListener = pageUpdateListener;
    }

    public void setFirstText(Spannable text) {
        onTextLoaded(text, true);
    }

    public void updateText(Spannable text) {
        onTextLoaded(text, false);
    }

    public void onTextLoaded(Spanned text, boolean update) {
        mPageIndex = 0;
        mLastPageIndex = -1;
        mBoundaries = new HashMap<>();
        mText = text;
        mFoundVerse = false;
        if(update) {
            setTextWithCaching(mPageIndex, 0, -1);
            findVerses();
        }
    }

    private void parseAllPages(boolean firstPage) {
        getActiveTextView().setText("");
        if(getActiveTextView().getLayout() == null) {
            getActiveTextView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    ViewTreeObserver obs = getActiveTextView().getViewTreeObserver();
                    obs.removeOnGlobalLayoutListener(this);
                    parseAllPages(firstPage);
                }
            });
        } else {
            int i = 0;
            setTextWithCaching(i++, 0, -1);
            while (mLastPageIndex < 0 && !firstPage) {
                setTextWithCaching(i, mBoundaries.get(i - 1).end, -1);
                i++;
            }
            if(firstPage) {
                this.mPageIndex = 0;
            } else {
                this.mPageIndex = mLastPageIndex;
            }
            this.findVerses();
        }
    }

    private void findVerses() {
        int startVerse = 0;
        int endVerse = 0;
        try {
            startVerse = findFirstVerseInText();
        } catch (RuntimeException ex) {
            Log.e("findVerses", "start number invalid");
        }
        try {
            endVerse = findLastVerseInText();
        } catch (RuntimeException ex) {
            Log.e("findVerses", "end number invalid");
        }
        updateListener.updatePage(startVerse, endVerse);
    }

    private int findFirstVerseInText() {
        int startVerse = 0;
        String text = getActiveTextView().getText().toString();
        int firstBlank = text.indexOf(" ");
        String textToBlank = text.substring(0, firstBlank);
        boolean isNumber = true;
        for(char ch : textToBlank.toCharArray()) {
            if(ch < '0' || ch > '9') {
                isNumber = false;
                break;
            }
        }
        if(isNumber) {
            startVerse = Integer.parseInt(textToBlank);
        } else {
            int firstLineBreak = text.indexOf("\n") + 1;
            int blank = text.indexOf(" ", firstLineBreak);
            textToBlank = text.substring(firstLineBreak, blank);
            startVerse = Integer.parseInt(textToBlank) - 1;
        }
        return startVerse;
    }

    private int findLastVerseInText() {
        String text = getActiveTextView().getText().toString();
        int lastBreak = text.lastIndexOf("\n");
        int blank = text.indexOf(" ", lastBreak);
        while(blank < 0 && lastBreak > 0) {
            lastBreak = text.lastIndexOf("\n", lastBreak - 1);
            blank = text.indexOf(" ", lastBreak);
        }
        String textToBlank = text.substring(lastBreak + 1, blank);
        return Integer.parseInt(textToBlank);
    }

    /**
     * Assume, that page index can be only next or previous. For other cases
     *
     * @param pageIndex index of selected page
     */
    private void selectPage(int pageIndex) {
        Log.v(TAG, "selectPage=" + pageIndex);

        CharSequence displayedText;
        if (mBoundaries.containsKey(pageIndex)) {
            // use existing boundaries
            Boundary boundary = mBoundaries.get(pageIndex);
            displayedText = mText.subSequence(boundary.start, boundary.end);
            TextView mTextView = getActiveTextView();
            mTextView.setText(displayedText);
            Log.v(TAG, "Existing[" + pageIndex + "]: ");
        } else if (mBoundaries.containsKey(pageIndex - 1)) {
            //calculate boundaries for new page (previous exists)
            Boundary previous = mBoundaries.get(pageIndex - 1);
            setTextWithCaching(pageIndex, previous.end, -1);
        } else {
            Log.d(TAG, "selectPage(" + pageIndex + "), values=[" + mBoundaries.keySet());
            parseAllPages(pageIndex == 0);
        }
    }

    private void setTextWithCaching(int pageIndex, int pageStartSymbol, int searchVerse) {
        CharSequence restText = mText.subSequence(pageStartSymbol, mText.length());

        TextView mTextView = getActiveTextView();
        mTextView.setText(restText);

        int height = mTextView.getHeight();
        int scrollY = mTextView.getScrollY();
        Layout layout = mTextView.getLayout();
        if(layout == null) {
            mTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    ViewTreeObserver obs = mTextView.getViewTreeObserver();
                    obs.removeOnGlobalLayoutListener(this);
                    setTextWithCaching(pageIndex, pageStartSymbol, searchVerse);
                    findVerses();
                }
            });
        } else {
            int firstVisibleLineNumber = layout.getLineForVertical(scrollY);
            int lastVisibleLineNumber = layout.getLineForVertical(height + scrollY);

            //check is latest line fully visible
            if (mTextView.getHeight() < layout.getLineBottom(lastVisibleLineNumber)) {
                lastVisibleLineNumber--;
            }

            int start = pageStartSymbol + mTextView.getLayout().getLineStart(firstVisibleLineNumber);
            int end = pageStartSymbol + mTextView.getLayout().getLineEnd(lastVisibleLineNumber);

            if (end >= mText.length()) {
                mLastPageIndex = pageIndex;
            }
            CharSequence displayedText = mText.subSequence(start, end);
            if(searchVerse > 0) {
                mFoundVerse = displayedText.toString().indexOf(String.valueOf(searchVerse)) >= 0;
            }
            //correct visible text
            mTextView.setText(displayedText);

            mBoundaries.put(pageIndex, new Boundary(start, end));
        }
    }

    public void next() {
        throwIfNotInitialized();
        if (isNextEnabled()) {
            flipPages();
            selectPage(++mPageIndex);
            findVerses();
        }
    }

    public void previous() {
        throwIfNotInitialized();
        if (isPreviousEnabled()) {
            flipPages();
            selectPage(--mPageIndex);
            findVerses();
        }
    }

    public void openVerse(int verse) {
        Log.d("openVerse", "verse: " + verse);
        getActiveTextView().setText("");
        if(getActiveTextView().getLayout() == null) {
            getActiveTextView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    ViewTreeObserver obs = getActiveTextView().getViewTreeObserver();
                    obs.removeOnGlobalLayoutListener(this);
                    openVerse(verse);
                }
            });
        } else {
            int i = 0;
            setTextWithCaching(i++, 0, verse);
            while (!mFoundVerse && mLastPageIndex < 0) {
                setTextWithCaching(i, mBoundaries.get(i - 1).end, verse);
                i++;
            }
            this.mPageIndex = i - 1;
            this.findVerses();
        }
    }

    public boolean isNextEnabled() {
        throwIfNotInitialized();
        return mPageIndex < mLastPageIndex || mLastPageIndex < 0;
    }

    public void openLastPage() {
        flipPages();
        selectPage(-1);
    }

    public void openFirstPage() {
        flipPages();
        selectPage(0);
    }

    public boolean isPreviousEnabled() {
        throwIfNotInitialized();
        return mPageIndex > 0;
    }

    void throwIfNotInitialized() {
        if (mText == null) {
            throw new IllegalStateException("Call onTextLoaded(String) first");
        }
    }

    private class Boundary {

        final int start;
        final int end;

        private Boundary(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    private void flipPages() {
        if(activeTextView == 1) {
            activeTextView = 2;
        } else {
            activeTextView = 1;
        }
    }

    public int getActivePage() {
        return this.activeTextView;
    }

    public interface PageUpdateListener {
        void updatePage(int startVerse, int endVerse);
    }
}
