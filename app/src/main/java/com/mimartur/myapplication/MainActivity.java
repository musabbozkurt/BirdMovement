package com.mimartur.myapplication;

import android.graphics.Point;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    // Screen Size
    private int screenWidth;
    private int screenHeight;

    // Images
    private ImageView arrowUp;
    private ImageView arrowDown;
    private ImageView arrowRight;
    private ImageView arrowLeft;

    // Button
    private Button pauseBtn;

    // Position
    private float arrowUpX;
    private float arrowUpY;
    private float arrowDownX;
    private float arrowDownY;
    private float arrowRightX;
    private float arrowRightY;
    private float arrowLeftX;
    private float arrowLeftY;

    // Initialize Class
    private Handler handler = new Handler();
    private Timer timer = new Timer();

    // Status Check
    private boolean pause_flg = false;
    private boolean start_flg = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrowUp = (ImageView)findViewById(R.id.arrowUp);
        arrowDown = (ImageView)findViewById(R.id.arrowDown);
        arrowRight = (ImageView)findViewById(R.id.arrowRight);
        arrowLeft = (ImageView)findViewById(R.id.arrowLeft);

        pauseBtn = (Button)findViewById(R.id.pauseBtn);

        // Get Screen Size.
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        // Move to out of screen.
        arrowUp.setX(-80.0f);
        arrowUp.setY(-80.0f);
        arrowDown.setX(-80.0f);
        arrowDown.setY(screenHeight + 80.0f);
        arrowRight.setX(screenWidth + 80.0f);
        arrowRight.setY(-80.0f);
        arrowLeft.setX(-80.0f);
        arrowLeft.setY(-80.0f);


        // Start the timer.
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        changePos();
                    }
                });
            }
        }, 0, 20);

    }

    public void changePos() {
        // Up
        arrowUpY -= 10;
        if (arrowUp.getY() + arrowUp.getHeight() < 0) {
            arrowUpX = (float)Math.floor(Math.random() * (screenWidth - arrowUp.getWidth()));
            arrowUpY = screenHeight + 100.0f;
        }
        arrowUp.setX(arrowUpX);
        arrowUp.setY(arrowUpY);

        // Down
        arrowDownY += 10;
        if (arrowDown.getY() > screenHeight) {
            arrowDownX = (float)Math.floor(Math.random() * (screenWidth - arrowDown.getWidth()));
            arrowDownY = -100.0f;
        }
        arrowDown.setX(arrowDownX);
        arrowDown.setY(arrowDownY);

        // Right
        arrowRightX += 10;
        if (arrowRight.getX() > screenWidth) {
            arrowRightX = -100.0f;
            arrowRightY = (float)Math.floor(Math.random() * (screenHeight - arrowRight.getHeight()));
        }
        arrowRight.setX(arrowRightX);
        arrowRight.setY(arrowRightY);

        // Left
        arrowLeftX -= 10;
        if (arrowLeft.getX() + arrowLeft.getWidth() < 0) {
            arrowLeftX = screenWidth + 100.0f;
            arrowLeftY = (float)Math.floor(Math.random() * (screenHeight - arrowLeft.getHeight()));
        }
        arrowLeft.setX(arrowLeftX);
        arrowLeft.setY(arrowLeftY);

    }


    public void pausePushed(View view) {

        if (pause_flg == false) {

            pause_flg = true;

            // Stop the timer.
            timer.cancel();
            timer = null;

            // Change Button Text.
            pauseBtn.setText("START");


        } else {

            pause_flg = false;

            // Change Button Text.
            pauseBtn.setText("PAUSE");

            // Create and Start the timer.
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }, 0, 20);

        }

    }
    public boolean onTouchEvent(MotionEvent me) {

            if (me.getAction() == MotionEvent.ACTION_DOWN) {
                pause_flg = true;

            } else if (me.getAction() == MotionEvent.ACTION_UP) {
                pause_flg = false;
            }

        return true;
    }
    
}
