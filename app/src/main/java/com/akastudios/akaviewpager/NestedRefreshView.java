package com.akastudios.akaviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent;
import androidx.core.widget.NestedScrollView;

public class NestedRefreshView  extends NestedScrollView implements NestedScrollingParent {

    private View nestedScrollTarget;
    private boolean nestedScrollTargetIsBeingDragged = false;
    private boolean nestedScrollTargetWasUnableToScroll = false;
    private boolean skipsTouchInterception = false;

    public NestedRefreshView(@NonNull Context context) {
        super(context);
    }

    public NestedRefreshView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedRefreshView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent( MotionEvent ev) {
        boolean temporarilySkipsInterception = nestedScrollTarget != null;
        if (temporarilySkipsInterception) {
            // If a descendent view is scrolling we set a flag to temporarily skip our onInterceptTouchEvent implementation
            skipsTouchInterception = true;
        }

        // First dispatch, potentially skipping our onInterceptTouchEvent
        boolean handled = super.dispatchTouchEvent(ev);

        if (temporarilySkipsInterception) {
            skipsTouchInterception = false;

            // If the first dispatch yielded no result or we noticed that the descendent view is unable to scroll in the
            // direction the user is scrolling, we dispatch once more but without skipping our onInterceptTouchEvent.
            // Note that RecyclerView automatically cancels active touches of all its descendents once it starts scrolling
            // so we don't have to do that.
            if (!handled || nestedScrollTargetWasUnableToScroll) {
                handled = super.dispatchTouchEvent(ev);
            }
        }

        return handled;
    }


    // Skips RecyclerView's onInterceptTouchEvent if requested
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e){
        return !skipsTouchInterception && super.onInterceptTouchEvent(e);
    }


    @Override
    public void onNestedScroll(View target,int dxConsumed, int dyConsumed, int dxUnconsumed,int dyUnconsumed) {
        if (target == nestedScrollTarget && !nestedScrollTargetIsBeingDragged) {
            if (dyConsumed != 0) {
                // The descendent was actually scrolled, so we won't bother it any longer.
                // It will receive all future events until it finished scrolling.
                nestedScrollTargetIsBeingDragged = true;
                nestedScrollTargetWasUnableToScroll = false;
            }
            else if (dyConsumed == 0 && dyUnconsumed != 0) {
                // The descendent tried scrolling in response to touch movements but was not able to do so.
                // We remember that in order to allow RecyclerView to take over scrolling.
                nestedScrollTargetWasUnableToScroll = true;
                if(target.getParent()!=null){
                    target.getParent().requestDisallowInterceptTouchEvent(false);
                }
            }
        }
    }


    @Override
    public void onNestedScrollAccepted(View child, View target,int axes) {
        if (axes !=0 && View.SCROLL_AXIS_VERTICAL != 0) {
            // A descendent started scrolling, so we'll observe it.
            nestedScrollTarget = target;
            nestedScrollTargetIsBeingDragged = false;
            nestedScrollTargetWasUnableToScroll = false;
        }

        super.onNestedScrollAccepted(child, target, axes);
    }


    // We only support vertical scrolling.
    @Override
    public boolean onStartNestedScroll(View child,View target,int nestedScrollAxes){
        return (nestedScrollAxes != 0 && View.SCROLL_AXIS_VERTICAL != 0);
    }



    @Override
    public void onStopNestedScroll( View child) {
        // The descendent finished scrolling. Clean up!
        nestedScrollTarget = null;
        nestedScrollTargetIsBeingDragged = false;
        nestedScrollTargetWasUnableToScroll = false;
    }


}
