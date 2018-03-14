package com.example.task_17;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener{

    private static final String TAG = "Gestures";
    private GestureDetectorCompat detector;

    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        detector = new GestureDetectorCompat(this,this);

        detector.setOnDoubleTapListener(this);

        info = findViewById(R.id.info);
        info.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (this.detector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        String msg = "onDown" + "\n";
        info.append(msg);
        Log.d(TAG,"onDown: " + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        String msg = "onFling" + "\n";
        info.append(msg);
        Log.d(TAG, "onFling: " + event1.toString() + event2.toString());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        String msg = "onLongPress" + "\n";
        info.append(msg);
        Log.d(TAG, "onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX,
                            float distanceY) {
        String msg = "onScroll" + "\n";
        info.append(msg);
        Log.d(TAG, "onScroll: " + event1.toString() + event2.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        String msg = "onShowPress" + "\n";
        info.append(msg);
        Log.d(TAG, "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        String msg = "onSingleTapUp" + "\n";
        info.append(msg);
        Log.d(TAG, "onSingleTapUp: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        String msg = "onDoubleTap" + "\n";
        info.append(msg);
        Log.d(TAG, "onDoubleTap: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        String msg = "onDoubleTapEvent" + "\n";
        info.append(msg);
        Log.d(TAG, "onDoubleTapEvent" + event.toString());
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        String msg = "onSingleTapConfirmed" + "\n";
        info.append(msg);
        Log.d(TAG, "onSingleTapConfirmed: " + event.toString());
        return true;
    }
}
