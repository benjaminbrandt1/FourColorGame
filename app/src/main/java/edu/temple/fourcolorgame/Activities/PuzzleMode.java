package edu.temple.fourcolorgame.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import edu.temple.fourcolorgame.Controllers.Surface;
import edu.temple.fourcolorgame.MapModels.Board;
import edu.temple.fourcolorgame.MapModels.Point;
import edu.temple.fourcolorgame.R;
import edu.temple.fourcolorgame.Utils.GameInformation;
import edu.temple.fourcolorgame.Utils.Intents;

public class PuzzleMode extends AppCompatActivity {
    private int gameMode, mapSize;
    private int[] colors;
    private Button colorOne, colorTwo, colorThree, colorFour;
    private int currentColor;
    private Board board;
    private Surface surface;
    private GameInformation gameInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_mode);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = width;


        colors = new int[4];
        getGameInformation();

        board = new Board(mapSize, width, height, colors, PuzzleMode.this);
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
        mapSize = gameInformation.getMapSize();
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
                //TODO YOU WIN DIALOG
                Toast.makeText(PuzzleMode.this, "YOU WIN DUDE", Toast.LENGTH_LONG).show();
            }


            return false;
        }
    }

}
