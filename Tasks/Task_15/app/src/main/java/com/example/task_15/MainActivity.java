package com.example.task_15;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final String SAVED_STATE_KEY = "savedStateKey";

    FloatingActionButton fab;
    TextView tv;
    SharedPreferences sharedPreferences;
    Button button;
    EditText editText;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        tv = findViewById(R.id.info);

        button = findViewById(R.id.button);
        button.setOnClickListener(this);

        editText = findViewById(R.id.editText);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        tv.setText(sharedPreferences.getString(SAVED_STATE_KEY, ""));

        dbHelper = new DBHelper(this);
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

        if (id == R.id.action_clear_db) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("delete from mytable;");
            db.execSQL("delete from sqlite_sequence where name='mytable';");
            db.close();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == fab.getId()){

            ContentValues cv = new ContentValues();

            String text = editText.getText().toString();

            SQLiteDatabase db = dbHelper.getWritableDatabase();

            cv.put("info", text);
            // вставляем запись и получаем ее ID
            long rowID = db.insert("mytable", null, cv);
            Log.d("onClick", "row inserted, ID = " + rowID);

            Toast.makeText(MainActivity.this, "Запись добавлена", Toast.LENGTH_SHORT).show();
        } else if(view.getId() == button.getId()){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor c = db.query("mytable", null, null, null, null, null, null);

            StringBuilder tmp = new StringBuilder();
            if (c.moveToFirst()) {

                int idColIndex = c.getColumnIndex("id");
                int infoColIndex = c.getColumnIndex("info");

                do {
                    tmp.append("ID = ")
                            .append(c.getInt(idColIndex))
                            .append(", info = ")
                            .append(c.getString(infoColIndex))
                            .append("\n");
                } while (c.moveToNext());
            }
            c.close();

            Log.d("Records", tmp.toString());
            tv.setText(tmp.toString());

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(SAVED_STATE_KEY, tmp.toString());
            editor.apply();
        }
    }
}
