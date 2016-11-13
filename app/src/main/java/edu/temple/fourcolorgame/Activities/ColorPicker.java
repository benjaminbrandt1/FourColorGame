package edu.temple.fourcolorgame.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.temple.fourcolorgame.Utils.BackArrowListener;
import edu.temple.fourcolorgame.Utils.ColorAdapter;
import edu.temple.fourcolorgame.Utils.HelpButtonListener;
import edu.temple.fourcolorgame.R;
import edu.temple.fourcolorgame.Utils.Intents;

/**
 * Created by Ben on 10/14/2016.
 */

public class ColorPicker extends AppCompatActivity {
    private int gameMode;
    private int mapSize;
    private String difficulty;
    private Intent intent;
    private ArrayList<Integer> colorChoices;
    private Integer[] userPicks;
    private int[] positions;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        startButton = (Button)findViewById(R.id.color_picker_start);

        userPicks = new Integer[4];
        positions = new int[4];

        getGameData();

        setTextViews();

       findViewById(R.id.help_button).setOnTouchListener(new HelpButtonListener(ColorPicker.this, R.string.help_color_picker));

        findViewById(R.id.back_arrow).setOnTouchListener(new BackArrowListener(ColorPicker.this));

        intent  = new Intent(ColorPicker.this, LoadingScreen.class);

        Resources res = ColorPicker.this.getResources();
        colorChoices = getIntegerArray(res.getIntArray(R.array.colorChoices));

        ColorAdapter adapter = new ColorAdapter(ColorPicker.this, colorChoices);

        Spinner firstSpinner = setUpSpinners(R.id.first_color_choice, 0, adapter);
        Spinner secondSpinner =  setUpSpinners(R.id.second_color_choice, 1, adapter);
        Spinner thirdSpinner = setUpSpinners(R.id.third_color_choice, 2, adapter);
        Spinner fourthSpinner = setUpSpinners(R.id.fourth_color_choice, 3, adapter);



        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mode = (new Integer(gameMode)).toString();
                intent.putExtra(Intents.gameMode, mode);
                intent.putExtra(Intents.mapChoice, (new Integer(mapSize)).toString());
                if(gameMode== Intents.comp){
                    intent.putExtra(Intents.compPlayerSkill, difficulty);
                }
                intent.putExtra(Intents.firstColor, userPicks[0].toString());
                intent.putExtra(Intents.secondColor, userPicks[1].toString());
                intent.putExtra(Intents.thirdColor, userPicks[2].toString());
                intent.putExtra(Intents.fourthColor, userPicks[3].toString());
                startActivity(intent);
            }
        });

    }

    private ArrayList<Integer> getIntegerArray(int[] array){
        ArrayList<Integer> res = new ArrayList<>();
        for(int i = 0; i<array.length; i++){
            res.add(new Integer(array[i]));
        }
        return res;
    }

    private Spinner setUpSpinners(int viewId, final int index, ColorAdapter adapter){
        final Spinner spinner = (Spinner)findViewById(viewId);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    Integer colorSelected = (Integer) parent.getItemAtPosition(position);
                    if(isUnique(colorSelected, index)) {
                        userPicks[index] = colorSelected;
                        positions[index] = position;
                    } else {
                        Toast.makeText(ColorPicker.this, "Colors must be unique!", Toast.LENGTH_SHORT).show();
                        spinner.setSelection(positions[index]);
                    }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner.setSelection(index);

        return spinner;


    }

    private void getGameData(){
        Intent receivedIntent = getIntent();
        //Get Game Mode
        String data = receivedIntent.getStringExtra(Intents.gameMode);
        try{
            gameMode = Integer.parseInt(data);
        } catch (NumberFormatException e){
            Toast.makeText(ColorPicker.this, "Invalid Game Mode", Toast.LENGTH_SHORT).show();
        }
        //Get Map Size
        try{
            mapSize = Integer.parseInt(receivedIntent.getStringExtra(Intents.mapChoice));
        } catch (NumberFormatException e){
            Toast.makeText(ColorPicker.this, "Invalid Map Size", Toast.LENGTH_SHORT).show();
        }
        //Get Comp Difficulty
        if(gameMode == Intents.comp){
            difficulty = receivedIntent.getStringExtra(Intents.compPlayerSkill);
        }

    }

    private void setTextViews(){
        TextView top = (TextView)findViewById(R.id.color_pick_top_text);
        TextView bottom = (TextView)findViewById(R.id.color_pick_bottom_text);

        if(gameMode == 1){
            top.setText("You");
            bottom.setText("Computer");
        } else if (gameMode == 2){
            top.setText("Player One");
            bottom.setText("Player Two");
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        if(startButton != null){
            startButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if(startButton != null){
            startButton.setVisibility(View.VISIBLE);
        }
    }

    private boolean isUnique(int color, int position){
        for(int i = 0; i < userPicks.length; i++){
            if(i == position){

            } else {
                try {
                    if (userPicks[i] == color) {
                        return false;
                    }
                } catch (NullPointerException e){

                }
            }
        }
        return true;
    }
}
