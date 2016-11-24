package edu.temple.fourcolorgame.Utils;

import android.app.Application;

import edu.temple.fourcolorgame.MapModels.Board;

/**
 * Created by Ben on 11/18/2016.
 */

public class BoardStorage extends Application {
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    private Board board;
}
