package edu.temple.fourcolorgame;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ben on 10/14/2016.
 */

public class ColorPicker extends AppCompatActivity {
    int gameMode;
    Intent intent;
    ArrayList<Integer> colorChoices;
    Integer first, second, third, fourth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        Intent receivedIntent = getIntent();
        String data = receivedIntent.getStringExtra("gameMode");
        try{
            gameMode = Integer.parseInt(data);
        } catch (NumberFormatException e){
            Toast.makeText(ColorPicker.this, "Invalid Game Mode", Toast.LENGTH_SHORT).show();
        }

        intent  = new Intent(ColorPicker.this, LoadingScreen.class);

        Resources res = ColorPicker.this.getResources();
        colorChoices = getIntegerArray(res.getIntArray(R.array.colorChoices));


        Spinner firstSpinner = (Spinner)findViewById(R.id.first_color_choice);
        Spinner secondSpinner = (Spinner)findViewById(R.id.second_color_choice);
        Spinner thirdSpinner = (Spinner)findViewById(R.id.third_color_choice);
        Spinner fourthSpinner = (Spinner)findViewById(R.id.fourth_color_choice);

        ColorAdapter adapter = new ColorAdapter(ColorPicker.this, colorChoices);

        firstSpinner.setAdapter(adapter);
        secondSpinner.setAdapter(adapter);
        thirdSpinner.setAdapter(adapter);
        fourthSpinner.setAdapter(adapter);

        setUpSpinners(firstSpinner, "firstColor");
        setUpSpinners(secondSpinner, "secondColor");
        setUpSpinners(thirdSpinner, "thirdColor");
        setUpSpinners(fourthSpinner, "fourthColor");

        ((Button)findViewById(R.id.color_picker_start)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mode = (new Integer(gameMode)).toString();
                intent.putExtra("gameMode", mode);
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

    private void setUpSpinners(Spinner spinner, final String valueToStore){


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){ }else{
                    Integer colorSelected = (Integer) parent.getItemAtPosition(position);
                    intent.putExtra(valueToStore, colorSelected.toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}
