package edu.temple.fourcolorgame;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TitleScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);

        ((Button)findViewById(R.id.puzzle_mode)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchColorPicker(0);
            }
        });

        ((Button)findViewById(R.id.vs_ai_mode)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchColorPicker(1);
            }
        });

        ((Button)findViewById(R.id.multiplayer_mode)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchColorPicker(2);
            }
        });

    }

    private void launchColorPicker(int gameType){
        Intent intent = new Intent(TitleScreen.this, ColorPicker.class);
        intent.putExtra(LoadingScreen.gameMode, (new Integer(gameType)).toString());
        startActivity(intent);

    }
}
