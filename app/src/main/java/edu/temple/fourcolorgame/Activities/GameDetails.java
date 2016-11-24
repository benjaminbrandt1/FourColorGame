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
import edu.temple.fourcolorgame.Utils.GameInformation;
import edu.temple.fourcolorgame.Utils.HelpButtonListener;
import edu.temple.fourcolorgame.Fragments.MapChoices;
import edu.temple.fourcolorgame.R;
import edu.temple.fourcolorgame.Utils.Intents;

public class GameDetails extends AppCompatActivity implements ComputerPlayerSkillFragment.SkillFragmentListener, MapChoices.MapChoiceListener,
        CustomMapPicker.CustomMapListener {
    private GameInformation gameInformation;
    private Intent intent;
    private Button nextButton;
    private MapChoices choicesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

        getGameInformation();

        intent = new Intent(GameDetails.this, ColorPicker.class);


        if(gameInformation.getGameMode()== Intents.comp){
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

        findViewById(R.id.help_button).setOnTouchListener
                (new HelpButtonListener(getResources().getString(R.string.map_size_help),
                        getResources().getString(R.string.help_game_details), GameDetails.this ));

        findViewById(R.id.back_arrow).setOnTouchListener(new BackArrowListener(GameDetails.this));

        nextButton = (Button)findViewById(R.id.game_details_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameInformation.getMapSize() == -1000){
                    Toast.makeText(GameDetails.this, "You must choose a map size", Toast.LENGTH_SHORT).show();
                } else if (gameInformation.getGameMode() == Intents.comp && gameInformation.getDifficulty() == null){
                    Toast.makeText(GameDetails.this, "You must choose a computer player difficulty", Toast.LENGTH_SHORT).show();
                } else {
                    intent.putExtra(Intents.gameInformation, gameInformation);
                    startActivity(intent);
                }
            }
        });
    }

    private void getGameInformation(){
        Intent receivedIntent = getIntent();
        gameInformation = receivedIntent.getParcelableExtra(Intents.gameInformation);
    }

    @Override
    public void updateMapChoice(String string){
        if(Integer.parseInt(string) != -1) {
            gameInformation.setMapSize(Integer.parseInt(string));
        } else {
            getFragmentManager().beginTransaction()
                    .replace(R.id.map_choices_panel, CustomMapPicker.newInstance())
                    .commit();
        }
    }

    @Override
    public void chooseSkill(String string){
        gameInformation.setDifficulty(string);
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
        gameInformation.setMapSize(Integer.parseInt(size));
        replaceMapPanel(choicesFragment);
        choicesFragment.setCustomSelected();
    }

    @Override
    public void customMapCancel() {
        replaceMapPanel(MapChoices.newInstance());
        gameInformation.setMapSize(-1000);

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
