package com.akastudios.akaviewpager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VerticalViewpager2Activity extends AppCompatActivity{
    private ViewPager2 viewPager;
    private List<String> mList;
    private final String TAG = "Vertical_Viewpager";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_viewpager_2);
        viewPager = findViewById(R.id.viewpager);

        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager.setNestedScrollingEnabled(true);

        mList = new ArrayList<>();
        prepareList(100);
//        SectionPagerAdapter sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager(),lifecycle);
        Adapter sectionPagerAdapter = new Adapter(this,mList);
        viewPager.setAdapter(sectionPagerAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                viewPager.endFakeDrag();
            }
        });
    }



    private void prepareList(int count) {
        for (int i = 0; i<count; i++){
            mList.add(String.valueOf(i));
        }
    }

    private Lifecycle lifecycle = new Lifecycle() {
        @Override
        public void addObserver(@NonNull LifecycleObserver observer) {
            Log.v(TAG," addObserver "+observer.toString());
        }

        @Override
        public void removeObserver(@NonNull LifecycleObserver observer) {
            Log.v(TAG," removeObserver "+observer.toString());
        }

        @NonNull
        @Override
        public State getCurrentState() {
            return null;
        }
    };


    class SectionPagerAdapter extends FragmentStateAdapter{

        SectionPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return ScrollingFragment2.getInstance(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }




    class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{

        private final Context context;
        private final List<String> mList;

        public Adapter(Context context, List<String> mList){
            this.context = context;
            this.mList = mList;
        }
        @NonNull
        @Override
        public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new Adapter.MyViewHolder(inflater.inflate(R.layout.item_list_view, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
            holder.rootContainer.setBackgroundColor(getrandomColor());
        }

        private int getrandomColor() {
            Random rand = new Random();
            int r = rand.nextInt(255);
            int g = rand.nextInt(255);
            int b = rand.nextInt(255);
            return Color.rgb(r,g,b);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            private View rootContainer;
            private View nestedScrollView;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                rootContainer = itemView.findViewById(R.id.rootContainer);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                rootContainer.setLayoutParams(lp);
            }
        }
    }
}
