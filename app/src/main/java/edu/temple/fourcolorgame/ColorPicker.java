package edu.temple.fourcolorgame;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ben on 10/14/2016.
 */

public class ColorPicker extends AppCompatActivity {
    int gameMode;
    Intent intent;
    ArrayList<Integer> colorChoices;
    Integer[] userPicks;
    int[] positions;
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        startButton = (Button)findViewById(R.id.color_picker_start);

        userPicks = new Integer[4];
        positions = new int[4];

        Intent receivedIntent = getIntent();
        String data = receivedIntent.getStringExtra(LoadingScreen.gameMode);
        try{
            gameMode = Integer.parseInt(data);
        } catch (NumberFormatException e){
            Toast.makeText(ColorPicker.this, "Invalid Game Mode", Toast.LENGTH_SHORT).show();
        }

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
                //TODO change intent extra labels to resource strings
                intent.putExtra(LoadingScreen.gameMode, mode);
                intent.putExtra(LoadingScreen.firstColor, userPicks[0].toString());
                intent.putExtra(LoadingScreen.secondColor, userPicks[1].toString());
                intent.putExtra(LoadingScreen.thirdColor, userPicks[2].toString());
                intent.putExtra(LoadingScreen.fourthColor, userPicks[3].toString());
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
