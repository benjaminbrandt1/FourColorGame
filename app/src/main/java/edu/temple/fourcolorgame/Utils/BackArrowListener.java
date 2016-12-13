package edu.temple.fourcolorgame.Utils;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Ben on 10/27/2016.
 */

/**
 * This listener simulates the back button being pressed
 */
public class BackArrowListener implements View.OnTouchListener {
    private Activity activity;

    public BackArrowListener(Activity activity){
        this.activity = activity;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setAlpha(0.5f);
                return true;
            case MotionEvent.ACTION_UP:
                v.setAlpha(1);
                activity.onBackPressed();
                activity = null;

                return true;
        }
        return false;
    }
}
