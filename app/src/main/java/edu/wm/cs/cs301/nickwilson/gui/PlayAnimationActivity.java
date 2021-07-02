package edu.wm.cs.cs301.nickwilson.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import edu.wm.cs.cs301.nickwilson.R;
public class PlayAnimationActivity extends AppCompatActivity implements PlayingActivity{
    /**
     * Toggles visible walls
     */
    private ToggleButton wallsToggle;
    /**
     * Toggles visible solution. Requires map to be shown.
     */
    private ToggleButton solutionToggle;
    /**
     * Toggles visible map
     */
    private  ToggleButton mapToggle;
    /**
     * Shortcut to the finish screen
     */
    private Button shortcutButton;
    /**
     * Energy text showing how much energy has been consumed
     */
    private TextView energyText;
    /**
     * Button for starting and pausing the animation
     */
    private  Button startPauseButton;
    /**
     * int recording the total path length.
     */
    private int pathLength = 0;
    /**
     * Text View displaying the path length
     */
    private  TextView pathLengthText;
    /**
     * Boolean representing the "won" status of the game.
     */
    private boolean won = false;
    /**
     * int recording the total energy consumed
     */
    private int energyConsumed = 0;

    /**
     * This method instantiates the various fields and sets them to the corresponding
     * XML components. It also sets up the various listeners for the various buttons.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_animation);
        //Sets the various fields to the XML components and
        //sets appropriate texts
        wallsToggle = (ToggleButton)findViewById(R.id.walls_button);
        solutionToggle = (ToggleButton)findViewById(R.id.solution_button);
        mapToggle = (ToggleButton)findViewById(R.id.map_button);
        shortcutButton = findViewById(R.id.shortcut_button);
        energyText = findViewById(R.id.energy_consumed_text);
        energyText.setText("Energy Consumed: " + energyConsumed);
        startPauseButton = findViewById(R.id.start_pause_button);
        pathLength = 0;
        pathLengthText = findViewById(R.id.path_length_text);
        pathLengthText.setText("Path Length: " +  pathLength);
        startPauseButton.setText(R.string.pause_string);
        //Toggles are all active by default
        wallsToggle.setChecked(true);
        solutionToggle.setChecked(true);
        mapToggle.setChecked(true);
        //Gives the shortcut button a listener to get to the finish screen
        shortcutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //For now, just toast + log
                String message = "Path Length: " + pathLength + "\nWin Status: " +
                        won + "\nEnergy Consumed: " + energyConsumed;
//                Toast.makeText(PlayAnimationActivity.this, message,
//                        Toast.LENGTH_SHORT).show();
                Log.v("PlayAnimationActivity",message);
                Intent shortcutIntent = new Intent(PlayAnimationActivity.this, FinishActivity.class);
                shortcutIntent.putExtra("energy", energyConsumed);
                shortcutIntent.putExtra("win", won);
                shortcutIntent.putExtra("path",pathLength);
                startActivity(shortcutIntent);
            }
        });
        setStartPauseButtonListener();
        setToggleListeners();
    }
    /**
     * Returns to the starting screen upon pressing the back button.
     */
    @Override
    public void onBackPressed(){
        Log.v("Back Press PlayAnimationActivity", "Back Press Called");
        Intent goHome = new Intent(PlayAnimationActivity.this, AMazeActivity.class);
        startActivity(goHome);
    }

    /**
     * Make the start/pause button a listener. For now, it just
     * creates a toast saying whether or not it is starting or pausing,
     * and changes its text appropriately.
     */
    private void setStartPauseButtonListener(){
        startPauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Going to add functionality here for P5
                //For now, just toast + log
                String message = startPauseButton.getText() + " Clicked";
//                Toast.makeText(PlayAnimationActivity.this, message,
//                        Toast.LENGTH_SHORT).show();
                Log.v("PlayAnimationActivity",message);
                //Swap the strings on the button to change it from start to pause and vice-versa
                if(startPauseButton.getText().equals("Pause")){
                    startPauseButton.setText(R.string.start_string);
                }
                else{
                    startPauseButton.setText(R.string.pause_string);
                }
            }
        });
    }
    /**
     * This method sets up the various listeners for the toggles at the top of the screen.
     * For now, just displays Toasts and Logs data, but for P5 will do things.
     */
    private void setToggleListeners(){
        //Make wallToggle a listener, so I can add the required functionality for P5
        wallsToggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Going to add functionality here for P5
                //For now, just toast + log
                String message = "Wall Toggle Clicked";
//                Toast.makeText(PlayAnimationActivity.this, message,
//                        Toast.LENGTH_SHORT).show();
                Log.v("PlayAnimationActivity",message);
            }
        });
        //Make solutionToggle a listener, so I can add the required functionality for P5
        solutionToggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Going to add functionality here for P5
                //For now, just toast + log
                String message = "Solution Toggle Clicked";
//                Toast.makeText(PlayAnimationActivity.this, message,
//                        Toast.LENGTH_SHORT).show();
                Log.v("PlayAnimationActivity",message);
            }
        });
        //Make mapToggle a listener, so I can add the required functionality for P5
        mapToggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Going to add functionality here for P5
                //For now, just toast + log
                String message = "Map Toggle Clicked";
//                Toast.makeText(PlayAnimationActivity.this, message,
//                        Toast.LENGTH_SHORT).show();
                Log.v("PlayAnimationActivity",message);
            }
        });
    }

    /**
     * Moves the app to the finish screen.
     */
    @Override
    public void moveToFinishScreen() {
        String message = "Path Length: " + pathLength + "\nWin Status: " +
                won + "\nEnergy Consumed: " + energyConsumed;
        Log.v("PlayAnimationActivity",message);
        Intent shortcutIntent = new Intent(PlayAnimationActivity.this, FinishActivity.class);
        shortcutIntent.putExtra("energy", energyConsumed);
        shortcutIntent.putExtra("win", won);
        shortcutIntent.putExtra("path",pathLength);
        startActivity(shortcutIntent);
    }
}