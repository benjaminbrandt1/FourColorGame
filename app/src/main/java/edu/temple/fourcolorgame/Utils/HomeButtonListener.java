package edu.temple.fourcolorgame.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import edu.temple.fourcolorgame.Activities.TitleScreen;
import edu.temple.fourcolorgame.Activities.TwoPlayerMode;
import edu.temple.fourcolorgame.R;

/**
 * Created by Ben on 11/18/2016.
 */

public class HomeButtonListener implements View.OnTouchListener {
    private Context context;

    public HomeButtonListener(Context context){
        this.context = context;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setAlpha(0.5f);
                return true;
            case MotionEvent.ACTION_UP:
                v.setAlpha(1);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(R.string.are_you_sure);
                builder.setTitle(R.string.home);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, TitleScreen.class);
                        context.startActivity(intent);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();


                return true;
        }
        return false;
    }
}
