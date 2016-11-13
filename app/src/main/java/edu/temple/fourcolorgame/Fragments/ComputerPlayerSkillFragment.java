package edu.temple.fourcolorgame.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.temple.fourcolorgame.Activities.GameDetails;
import edu.temple.fourcolorgame.R;
import edu.temple.fourcolorgame.Utils.Intents;

//Fragment containing the UI to choose Computer Player's skill level
public class ComputerPlayerSkillFragment extends Fragment {
    private Button easy, hard;

    private SkillFragmentListener mListener;

    public ComputerPlayerSkillFragment() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static ComputerPlayerSkillFragment newInstance() {
        ComputerPlayerSkillFragment fragment = new ComputerPlayerSkillFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_computer_player_skill, container, false);

        easy = (Button)v.findViewById(R.id.computer_skill_easy);
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hard.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                easy.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                mListener.chooseSkill(Intents.easyMode);
            }
        });
        hard = (Button)v.findViewById(R.id.computer_skill_hard);
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easy.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                hard.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                mListener.chooseSkill(Intents.hardMode);
            }
        });

        // Inflate the layout for this fragment
        return v;
    }




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof SkillFragmentListener) {
            mListener = (SkillFragmentListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement SkillFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface SkillFragmentListener {
        // TODO: Update argument type and name
        void chooseSkill(String string);
    }
}
