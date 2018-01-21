package com.example.task_13;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private VideoView vv;
    private MediaController mediacontroller;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progrss);
        vv = findViewById(R.id.vv);

        mediacontroller = new MediaController(this);
        mediacontroller.setAnchorView(vv);

        Uri video = Uri.parse("android.resource://" + getPackageName() + "/"
                + R.raw.vzletelnaakvataketrim);

        progressBar.setVisibility(View.VISIBLE);
        vv.setMediaController(mediacontroller);
        vv.setVideoURI(video);
        vv.requestFocus();
        vv.start();

        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
