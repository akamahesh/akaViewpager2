package com.akastudios.akaviewpager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListingFragment extends Fragment {
    private static String POSITION = "position";
    private String position;
    private List<String> mList;

    public static ListingFragment getInstance(String position){
        ListingFragment fragment = new ListingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(POSITION,position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            Bundle bundle = getArguments();
            position = bundle.getString(POSITION);
        }
        mList = new ArrayList<>();
        mList.add("1");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listing, container, false);
        int color = getrandomColor();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setBackgroundColor(color);
        Adapter adapter = new Adapter(getContext(),mList);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(true);

        return view;
    }

    private int getrandomColor() {
        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        return Color.rgb(r,g,b);
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
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new MyViewHolder(inflater.inflate(R.layout.item_list_view, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}