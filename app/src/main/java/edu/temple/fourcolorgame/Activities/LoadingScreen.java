package edu.temple.fourcolorgame.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import edu.temple.fourcolorgame.R;
import edu.temple.fourcolorgame.Utils.Intents;

public class LoadingScreen extends AppCompatActivity {
    private int gameMode, mapSize, colorOne, colorTwo, colorThree, colorFour;
    private String difficulty;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        getGameData();
        switch (gameMode){
            case Intents.puzzle:
                intent = new Intent(LoadingScreen.this, PuzzleMode.class);
                break;
            default:
                intent = new Intent(LoadingScreen.this, TitleScreen.class);
        }

        addGameData();
        startActivity(intent);

    }

    private void getGameData(){
        Intent receivedIntent = getIntent();
        //Get Game Mode
        String data = receivedIntent.getStringExtra(Intents.gameMode);
        try{
            gameMode = Integer.parseInt(data);
        } catch (NumberFormatException e){
            Toast.makeText(LoadingScreen.this, "Invalid Game Mode", Toast.LENGTH_SHORT).show();
        }
        //Get Map Size
        try{
            mapSize = Integer.parseInt(receivedIntent.getStringExtra(Intents.mapChoice));
        } catch (NumberFormatException e){
            Toast.makeText(LoadingScreen.this, "Invalid Map Size", Toast.LENGTH_SHORT).show();
        }
        //Get Comp Difficulty
        if(gameMode == Intents.comp){
            difficulty = receivedIntent.getStringExtra(Intents.compPlayerSkill);
        }
        //Get Colors
        try {
            colorOne = Integer.parseInt(receivedIntent.getStringExtra(Intents.firstColor));
            colorTwo = Integer.parseInt(receivedIntent.getStringExtra(Intents.secondColor));
            colorThree = Integer.parseInt(receivedIntent.getStringExtra(Intents.thirdColor));
            colorFour = Integer.parseInt(receivedIntent.getStringExtra(Intents.fourthColor));
        } catch (NumberFormatException e){
            Toast.makeText(LoadingScreen.this, "Invalid Color", Toast.LENGTH_SHORT).show();
        }


    }

    private void addGameData(){
        String mode = (new Integer(gameMode)).toString();
        intent.putExtra(Intents.gameMode, mode);
        intent.putExtra(Intents.mapChoice, (new Integer(mapSize)).toString());
        if(gameMode== Intents.comp){
            intent.putExtra(Intents.compPlayerSkill, difficulty);
        }
        intent.putExtra(Intents.firstColor, (new Integer(colorOne)).toString());
        intent.putExtra(Intents.secondColor, (new Integer(colorTwo)).toString());
        intent.putExtra(Intents.thirdColor, (new Integer(colorThree)).toString());
        intent.putExtra(Intents.fourthColor, (new Integer(colorFour)).toString());
    }
}
