package edu.temple.fourcolorgame.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.temple.fourcolorgame.Controllers.Surface;
import edu.temple.fourcolorgame.MapModels.Board;
import edu.temple.fourcolorgame.MapModels.Point;
import edu.temple.fourcolorgame.R;
import edu.temple.fourcolorgame.Utils.BoardStorage;
import edu.temple.fourcolorgame.Utils.GameInformation;
import edu.temple.fourcolorgame.Utils.GameOverDialog;
import edu.temple.fourcolorgame.Utils.HomeButtonListener;
import edu.temple.fourcolorgame.Utils.Intents;
import edu.temple.fourcolorgame.Utils.Turn;

public class TwoPlayerMode extends AppCompatActivity {
    private int gameMode, skippedTurnCount;
    private int[] colors;
    private Button colorOne, colorTwo, colorThree, colorFour;
    private int currentColor;
    private Board board;
    private Surface surface;
    private GameInformation gameInformation;
    private ArrayList<Button> colorButtons;
    private Turn turn;
    private boolean gameOver;
    private int p1score, p2score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player_mode);
        findViewById(R.id.home_button).setOnTouchListener(new HomeButtonListener(TwoPlayerMode.this));

        gameOver = false;
        p1score = 0;
        p2score = 0;

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        /*int width = metrics.widthPixels;
        int height = width;*/

        float density = metrics.density;
        int width = (int)(metrics.widthPixels - 0.5*density * 64);
        int height = width;
        //int width = height - 2 * getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        Log.d("WIDTHLOADING", String.valueOf(width));
        Log.d("WIDTHLOADING", String.valueOf(height));

        colors = new int[4];
        getGameInformation();

        board = ((BoardStorage)getApplication()).getBoard();
        surface = (Surface)findViewById(R.id.game_chart);

        surface.setBitmap(board.createBitmap());

        surface.setLayoutParams(new android.widget.RelativeLayout.LayoutParams(width, height));
        surface.setOnTouchListener(new TwoPlayerMode.TwoPlayerModeListener());

        colorButtons = new ArrayList<>();
        colorButtons.add((Button)findViewById(R.id.color_one));
        colorButtons.add((Button)findViewById(R.id.color_two));
        colorButtons.add((Button)findViewById(R.id.color_three));
        colorButtons.add((Button)findViewById(R.id.color_four));

        setUpColorButtons();

        skippedTurnCount = 0;
        nextTurn();




    }

    private void getGameInformation(){
        Intent receivedIntent = getIntent();
        gameInformation = receivedIntent.getParcelableExtra(Intents.gameInformation);
        gameMode = gameInformation.getGameMode();
        for(int i = 0; i<4; i++){
            colors[i] = (gameInformation.getColors())[i];
        }

    }

    private void setUpColorButtons(){
        for(int i = 0; i<4; i++){
            colorButtons.get(i).setClickable(false);
            colorButtons.get(i).setBackgroundColor(colors[i]);
        }

    }

    private void nextTurn(){
        if(skippedTurnCount == 4){
            endGame();
            return;
        }
        if(turn == null){
            turn = new Turn();
            selectColor(turn.getTurn());
        } else {
            turn.next();
            selectColor(turn.getTurn());

            if(board.noMovesAvailable(colors[turn.getTurn()])){
                skippedTurnCount++;
                Log.d("SkippedTurn", String.valueOf(skippedTurnCount));
                nextTurn();

            } else {
                skippedTurnCount = 0;
            }
        }

    }

    private void selectColor(Integer index){
        for(int i = 0; i<4; i++) {
            colorButtons.get(i).setAlpha(0.4f);
        }
        colorButtons.get(index).setAlpha(1);
        currentColor = colors[index];
        if(index <= 1){
            (findViewById(R.id.player_one_text)).setAlpha(1);
            (findViewById(R.id.player_two_text)).setAlpha(0.5f);
        } else {
            (findViewById(R.id.player_one_text)).setAlpha(0.5f);
            (findViewById(R.id.player_two_text)).setAlpha(1);
        }
    }

    private void endGame(){
        String message;

        gameOver = true;

        if(p1score > p2score){
            message = getResources().getString(R.string.player_one_wins);
        } else if (p2score > p1score){
            message = getResources().getString(R.string.player_two_wins);
        } else {
            message = getResources().getString(R.string.tie_game);
        }

        GameOverDialog dialog = new GameOverDialog(getResources().getString(R.string.no_more_moves), message,
                TwoPlayerMode.this, gameInformation);
        dialog.show();



    }

    private class TwoPlayerModeListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent e){
            int x = (int) e.getX();
            int y = (int) e.getY();
            Point click = new Point(x, y);
            Log.d("ClickedPuzzle", String.valueOf(x) + " " + String.valueOf(y));

            if(gameOver){
                return false;
            }

            if(x >= board.getWidth() || y >= board.getHeight()){
                return false;
            }

            if(board.isValidMove(click, currentColor, gameMode)){
                if(turn.getTurn() <= 1) {
                    p1score += board.colorTerritory(click, currentColor)/100;
                    ((TextView)findViewById(R.id.human_score)).setText(String.valueOf(p1score));
                } else {
                    p2score += board.colorTerritory(click, currentColor)/100;
                    ((TextView)findViewById(R.id.computer_score)).setText(String.valueOf(p2score));
                }
                surface.draw(board.createBitmap());
                nextTurn();
            } else {
                Toast.makeText(TwoPlayerMode.this, getResources().getText(R.string.invalidMove), Toast.LENGTH_SHORT).show();
            }

            if(board.isFilledOut()){
                endGame();

            }


            return false;
        }
    }

}
