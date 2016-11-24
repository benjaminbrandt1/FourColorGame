package edu.temple.fourcolorgame.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import edu.temple.fourcolorgame.Controllers.Surface;
import edu.temple.fourcolorgame.MapModels.Board;
import edu.temple.fourcolorgame.MapModels.Point;
import edu.temple.fourcolorgame.R;
import edu.temple.fourcolorgame.Utils.BoardStorage;
import edu.temple.fourcolorgame.Utils.GameInformation;
import edu.temple.fourcolorgame.Utils.GameOverDialog;
import edu.temple.fourcolorgame.Utils.HomeButtonListener;
import edu.temple.fourcolorgame.Utils.Intents;

public class PuzzleMode extends AppCompatActivity {
    private int gameMode;
    private int[] colors;
    private Button colorOne, colorTwo, colorThree, colorFour;
    private int currentColor;
    private Board board;
    private Surface surface;
    private GameInformation gameInformation;
    private boolean gameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_mode);
        findViewById(R.id.home_button).setOnTouchListener(new HomeButtonListener(PuzzleMode.this));

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
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
        surface.setOnTouchListener(new puzzleModeListener());

        colorOne = (Button)findViewById(R.id.color_one);
        colorTwo = (Button)findViewById(R.id.color_two);
        colorThree = (Button)findViewById(R.id.color_three);
        colorFour = (Button)findViewById(R.id.color_four);

        setUpColorButton(0, colorOne);
        setUpColorButton(1, colorTwo);
        setUpColorButton(2, colorThree);
        setUpColorButton(3, colorFour);

        colorOne.performClick();



    }

    private void getGameInformation(){
        Intent receivedIntent = getIntent();
        gameInformation = receivedIntent.getParcelableExtra(Intents.gameInformation);
        gameMode = gameInformation.getGameMode();
        for(int i = 0; i<4; i++){
            colors[i] = (gameInformation.getColors())[i];
        }

    }

    private void setUpColorButton(final int index, Button button){
        button.setBackgroundColor(colors[index]);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorOne.setAlpha(0.4f);
                colorTwo.setAlpha(0.4f);
                colorThree.setAlpha(0.4f);
                colorFour.setAlpha(0.4f);
                v.setAlpha(1);
                currentColor = colors[index];
            }
        });
    }

    private class puzzleModeListener implements View.OnTouchListener {
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
                board.colorTerritory(click, currentColor);
                surface.draw(board.createBitmap());
            } else {
                Toast.makeText(PuzzleMode.this, getResources().getText(R.string.invalidMove), Toast.LENGTH_SHORT).show();
            }

            if(board.isFilledOut()){
                gameOver = true;
                GameOverDialog dialog = new GameOverDialog(getResources().getString(R.string.game_over),
                        getResources().getString(R.string.you_win), PuzzleMode.this, gameInformation);
                dialog.show();

            }


            return false;
        }
    }

}
