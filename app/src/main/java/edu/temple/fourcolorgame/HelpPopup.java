package edu.temple.fourcolorgame;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

public class HelpPopup extends AppCompatActivity {
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_popup);

        Intent intent = getIntent();
        int textID = Integer.parseInt(intent.getStringExtra(HelpButtonListener.helpText));

        ((TextView)findViewById(R.id.help_text)).setText(textID);



        double hMultiplier = 1;

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int orientation = HelpPopup.this.getResources().getConfiguration().orientation;

        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            hMultiplier = 0.7;
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            hMultiplier = 0.9;
        }

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*hMultiplier));

        findViewById(R.id.popup_okay_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
