package com.example.task_14;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, Camera.PictureCallback, Camera.PreviewCallback, Camera.AutoFocusCallback
{
    private Camera camera;
    private SurfaceHolder surfaceHolder;
    private SurfaceView preview;
    ImageView image;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preview = findViewById(R.id.surface_view);

        surfaceHolder = preview.getHolder();
        surfaceHolder.addCallback(this);

        image = findViewById(R.id.image);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        camera = Camera.open();
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        if (camera != null)
        {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        try
        {
            camera.setPreviewDisplay(holder);
            camera.setPreviewCallback(this);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        camera.startPreview();

        camera.autoFocus(this);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
    }

    @Override
    public void onPictureTaken(byte[] paramArrayOfByte, Camera paramCamera)
    {
        preview.setVisibility(View.INVISIBLE);
        image.setImageBitmap(BitmapFactory.decodeByteArray(paramArrayOfByte, 0 , paramArrayOfByte.length));
    }

    @Override
    public void onAutoFocus(boolean paramBoolean, Camera paramCamera)
    {
        if (paramBoolean)
        {
            paramCamera.takePicture(null, null, null, this);
        }
    }

    @Override
    public void onPreviewFrame(byte[] paramArrayOfByte, Camera paramCamera)
    {
    }
}
