package com.example.task_8;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    final static String URI_KEY = "URIKEY";

    SharedPreferences.Editor editor;

    boolean isEditable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText editText = (EditText) findViewById(R.id.link);
        editText.setInputType(InputType.TYPE_NULL);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPref.edit();

        try {
            if (!sharedPref.getString(URI_KEY, "").equals(""))
                editText.setText(sharedPref.getString(URI_KEY, ""));
        }catch (Exception ignored){}

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEditable) {
                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
                    fab.setImageResource(R.drawable.ic_done_white_24dp);
                    isEditable = !isEditable;
                }else {
                    editText.setInputType(InputType.TYPE_NULL);
                    fab.setImageResource(R.drawable.ic_edit_white_24dp);
                    editor.putString(URI_KEY, String.valueOf(editText.getText()));
                    editor.apply();
                    isEditable = !isEditable;
                }
            }
        });
    }


}
