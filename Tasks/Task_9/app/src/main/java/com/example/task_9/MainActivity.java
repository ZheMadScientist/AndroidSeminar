package com.example.task_9;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final int MENU_COLOR_RED = 1;
    private static final int MENU_COLOR_GREEN = 2;
    private static final int MENU_COLOR_BLUE = 3;


    SharedPreferences sharedPreferences;

    CardView card;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        card = findViewById(R.id.card_view);
        image = findViewById(R.id.image_card);

        registerForContextMenu(card);

        if(!sharedPreferences.getBoolean("check_card", true))
            card.setVisibility(View.GONE);
        else card.setVisibility(View.VISIBLE);

        if(!sharedPreferences.getBoolean("switch_image", true))
            image.setVisibility(View.GONE);
        else image.setVisibility(View.VISIBLE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedPreferences.getBoolean("check_snack", false))
                    Snackbar.make(view, "Snackbar", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!sharedPreferences.getBoolean("check_card", true))
            card.setVisibility(View.GONE);
        else card.setVisibility(View.VISIBLE);

        if(!sharedPreferences.getBoolean("switch_image", true))
            image.setVisibility(View.GONE);
        else image.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);

        switch (item.getItemId()){
            case MENU_COLOR_RED:
                image.setBackgroundResource(R.color.red);
                break;
            case MENU_COLOR_GREEN:
                image.setBackgroundResource(R.color.green);
                break;
            case MENU_COLOR_BLUE:
                image.setBackgroundResource(R.color.blue);
                break;
        }

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        switch (v.getId()) {
            case R.id.card_view:
                menu.add(0, MENU_COLOR_RED, 0, "Red");
                menu.add(0, MENU_COLOR_GREEN, 0, "Green");
                menu.add(0, MENU_COLOR_BLUE, 0, "Blue");
                break;

        }
    }
}
