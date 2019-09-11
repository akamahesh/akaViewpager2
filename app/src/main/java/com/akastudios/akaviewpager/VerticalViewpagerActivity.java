package com.akastudios.akaviewpager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VerticalViewpagerActivity extends AppCompatActivity implements ArticleActionListener{
    private ViewPager2 viewPager;
    private List<String> mList;
    private final String TAG = "Vertical_Viewpager";
    private SeekBar seekBar;
    private int maxOffset;
    private int currentOffset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_viewpager);
        viewPager = findViewById(R.id.viewpager);
        seekBar = findViewById(R.id.seekbar);
        seekBar.setVisibility(View.VISIBLE);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if(viewPager.isFakeDragging()){
                    int offset = (int) ((maxOffset/100.0)*progress);
                    int dragby = -1 * (offset - currentOffset);
                    Log.v(TAG," progress "+progress +" offset "+offset+" dragby "+dragby);
                    viewPager.fakeDragBy(dragby);
                    currentOffset = offset;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                maxOffset = viewPager.getWidth();
                viewPager.beginFakeDrag();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                viewPager.endFakeDrag();
                currentOffset = 0;
                seekBar.setProgress(0);
            }
        });

        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager.setUserInputEnabled(false);

        mList = new ArrayList<>();
        prepareList(100);
        SectionPagerAdapter sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager(),lifecycle);
        viewPager.setAdapter(sectionPagerAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                viewPager.endFakeDrag();
                currentOffset = 0;
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

    @Override
    public void fakeDrag(float drag) {
        if(viewPager.isFakeDragging()){
            int offset = (int) (drag);
            if(offset<=0)
                viewPager.fakeDragBy(offset);
        }
    }

    @Override
    public void beginFakeDrag() {
        maxOffset = viewPager.getHeight();
        viewPager.beginFakeDrag();
    }

    @Override
    public void endFakeDrag() {
        currentOffset = 0;
        viewPager.endFakeDrag();
    }


    class SectionPagerAdapter extends FragmentStateAdapter{

        SectionPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return ScrollingFragment.getInstance(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }




/*    class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{

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
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                rootContainer = itemView.findViewById(R.id.rootContainer);
            }
        }
    }*/
}
