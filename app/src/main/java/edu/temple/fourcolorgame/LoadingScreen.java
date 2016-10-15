package edu.temple.fourcolorgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoadingScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        Intent received = getIntent();
        int gameMode = Integer.parseInt(received.getStringExtra("gameMode"));
        int firstColor = Integer.parseInt(received.getStringExtra("firstColor"));


    }
}
