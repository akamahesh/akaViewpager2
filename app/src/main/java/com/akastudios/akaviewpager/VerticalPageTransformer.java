package com.akastudios.akaviewpager;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class VerticalPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.87f;

    @Override
    public void transformPage(View view, float position) {

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);

        } else if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the left page
            view.setAlpha(1);
            // Counteract the default slide transition
            view.setTranslationX(-position);

            //set Y position to swipe in from top
            float yPosition = (position / 4) * view.getHeight();
            view.setTranslationY(yPosition);
            view.setScaleX(1);
            view.setScaleY(1);

        } else if (position <= 1) { // [0,1]
            view.setAlpha(1 - position);

            // Counteract the default slide transition
            view.setTranslationX(-position);
            float yPosition = -position * view.getHeight();
            view.setTranslationY(yPosition);

            // Scale the page down (between MIN_SCALE and 1)
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }
}