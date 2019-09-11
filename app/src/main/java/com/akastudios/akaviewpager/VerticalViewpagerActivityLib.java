package com.akastudios.akaviewpager;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class VerticalViewpagerActivityLib extends AppCompatActivity implements ArticleActionListener {
    private List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_viewpager_lib);

        VerticalViewPager viewPager = findViewById(R.id.viewpager_old);
        SectionPagerAdapter sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());

        mList = new ArrayList<>();
        prepareList(10);

        viewPager.setPageTransformer(true, new VerticalPageTransformer());
        viewPager.setAdapter(sectionPagerAdapter);
    }

    private void prepareList(int count) {
        for (int i = 0; i < count; i++) {
            mList.add(String.valueOf(i));
        }
    }

    @Override
    public void fakeDrag(float drag) {

    }

    @Override
    public void beginFakeDrag() {

    }

    @Override
    public void endFakeDrag() {

    }


    class SectionPagerAdapter extends FragmentStatePagerAdapter {


        public SectionPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return ScrollingFragment.getInstance(mList.get(position));
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }
}
