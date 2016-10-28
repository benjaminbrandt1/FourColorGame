package edu.temple.fourcolorgame;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class GameDetails extends AppCompatActivity implements ComputerPlayerSkillFragment.SkillFragmentListener, MapChoices.MapChoiceListener,
        CustomMapPicker.CustomMapListener{
    private int gameMode;
    public final static String easyMode = "easy";
    public final static String hardMode = "hard";
    public final String compPlayerSkill = "skill";
    private Intent intent;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

        Intent receivedIntent = getIntent();
        String data = receivedIntent.getStringExtra(LoadingScreen.gameMode);
        try{
            gameMode = Integer.parseInt(data);
        } catch (NumberFormatException e){
            Toast.makeText(GameDetails.this, "Invalid Game Mode", Toast.LENGTH_SHORT).show();
        }

        intent = new Intent(GameDetails.this, ColorPicker.class);


        if(gameMode==1){
            getFragmentManager().beginTransaction()
                    .add( R.id.computer_player_skill_fragment, ComputerPlayerSkillFragment.newInstance())
                    .commit();
        } else {
            findViewById(R.id.map_choices_panel)
                    .setLayoutParams(new RelativeLayout.LayoutParams
                            (RelativeLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        getFragmentManager().beginTransaction()
                .add(R.id.map_choices_panel, MapChoices.newInstance())
                .commit();

        findViewById(R.id.help_button).setOnTouchListener(new HelpButtonListener(GameDetails.this, R.string.help_game_details));

        findViewById(R.id.back_arrow).setOnTouchListener(new BackArrowListener(GameDetails.this));

        nextButton = (Button)findViewById(R.id.game_details_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intent.getStringExtra(MapChoices.mapChoice)==null){
                    Toast.makeText(GameDetails.this, "You must choose a map size", Toast.LENGTH_SHORT).show();
                } else if (gameMode == 1 && intent.getStringExtra(compPlayerSkill) == null){
                    Toast.makeText(GameDetails.this, "You must choose a computer player difficulty", Toast.LENGTH_SHORT).show();
                } else {
                    intent.putExtra(LoadingScreen.gameMode, (new Integer(gameMode)).toString());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void updateMapChoice(String string){
        if(Integer.parseInt(string) != -1) {
            intent.putExtra(MapChoices.mapChoice, string);
        } else {
            getFragmentManager().beginTransaction()
                    .replace(R.id.map_choices_panel, CustomMapPicker.newInstance())
                    .commit();
        }
    }

    @Override
    public void chooseSkill(String string){
        intent.putExtra(compPlayerSkill, string);
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
        intent.putExtra(MapChoices.mapChoice, size);
        MapChoices choicesFragment = MapChoices.newInstance();
        replaceMapPanel(choicesFragment);
        choicesFragment.setCustomSelected();
    }

    @Override
    public void customMapCancel() {
        replaceMapPanel(MapChoices.newInstance());
        intent.putExtra(MapChoices.mapChoice, (String)null);

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
