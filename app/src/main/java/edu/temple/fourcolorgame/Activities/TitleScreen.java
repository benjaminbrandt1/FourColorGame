package edu.temple.fourcolorgame.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.temple.fourcolorgame.Utils.GameInformation;
import edu.temple.fourcolorgame.Utils.HelpButtonListener;
import edu.temple.fourcolorgame.R;
import edu.temple.fourcolorgame.Utils.Intents;

/*
First screen the user sees
Provides game mode options and information on how to play each mode
 */
public class TitleScreen extends AppCompatActivity {

    private Button puzzle, comp, multi;
    private GameInformation gameInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);

        gameInformation = new GameInformation();

        puzzle = (Button)findViewById(R.id.puzzle_mode);
        puzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchGameDetails(Intents.puzzle);
            }
        });

        comp = (Button)findViewById(R.id.vs_ai_mode);
        comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchGameDetails(Intents.comp);
            }
        });

        multi=(Button)findViewById(R.id.multiplayer_mode);
        multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchGameDetails(Intents.multi);
            }
        });

        findViewById(R.id.help_button).setOnTouchListener
                (new HelpButtonListener("How to Play", getResources().getString(R.string.help_title_screen), TitleScreen.this));

    }

    //Start the GameDetails activity
    private void launchGameDetails(int gameType){
        gameInformation.setGameMode(gameType);
        Intent intent = new Intent(TitleScreen.this, GameDetails.class);
        intent.putExtra(Intents.gameInformation, gameInformation);
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
