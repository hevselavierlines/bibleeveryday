package tk.hevselavierlines.bibleeveryday.ui.main;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.Arrays;

public class PageSwipeTouchListener implements View.OnTouchListener {

    public interface PageChangeListener {
        void previousPage();
        void nextPage();
        void showMenu();
    }

    private GestureDetector gestureDetector;
    private View mainTextView;
    private PageChangeListener pageChangeListener;

    public PageSwipeTouchListener(PageChangeListener listener, View leftArea, View rightArea) {
        this.pageChangeListener = listener;
        gestureDetector = new GestureDetector(leftArea.getContext(), new GestureListener());
        this.mainTextView = leftArea;
        leftArea.setOnTouchListener(this);
        rightArea.setOnTouchListener(this);
    }

    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            onClick(e);
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            onDoubleClick();
            return super.onDoubleTap(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            onLongClick();
            super.onLongPress(e);
        }

        // Determines the fling velocity and then fires the appropriate swipe event accordingly
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeDown();
                        } else {
                            onSwipeUp();
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void onSwipeRight() {
        pageChangeListener.previousPage();
    }

    public void onSwipeLeft() {
        pageChangeListener.nextPage();
    }

    public void onSwipeUp() {
        pageChangeListener.showMenu();
    }

    public void onSwipeDown() {
    }

    public void onClick(MotionEvent motionEvent) {

    }

    public void onDoubleClick() {

    }

    public void onLongClick() {
    }
}
