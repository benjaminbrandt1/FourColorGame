package edu.temple.fourcolorgame.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import edu.temple.fourcolorgame.MapModels.Map;
import edu.temple.fourcolorgame.R;
import edu.temple.fourcolorgame.Utils.BoardStorage;
import edu.temple.fourcolorgame.Utils.GameInformation;
import edu.temple.fourcolorgame.Utils.Intents;

//This Activity handles displaying a loading screen while the logic to build a game map is carried out
public class LoadingScreen extends AppCompatActivity {
    private GameInformation gameInformation;
    private Intent intent;
    private Map map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        getGameInformation();
        new BoardBuilder().execute();

    }

    /*
    Retrieve all details about the game being created
     */
    private void getGameInformation(){
        Intent receivedIntent = getIntent();
        gameInformation = receivedIntent.getParcelableExtra(Intents.gameInformation);
        Log.d("gameInfo", String.valueOf(gameInformation.getGameMode()));
        Log.d("gameInfo", String.valueOf(gameInformation.getMapSize()));
    }

    //Thread to build the game map while the loading screen is displayed
    private class BoardBuilder extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... voids) {
            //Get the proper size for the game map
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            float density = metrics.density;
            int margin = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
            int width = (int)(metrics.widthPixels - 0.5*density * 64);
            int height = width;
            Log.d("WIDTHLOADING", String.valueOf(density*64));
            Log.d("WIDTHLOADING", String.valueOf(height));
            Log.d("WIDTHLOADING", String.valueOf(margin));

            //Build the map and save it to the app module so other activities can access it
            map = new Map(gameInformation.getMapSize(), width, height, gameInformation.getColors(), LoadingScreen.this);
            BoardStorage storage = (BoardStorage) LoadingScreen.this.getApplication();
            storage.setMap(map);

            //Start the proper gameplay activity
            switch(gameInformation.getGameMode()){
                case Intents.puzzle:
                    intent = new Intent(LoadingScreen.this, PuzzleMode.class);
                    break;
                case Intents.multi:
                    intent = new Intent(LoadingScreen.this, TwoPlayerMode.class);
                    break;
                case Intents.comp:
                    intent = new Intent(LoadingScreen.this, VsComputerMode.class);
                    break;
                default:
                    Log.d("BadGameMode", String.valueOf(gameInformation.getGameMode()));
                    break;
            }

            intent.putExtra(Intents.gameInformation, gameInformation);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(Long result) {


        }
    }

}
