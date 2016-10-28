package edu.temple.fourcolorgame;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TitleScreen extends AppCompatActivity {

    private Button puzzle, comp, multi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);

        puzzle = (Button)findViewById(R.id.puzzle_mode);
        puzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchColorPicker(0);
            }
        });

        comp = (Button)findViewById(R.id.vs_ai_mode);
        comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchColorPicker(1);
            }
        });

        multi=(Button)findViewById(R.id.multiplayer_mode);
        multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchColorPicker(2);
            }
        });

        findViewById(R.id.help_button).setOnTouchListener(new HelpButtonListener(TitleScreen.this, R.string.help_title_screen));

    }

    private void launchColorPicker(int gameType){
        Intent intent = new Intent(TitleScreen.this, GameDetails.class);
        intent.putExtra(LoadingScreen.gameMode, (new Integer(gameType)).toString());
        startActivity(intent);

    }

    @Override
    public void onPause(){
        super.onPause();
        puzzle.setVisibility(View.INVISIBLE);
        comp.setVisibility(View.INVISIBLE);
        multi.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onResume(){
        super.onResume();
        puzzle.setVisibility(View.VISIBLE);
        comp.setVisibility(View.VISIBLE);
        multi.setVisibility(View.VISIBLE);
    }
}
