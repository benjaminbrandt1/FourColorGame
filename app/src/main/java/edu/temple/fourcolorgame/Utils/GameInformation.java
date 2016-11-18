package edu.temple.fourcolorgame.Utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ben on 11/18/2016.
 */

public class GameInformation implements Parcelable {

    private int gameMode;
    private int mapSize;
    private String difficulty;
    private Integer[] colors;

    public GameInformation(){
        gameMode = -1;
        mapSize = -1000;
        difficulty = null;
        colors = new Integer[]{-1, -1, -1, -1};

    }

    /** Parcelable Methods **/

    //Build GameInformation object from parcel
    protected GameInformation(Parcel in) {
        this.colors = new Integer[4];
        String[] data = new String[7];

        in.readStringArray(data);
        this.gameMode = Integer.parseInt(data[0]);
        this.mapSize = Integer.parseInt(data[1]);
        if(gameMode == Intents.comp){
            this.difficulty = data[2];
        }
        for(int i = 0; i<4; i++){
            this.colors[i] = Integer.parseInt(data[i+3]);
        }

    }

    public static final Creator<GameInformation> CREATOR = new Creator<GameInformation>() {
        @Override
        public GameInformation createFromParcel(Parcel in) {
            return new GameInformation(in);
        }

        @Override
        public GameInformation[] newArray(int size) {
            return new GameInformation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    //Creating the Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String array[] = new String[7];
        array[0] = String.valueOf(gameMode);
        array[1] = String.valueOf(mapSize);
        array[2] = difficulty;
        for (int i = 0; i<4; i++){
            array[i+3] = String.valueOf(colors[i]);
        }
        dest.writeStringArray(array);
    }

    //Getters and Setters for fields
    public int getGameMode() {
        return gameMode;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    public int getMapSize() {
        return mapSize;
    }

    public void setMapSize(int mapSize) {
        this.mapSize = mapSize;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Integer[] getColors() {
        return colors;
    }

    public void setColors(Integer[] colors) {
        if(colors.length == 4) {
            this.colors = colors;
        }
    }
}
