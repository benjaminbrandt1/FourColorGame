package edu.temple.fourcolorgame.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import edu.temple.fourcolorgame.R;
import edu.temple.fourcolorgame.Utils.Intents;

public class PuzzleMode extends AppCompatActivity {
    private int gameMode, mapSize;
    private int[] colors;
    private Button colorOne, colorTwo, colorThree, colorFour;
    private int currentColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_mode);

        colors = new int[4];
        getGameData();

        colorOne = (Button)findViewById(R.id.color_one);
        colorTwo = (Button)findViewById(R.id.color_two);
        colorThree = (Button)findViewById(R.id.color_three);
        colorFour = (Button)findViewById(R.id.color_four);

        setUpColorButton(0, colorOne);
        setUpColorButton(1, colorTwo);
        setUpColorButton(2, colorThree);
        setUpColorButton(3, colorFour);

    }

    private void getGameData(){
        Intent receivedIntent = getIntent();
        //Get Game Mode
        String data = receivedIntent.getStringExtra(Intents.gameMode);
        try{
            gameMode = Integer.parseInt(data);
        } catch (NumberFormatException e){
            Toast.makeText(PuzzleMode.this, "Invalid Game Mode", Toast.LENGTH_SHORT).show();
        }
        //Get Map Size
        try{
            mapSize = Integer.parseInt(receivedIntent.getStringExtra(Intents.mapChoice));
        } catch (NumberFormatException e){
            Toast.makeText(PuzzleMode.this, "Invalid Map Size", Toast.LENGTH_SHORT).show();
        }
        //Get Colors
        try {
            colors[0] = Integer.parseInt(receivedIntent.getStringExtra(Intents.firstColor));
            colors[1] = Integer.parseInt(receivedIntent.getStringExtra(Intents.secondColor));
            colors[2] = Integer.parseInt(receivedIntent.getStringExtra(Intents.thirdColor));
            colors[3] = Integer.parseInt(receivedIntent.getStringExtra(Intents.fourthColor));
        } catch (NumberFormatException e){
            Toast.makeText(PuzzleMode.this, "Invalid Color", Toast.LENGTH_SHORT).show();
        }

    }

    private void setUpColorButton(final int index, Button button){
        button.setBackgroundColor(colors[index]);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentColor = colors[index];
            }
        });
    }
}
