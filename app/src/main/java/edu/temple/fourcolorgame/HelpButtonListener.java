package edu.temple.fourcolorgame;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Ben on 10/27/2016.
 */

public class HelpButtonListener implements View.OnTouchListener{
    private Context context;
    private int textID;
    public static final String helpText = "helpText";

    public HelpButtonListener(Context context, int textID){
        this.context = context;
        this.textID = textID;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setAlpha(0.5f);
                return true;
            case MotionEvent.ACTION_UP:
                v.setAlpha(1);
                //TODO implement help dialog
                Intent intent = new Intent(context, HelpPopup.class);
                intent.putExtra(helpText, (new Integer(textID)).toString());
                context.startActivity(intent);

                return true;
        }
        return false;
    }
}
