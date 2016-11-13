package edu.temple.fourcolorgame.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import edu.temple.fourcolorgame.R;
import edu.temple.fourcolorgame.Utils.Intents;


public class MapChoices extends Fragment {

    private Button small, medium, large, custom;

    private MapChoiceListener mListener;

    public MapChoices() {
        // Required empty public constructor
    }

    public static MapChoices newInstance() {
        MapChoices fragment = new MapChoices();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_map_choices, container, false);
        setUpMapButtons(v);


        return v;
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof MapChoiceListener) {
            mListener = (MapChoiceListener) context;
        } else {
            Toast.makeText(context, "Null Listener", Toast.LENGTH_SHORT).show();
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface MapChoiceListener {
        void updateMapChoice(String string);
    }

    public void setCustomSelected(){
        small.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        medium.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        large.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        custom.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    private void buttonClicked(View v){

            small.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            medium.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            large.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            custom.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            v.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }


    private void setUpMapButtons(View v){
        small = (Button)v.findViewById(R.id.small_map);
        medium = (Button)v.findViewById(R.id.medium_map);
        large = (Button)v.findViewById(R.id.large_map);
        custom = (Button)v.findViewById(R.id.custom_map);
        small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked(v);
                mListener.updateMapChoice((new Integer(Intents.smallMap)).toString());
            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked(v);
                mListener.updateMapChoice((new Integer(Intents.mediumMap)).toString());
            }
        });
        large.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked(v);
                mListener.updateMapChoice((new Integer(Intents.largeMap)).toString());
            }
        });
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked(v);
                mListener.updateMapChoice((new Integer(Intents.customMap)).toString());
            }
        });
    }
}
