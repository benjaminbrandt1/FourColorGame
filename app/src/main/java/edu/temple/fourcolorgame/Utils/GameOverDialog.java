package edu.temple.fourcolorgame.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import edu.temple.fourcolorgame.Activities.LoadingScreen;
import edu.temple.fourcolorgame.Activities.TitleScreen;
import edu.temple.fourcolorgame.Activities.TwoPlayerMode;
import edu.temple.fourcolorgame.R;

/**
 * Created by Ben on 11/19/2016.
 */

public class GameOverDialog {
    private String title, message;
    private Context context;
    private GameInformation gameInformation;

    public GameOverDialog(String title, String message, Context context, GameInformation gameInformation) {
        this.title = title;
        this.message = message;
        this.context = context;
        this.gameInformation = gameInformation;
    }

    public void show(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle(title);

        builder.setNegativeButton(R.string.home, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(context, TitleScreen.class);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });

        builder.setNeutralButton(R.string.close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton(R.string.play_again, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(context, LoadingScreen.class);
                intent.putExtra(Intents.gameInformation, gameInformation);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
