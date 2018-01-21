package com.example.task_12;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        onSoundPlay listener = new onSoundPlay();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(listener);

        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setOnClickListener(listener);
    }

    class onSoundPlay implements View.OnClickListener{

        MediaPlayer big_shaq = MediaPlayer.create(MainActivity.this, R.raw.big_shaq);
        MediaPlayer skraa = MediaPlayer.create(MainActivity.this, R.raw.the_thing_go_skraa);

        boolean stopShaq = false;
        boolean stopSkraa = false;

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.fab){
                if(stopShaq){
                    big_shaq.stop();
                    big_shaq.release();
                    big_shaq = MediaPlayer.create(MainActivity.this, R.raw.big_shaq);
                } else {
                    big_shaq.start();
                }
                stopShaq = !stopShaq;
            } else if(view.getId() == R.id.fab1){
                if(stopSkraa){
                    skraa.stop();
                    skraa.release();
                    skraa = MediaPlayer.create(MainActivity.this, R.raw.the_thing_go_skraa);
                } else {
                    skraa.start();
                }
                stopSkraa = !stopSkraa;
            }
        }
    }

}
