package com.akastudios.akaviewpager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import java.util.Random;

public class ScrollingFragment2 extends Fragment {
    private static final String TAG = "ARTICLE_SWIPE";
    private static String POSITION = "position";
    float yAxis = 0;
    private String position;
    private float lastValue = 0f;
    private boolean atEnd;

    public static ScrollingFragment2 getInstance(String position) {
        ScrollingFragment2 fragment = new ScrollingFragment2();
        Bundle bundle = new Bundle();
        bundle.putString(POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
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

        return view;
    }


    private int getrandomColor() {
        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        return Color.rgb(r, g, b);
    }
}