package edu.temple.fourcolorgame.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import edu.temple.fourcolorgame.R;
import edu.temple.fourcolorgame.Utils.GameInformation;
import edu.temple.fourcolorgame.Utils.Intents;

public class LoadingScreen extends AppCompatActivity {
    private GameInformation gameInformation;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        getGameInformation();
        if(gameInformation.getGameMode() == 0) {
            intent = new Intent(LoadingScreen.this, PuzzleMode.class);
        } else {
            intent = new Intent(LoadingScreen.this, TitleScreen.class);
        }

        intent.putExtra(Intents.gameInformation, gameInformation);
        startActivity(intent);

    }

    private void getGameInformation(){
        Intent receivedIntent = getIntent();
        gameInformation = receivedIntent.getParcelableExtra(Intents.gameInformation);
        Log.d("gameInfo", String.valueOf(gameInformation.getGameMode()));
        Log.d("gameInfo", String.valueOf(gameInformation.getMapSize()));
    }

}
