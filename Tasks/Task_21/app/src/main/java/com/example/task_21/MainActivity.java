package com.example.task_21;

import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView info;
    ImageView img;
    Button prev, next;
    ArrayList<String> imgPaths;

    int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgPaths = new ArrayList<>();

        info = findViewById(R.id.info);

        img = findViewById(R.id.img);

        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);

        prev.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            File directory = new File(getCameraPath());
            imgPaths = explorePaths(directory);
            updatePhoto(Uri.parse(imgPaths.get(current)));
        } catch (Exception e) {
            Toast.makeText(this, "wrong path: " + getCameraPath(), Toast.LENGTH_SHORT).show();
        }
    }

    private static String getCameraPath() {
        return Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/Camera/";
    }

    private ArrayList<String> explorePaths(File dir) {
        ArrayList<String> imagesFinded = new ArrayList<String>();
        for (File f : dir.listFiles()) {
            if (!f.isDirectory()) {
                String fileExt = getFileExt(f.getAbsolutePath());
                if (fileExt.equals("png") || fileExt.equals("jpg") || fileExt.equals("jpeg")) {
                    Log.d("myLogs", "Файл найден " + f.getAbsolutePath());
                    imagesFinded.add(f.getAbsolutePath());
                }
            }
        }
        return imagesFinded;
    }

    private String getFileExt(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    public void updatePhoto(Uri uri) {
        try {
            info.setText((current + 1) + "/" + imgPaths.size());
            img.setImageURI(uri);
        } catch (Exception e) {
            info.setText("Ошибка загрузки файла");
        }
    }

    public void next() {
        if (current + 1 < imgPaths.size() && imgPaths.size() > 0) {
            current++;
            updatePhoto(Uri.parse(imgPaths.get(current)));
        }
    }

    public void prev() {
        if (current > 0 && imgPaths.size() > 0) {
            --current;
            updatePhoto(Uri.parse(imgPaths.get(current)));
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.prev) {
            prev();
        } else if (view.getId() == R.id.next) {
            next();
        }
    }
}
