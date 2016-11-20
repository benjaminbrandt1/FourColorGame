package edu.temple.fourcolorgame.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import edu.temple.fourcolorgame.MapModels.Board;
import edu.temple.fourcolorgame.R;
import edu.temple.fourcolorgame.Utils.BoardStorage;
import edu.temple.fourcolorgame.Utils.GameInformation;
import edu.temple.fourcolorgame.Utils.Intents;

public class LoadingScreen extends AppCompatActivity {
    private GameInformation gameInformation;
    private Intent intent;
    private Board board;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        getGameInformation();
        new BoardBuilder().execute();

    }

    private void getGameInformation(){
        Intent receivedIntent = getIntent();
        gameInformation = receivedIntent.getParcelableExtra(Intents.gameInformation);
        Log.d("gameInfo", String.valueOf(gameInformation.getGameMode()));
        Log.d("gameInfo", String.valueOf(gameInformation.getMapSize()));
    }

    private class BoardBuilder extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... voids) {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int width = metrics.widthPixels;
            board = new Board(gameInformation.getMapSize(), width, width, gameInformation.getColors(), LoadingScreen.this);
            BoardStorage storage = (BoardStorage) LoadingScreen.this.getApplication();
            storage.setBoard(board);
            switch(gameInformation.getGameMode()){
                case Intents.puzzle:
                    intent = new Intent(LoadingScreen.this, PuzzleMode.class);
                    break;
                case Intents.multi:
                    intent = new Intent(LoadingScreen.this, TwoPlayerMode.class);
                    break;
                case Intents.comp:
                    //TODO comp intent
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
