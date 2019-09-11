package com.akastudios.akaviewpager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import java.util.Random;

public class ScrollingFragment extends Fragment {
    private static final String TAG = "ARTICLE_SWIPE";
    private static String POSITION = "position";
    float yAxis = 0;
    private String position;
    private ArticleActionListener articleActionListener;
    private float lastValue = 0f;
    private boolean atEnd;

    public static ScrollingFragment getInstance(String position) {
        ScrollingFragment fragment = new ScrollingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.articleActionListener = (ArticleActionListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            position = bundle.getString(POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scrolling, container, false);
        TextView tvPosition = view.findViewById(R.id.tvPosition);
        tvPosition.setText(String.format("Position %s ", position));
        int color = getrandomColor();
        NestedScrollView scrollView = view.findViewById(R.id.vParent);
        scrollView.setBackgroundColor(color);
        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            // at bottom
            atEnd = !v.canScrollVertically(1);
        });


        scrollView.setOnTouchListener((view1, motionEvent) -> {
            Log.v(TAG, "Motion event y axis " + (int) motionEvent.getY() + " computeVerticalScrollOffset "+scrollView.computeVerticalScrollOffset()
                    +" MotionEvent " + motionEvent.toString());


//
            if (atEnd) {
                // bottom of scroll view

                //that fling
//                if (motionEvent.getY() < yAxis) {
//                    //that flig
//                    yAxis = motionEvent.getY();
//                    return false;
//                }
                return handleOnTouchEvent(motionEvent);
            }
            return false;
        });
        return view;
    }

    private boolean handleOnTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastValue = getValue(event);
                articleActionListener.beginFakeDrag();
                break;

            case MotionEvent.ACTION_MOVE:
                float value = getValue(event);
                float delta = value - lastValue;
                if(delta>0){
                    return false;
                }
                articleActionListener.fakeDrag(delta);
                lastValue = value;
                break;
            case MotionEvent.ACTION_CANCEL:
                atEnd = false;
                articleActionListener.endFakeDrag();
                return false;
            case MotionEvent.ACTION_UP:
                articleActionListener.endFakeDrag();
                return false;
            case MotionEvent.ACTION_SCROLL:
                Log.v(TAG,"ActionUp ");
                return false;
            case MotionEvent.ACTION_POINTER_UP:
                Log.v(TAG,"ACTION_POINTER_UP ");
                return false;
            case MotionEvent.ACTION_OUTSIDE:
                Log.v(TAG,"ACTION_OUTSIDE ");
                return false;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.v(TAG,"ACTION_POINTER_DOWN ");
                return false;

        }
        return true;
    }

    private float getValue(MotionEvent event) {
        Log.v(TAG, ("Motion event " + " getY " + event.getY() + " getYPrecision " + event.getYPrecision() + " getRawY " + event.getRawY() + " result " + event.getYPrecision() * event.getY()));
        return event.getY();
    }


    private int getrandomColor() {
        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        return Color.rgb(r, g, b);
    }
}