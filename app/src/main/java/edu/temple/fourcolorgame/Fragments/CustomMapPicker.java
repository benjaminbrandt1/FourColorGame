package edu.temple.fourcolorgame.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import edu.temple.fourcolorgame.R;

//Fragment containing the UI for choosing a custom map size
public class CustomMapPicker extends Fragment {




    private CustomMapListener mListener;
    private Activity context;

    public CustomMapPicker() {
        // Required empty public constructor
    }



    public static CustomMapPicker newInstance() {
        CustomMapPicker fragment = new CustomMapPicker();
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
        final View view =  inflater.inflate(R.layout.fragment_custom_map_picker, container, false);
        view.findViewById(R.id.custom_map_okay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the custom number of regions
                String numRegions = ((EditText)view.findViewById(R.id.custom_map_num_regions)).getText().toString();
                //Check to make sure # of regions is within bounds
                if(isOkay(numRegions)) {
                    hideKeyboard();
                    mListener.customMapSelection(numRegions);
                }
            }
        });

        view.findViewById(R.id.custom_map_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                mListener.customMapCancel();
            }
        });

        return view;
    }


    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof CustomMapListener) {
            mListener = (CustomMapListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CustomMapListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //Test the number of regions to make sure it is valid
    private boolean isOkay(String number){
        try{
            int num = Integer.parseInt(number);
            if(num < 10){
                Toast.makeText(context, "Please enter number greater than 10", Toast.LENGTH_SHORT ).show();
                return false;
            } else if (num > 50){
                Toast.makeText(context, "Please enter a number no greater than 50", Toast.LENGTH_SHORT ).show();
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException e){
            Toast.makeText(context, "Please enter a valid number", Toast.LENGTH_SHORT ).show();
            return false;
        }
    }

    private void hideKeyboard(){
        try {
            InputMethodManager inputManager = (InputMethodManager)
                    context.getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e){

        }
    }


    public interface CustomMapListener {
        void customMapSelection(String size);
        void customMapCancel();
    }
}
