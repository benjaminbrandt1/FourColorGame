package edu.temple.fourcolorgame;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ben on 10/14/2016.
 */

public class ColorAdapter extends BaseAdapter {
    private Context mContext;
    private final ArrayList<Integer> colors;

    public ColorAdapter(Context c, ArrayList<Integer> colors){
        mContext = c;
        this.colors = colors;
    }

    @Override
    public int getCount(){
        return colors.size();
    }

    @Override
    public Integer getItem(int position){
        return colors.get(position);

    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View oldView, ViewGroup parent){
/*        LinearLayout layout;

       *//* if(oldView!=null) {
            layout = (LinearLayout) oldView;
        } else {
            layout = new LinearLayout(mContext);
        }*/

        TextView color = new TextView(mContext);
        try {
            color.setBackgroundColor(colors.get(position));
        } catch (Exception e) {
            color.setBackgroundColor(Color.WHITE);
        }
        //color.setLayoutParams( new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));


        final float scale = mContext.getResources().getDisplayMetrics().density;
        int pixels = (int) (80 * scale + 0.5f);
        color.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, pixels));
        //color.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        /*layout = new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(color);*/
        return color;

    }


}
