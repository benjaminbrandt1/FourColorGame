package edu.temple.fourcolorgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class LoadingScreen extends AppCompatActivity {
    public static final String gameMode = "gameMode";
    public static final String firstColor = "firstColor";
    public static final String secondColor = "secondColor";
    public static final String thirdColor = "thirdColor";
    public static final String fourthColor = "fourthColor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        Intent received = getIntent();
        int mode = Integer.parseInt(received.getStringExtra(gameMode));
        int colorOne = Integer.parseInt(received.getStringExtra(firstColor));
        int colorTwo = Integer.parseInt(received.getStringExtra(secondColor));
        int colorThree = Integer.parseInt(received.getStringExtra(thirdColor));
        int colorFour = Integer.parseInt(received.getStringExtra(fourthColor));



    }
}
