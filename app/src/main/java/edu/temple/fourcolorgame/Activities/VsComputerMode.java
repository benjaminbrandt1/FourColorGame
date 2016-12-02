package edu.temple.fourcolorgame.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
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

import edu.temple.fourcolorgame.GameLogic.ComputerPlayer;
import edu.temple.fourcolorgame.GameLogic.ComputerPlayerEasy;
import edu.temple.fourcolorgame.GameLogic.ComputerPlayerHard;
import edu.temple.fourcolorgame.GameLogic.Surface;
import edu.temple.fourcolorgame.MapModels.Board;
import edu.temple.fourcolorgame.MapModels.Point;
import edu.temple.fourcolorgame.R;
import edu.temple.fourcolorgame.Utils.BoardStorage;
import edu.temple.fourcolorgame.Utils.GameInformation;
import edu.temple.fourcolorgame.Utils.GameOverDialog;
import edu.temple.fourcolorgame.Utils.HomeButtonListener;
import edu.temple.fourcolorgame.Utils.Intents;
import edu.temple.fourcolorgame.Utils.Turn;

//Activity for handling user input and gameplay for Vs Computer mode
public class VsComputerMode extends AppCompatActivity {

    private int gameMode, skippedTurnCount;
    private int[] colors;
    private int currentColor;
    private Board board;
    private Surface surface;
    private GameInformation gameInformation;
    private ArrayList<Button> colorButtons;
    private Turn turn;
    private boolean gameOver, computerTurn;
    private int humanScore, computerScore;
    private ComputerPlayer computerPlayer;
    private Handler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vs_computer_mode);
        //Initialize variables and set button listeners
        findViewById(R.id.home_button).setOnTouchListener(new HomeButtonListener(VsComputerMode.this));
        uiHandler = new Handler();
        gameOver = false;
        computerTurn = false;
        humanScore = 0;
        computerScore = 0;
        colors = new int[4];

        //Get the proper size for the game board
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float density = metrics.density;
        int width = (int)(metrics.widthPixels - 0.5*density * 64);
        int height = width;
        Log.d("WIDTHLOADING", String.valueOf(width));
        Log.d("WIDTHLOADING", String.valueOf(height));

        getGameInformation();

        board = ((BoardStorage)getApplication()).getBoard();
        surface = (Surface)findViewById(R.id.game_chart);

        surface.setBitmap(board.createBitmap());

        surface.setLayoutParams(new android.widget.RelativeLayout.LayoutParams(width, height));
        surface.setOnTouchListener(new VsComputerMode.VsComputerModeListener());

        colorButtons = new ArrayList<>();
        colorButtons.add((Button)findViewById(R.id.color_one));
        colorButtons.add((Button)findViewById(R.id.color_two));
        colorButtons.add((Button)findViewById(R.id.color_three));
        colorButtons.add((Button)findViewById(R.id.color_four));

        setUpColorButtons();

        skippedTurnCount = 0;
        nextTurn();
    }

    //Method for retrieving the game information from the received intent
    private void getGameInformation(){
        Intent receivedIntent = getIntent();
        gameInformation = receivedIntent.getParcelableExtra(Intents.gameInformation);
        gameMode = gameInformation.getGameMode();
        for(int i = 0; i<4; i++){
            colors[i] = (gameInformation.getColors())[i];
        }

    }

    //Set the colors of all of the color buttons and prevent them from being clicked
    private void setUpColorButtons(){
        for(int i = 0; i<4; i++){
            colorButtons.get(i).setClickable(false);
            colorButtons.get(i).setBackgroundColor(colors[i]);
        }
    }

    //Moves to the next turn in gameplay. The next turn is skipped if the color cannot make any moves.
    // If four colors have been skipped in a row, the game is ended.
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
                if(turn.getTurn() >=2){
                    computerTurn = true;
                    computerPlayerMove();
                }
            }
        }

    }

    //Dim the title and colors of the player that are not moving to identify whose turn it is
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

    //Display a dialog telling the user the result of the game
    private void endGame(){
        String message;

        gameOver = true;

        if(computerScore > humanScore){
            message = getResources().getString(R.string.computer_wins);
        } else if (humanScore > computerScore){
            message = getResources().getString(R.string.you_win);
        } else {
            message = getResources().getString(R.string.tie_game);
        }

        GameOverDialog dialog = new GameOverDialog(getResources().getString(R.string.no_more_moves), message,
                VsComputerMode.this, gameInformation);
        dialog.show();
    }

    //Listener to handle user input on the game baord. If it is the computer's turn, nothing happens.
    //If it is the player's turn and a valid move, the tile on the board is colored in
    private class VsComputerModeListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent e){
            int x = (int) e.getX();
            int y = (int) e.getY();
            Point click = new Point(x, y);
            Log.d("ClickedPuzzle", String.valueOf(x) + " " + String.valueOf(y));

            if(gameOver || computerTurn){
                return false;
            }

            if(x >= board.getWidth() || y >= board.getHeight()){
                return false;
            }

            if(board.isValidMove(click, currentColor, gameMode)){
                if(turn.getTurn() <= 1) {
                    humanScore += board.colorTerritory(click, currentColor)/100;
                    ((TextView)findViewById(R.id.human_score)).setText(String.valueOf(humanScore));
                } else {
                    return false;
                }
                surface.draw(board.createBitmap());
                nextTurn();
            } else {
                Toast.makeText(VsComputerMode.this, getResources().getText(R.string.invalidMove), Toast.LENGTH_SHORT).show();
            }

            if(board.isFilledOut()){
                endGame();

            }


            return false;
        }
    }

    //Create the proper computer player and start the background process for its turn
    private void computerPlayerMove(){
        computerPlayer = generateComputerPlayer();
        CompTurn turn = new CompTurn();
        turn.doInBackground();
    }

    //Creates a computer player of the proper difficulty
    private ComputerPlayer generateComputerPlayer(){
        if(gameInformation.getDifficulty().equals(Intents.easyMode)){
            return new ComputerPlayerEasy(board, currentColor, board.getTerritories());
        } else {
            return new ComputerPlayerHard(board, currentColor, board.getTerritories(), colors);
        }
    }

    //Computer player's turn is performed in the background so that the user can still interact with the screen
    //This method calls the computer player's nextMove method and fills in the board at the point it receives
    private class CompTurn extends AsyncTask<Void, Void, Void> {
        Point nextMove;
        protected Void doInBackground(Void... voids) {
            nextMove = computerPlayer.getNextMove();

            computerScore += board.colorTerritory(nextMove, currentColor)/100;
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    ((TextView)findViewById(R.id.computer_score)).setText(String.valueOf(computerScore));
                    surface.draw(board.createBitmap());
                }
            });
            computerTurn = false;
            nextTurn();
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected Point onPostExecute() {
            return nextMove;

        }
    }
}
