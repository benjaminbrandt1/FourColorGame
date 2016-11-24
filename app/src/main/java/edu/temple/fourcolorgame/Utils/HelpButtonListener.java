package edu.temple.fourcolorgame.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.MotionEvent;
import android.view.View;

import edu.temple.fourcolorgame.R;

/**
 * Created by Ben on 10/27/2016.
 */

public class HelpButtonListener implements View.OnTouchListener{
    private Context context;
    private int textID;
    public static final String helpText = "helpText";
    private String title, message;

    public HelpButtonListener(Context context, int textID){
        this.context = context;
        this.textID = textID;
    }
    public HelpButtonListener(String title, String message, Context context) {
        this.title = title;
        this.message = message;
        this.context = context;
    }

    public void show(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle(title);

        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setAlpha(0.5f);
                return true;
            case MotionEvent.ACTION_UP:
                v.setAlpha(1);
                show();

                return true;
        }
        return false;
    }
}
