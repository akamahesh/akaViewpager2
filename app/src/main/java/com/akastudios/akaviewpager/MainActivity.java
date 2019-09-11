package com.akastudios.akaviewpager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //check this one only
        findViewById(R.id.btnVerticalViewpager2).setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this,VerticalViewpager2Activity.class));
        });


        findViewById(R.id.btnVerticalViewpager).setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this,VerticalViewpagerActivity.class));
        });

        findViewById(R.id.btnVerticalViewpagerLib).setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this,VerticalViewpagerActivityLib.class));
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
