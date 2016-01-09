package com.kamtechs.ifcgeorgiatech;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarmain);
        getSupportActionBar().setTitle("GT IFC");

        final ImageView newsImage = (ImageView) findViewById(R.id.newsImage);


        Button newsButton = (Button) findViewById(R.id.newsButton);
        newsButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        newsImage.setBackgroundColor(Color.rgb(0, 0, 100));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        newsImage.setBackgroundColor(Color.argb(0,0,0,0));
                        goToNews();
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });
    }

    public void goToNews()
    {
        Intent intent = new Intent(MainActivity.this, NewsActivity.class);
        startActivity(intent);
    }
}
