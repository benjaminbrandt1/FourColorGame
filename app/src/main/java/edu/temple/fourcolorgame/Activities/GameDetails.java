package edu.temple.fourcolorgame.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import edu.temple.fourcolorgame.Utils.BackArrowListener;
import edu.temple.fourcolorgame.Fragments.ComputerPlayerSkillFragment;
import edu.temple.fourcolorgame.Fragments.CustomMapPicker;
import edu.temple.fourcolorgame.Utils.HelpButtonListener;
import edu.temple.fourcolorgame.Fragments.MapChoices;
import edu.temple.fourcolorgame.R;
import edu.temple.fourcolorgame.Utils.Intents;

public class GameDetails extends AppCompatActivity implements ComputerPlayerSkillFragment.SkillFragmentListener, MapChoices.MapChoiceListener,
        CustomMapPicker.CustomMapListener {
    private int gameMode;

    private Intent intent;
    private Button nextButton;
    private MapChoices choicesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

        getGameMode();

        intent = new Intent(GameDetails.this, ColorPicker.class);


        if(gameMode== Intents.comp){
            getFragmentManager().beginTransaction()
                    .replace( R.id.computer_player_skill_fragment, ComputerPlayerSkillFragment.newInstance())
                    .commit();
        } else {
            findViewById(R.id.map_choices_panel)
                    .setLayoutParams(new RelativeLayout.LayoutParams
                            (RelativeLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        choicesFragment = MapChoices.newInstance();
        getFragmentManager().beginTransaction()
                .replace(R.id.map_choices_panel, choicesFragment)
                .commit();

        findViewById(R.id.help_button).setOnTouchListener(new HelpButtonListener(GameDetails.this, R.string.help_game_details));

        findViewById(R.id.back_arrow).setOnTouchListener(new BackArrowListener(GameDetails.this));

        nextButton = (Button)findViewById(R.id.game_details_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intent.getStringExtra(Intents.mapChoice)==null){
                    Toast.makeText(GameDetails.this, "You must choose a map size", Toast.LENGTH_SHORT).show();
                } else if (gameMode == Intents.comp && intent.getStringExtra(Intents.compPlayerSkill) == null){
                    Toast.makeText(GameDetails.this, "You must choose a computer player difficulty", Toast.LENGTH_SHORT).show();
                } else {
                    intent.putExtra(Intents.gameMode, (new Integer(gameMode)).toString());
                    startActivity(intent);
                }
            }
        });
    }

    private void getGameMode(){
        Intent receivedIntent = getIntent();
        String data = receivedIntent.getStringExtra(Intents.gameMode);
        try{
            gameMode = Integer.parseInt(data);
        } catch (NumberFormatException e){
            Toast.makeText(GameDetails.this, "Invalid Game Mode", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void updateMapChoice(String string){
        if(Integer.parseInt(string) != -1) {
            intent.putExtra(Intents.mapChoice, string);
        } else {
            getFragmentManager().beginTransaction()
                    .replace(R.id.map_choices_panel, CustomMapPicker.newInstance())
                    .commit();
        }
    }

    @Override
    public void chooseSkill(String string){
        intent.putExtra(Intents.compPlayerSkill, string);
    }

    @Override
    public void onPause(){
        super.onPause();
        if(nextButton != null){
            nextButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void customMapSelection(String size) {
        intent.putExtra(Intents.mapChoice, size);
        replaceMapPanel(choicesFragment);
        choicesFragment.setCustomSelected();
    }

    @Override
    public void customMapCancel() {
        replaceMapPanel(MapChoices.newInstance());
        intent.putExtra(Intents.mapChoice, (String)null);

    }

    @Override
    public void onResume(){
        super.onResume();
        if(nextButton != null){
            nextButton.setVisibility(View.VISIBLE);
        }
    }

    public void replaceMapPanel(Fragment fragment){
        FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                .replace(R.id.map_choices_panel, fragment)
                .commit();
        fm.executePendingTransactions();
    }
}
