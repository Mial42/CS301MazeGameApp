package edu.wm.cs.cs301.nickwilson.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import edu.wm.cs.cs301.nickwilson.R;

public class PlayManuallyActivity extends AppCompatActivity implements PlayingActivity {
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
     * Button to move left
     */
    private Button leftButton;
    /**
     * Button to move right
     */
    private Button rightButton;
    /**
     * Button to move forwards
     */
    private Button forwardsButton;
    /**
     * Button to move backwards
     */
    private Button backwardsButton;
    /**
     * Button to jump
     */
    private Button jumpButton;

    /**
     * This method instantiates the various fields and sets them to the
     * appropriate XML components. Also sets the various listeners for the buttons.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_manually);
        wallsToggle = (ToggleButton)findViewById(R.id.walls_button);
        solutionToggle = (ToggleButton)findViewById(R.id.solution_button);
        mapToggle = (ToggleButton)findViewById(R.id.map_button);
//        shortcutButton = findViewById(R.id.shortcut_button);

        leftButton = findViewById(R.id.left_button);
        rightButton = findViewById(R.id.right_button);
        jumpButton = findViewById(R.id.jump_button);
        forwardsButton = findViewById(R.id.forwards_button);
        backwardsButton = findViewById(R.id.back_button);

        setToggleListeners();
        setDirectionalListeners();
        Log.v("Does info transfer?", GeneratingActivity.myMaze.toString());
        Log.v("Does info transfer?", "Height: " + GeneratingActivity.myMaze.getHeight());
        Log.v("Does info transfer?", "Width: " + GeneratingActivity.myMaze.getWidth());
        Log.v("Does info transfer?", "Starting Position: (" +
                GeneratingActivity.myMaze.getStartingPosition()[0] + ", " +
                GeneratingActivity.myMaze.getStartingPosition()[1] + ")");
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
//                Toast.makeText(PlayManuallyActivity.this, message,
//                        Toast.LENGTH_SHORT).show();
                Log.v("PlayManuallyActivity",message);
            }
        });
        //Make solutionToggle a listener, so I can add the required functionality for P5
        solutionToggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Going to add functionality here for P5
                //For now, just toast + log
                String message = "Solution Toggle Clicked";
//                Toast.makeText(PlayManuallyActivity.this, message,
//                        Toast.LENGTH_SHORT).show();
                Log.v("PlayManuallyActivity",message);
            }
        });
        //Make mapToggle a listener, so I can add the required functionality for P5
        mapToggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Going to add functionality here for P5
                //For now, just toast + log
                String message = "Map Toggle Clicked";
//                Toast.makeText(PlayManuallyActivity.this, message,
//                        Toast.LENGTH_SHORT).show();
                Log.v("PlayManuallyActivity",message);
            }
        });
    }
    /**
     * This method sets the listeners to the control buttons, that is, left, right,
     * forwards, backwards, and jump.
     */
    private void setDirectionalListeners(){

        //Make leftButton a listener, so I can add the required functionality for P5
        leftButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Going to add functionality here for P5
                //For now, just toast + log
                String message = "Left Button Clicked";
//                Toast.makeText(PlayManuallyActivity.this, message,
//                        Toast.LENGTH_SHORT).show();
                Log.v("PlayManuallyActivity",message);
            }
        });
        //Make rightButton a listener, so I can add the required functionality for P5
        rightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Going to add functionality here for P5
                //For now, just toast + log
                String message = "Right Button Clicked";
//                Toast.makeText(PlayManuallyActivity.this, message,
//                        Toast.LENGTH_SHORT).show();
                Log.v("PlayManuallyActivity",message);
            }
        });
        //Make forwards button a listener, so I can add the required functionality for P5
        forwardsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Going to add functionality here for P5
                //For now, just toast + log
                String message = "Forwards Button Clicked";
//                Toast.makeText(PlayManuallyActivity.this, message,
//                        Toast.LENGTH_SHORT).show();
                Log.v("PlayManuallyActivity",message);
            }
        });
        //Make jump button a listener, so I can add the required functionality for P5
        jumpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Going to add functionality here for P5
                //For now, just toast + log
                String message = "Jump Button Clicked";
//                Toast.makeText(PlayManuallyActivity.this, message,
//                        Toast.LENGTH_SHORT).show();
                Log.v("PlayManuallyActivity",message);
            }
        });
        //Make backwards button a listener, so I can add the required functionality for P5
        backwardsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Going to add functionality here for P5
                //For now, just toast + log
                String message = "Back Button Clicked";
//                Toast.makeText(PlayManuallyActivity.this, message,
//                        Toast.LENGTH_SHORT).show();
                Log.v("PlayManuallyActivity",message);
            }
        });
    }
    /**
     * Returns to the starting screen upon pressing the back button.
     */
    @Override
    public void onBackPressed(){
        Log.v("Back Press PlayManuallyActivity", "Back Press Called");
        Intent goHome = new Intent(PlayManuallyActivity.this, AMazeActivity.class);
        startActivity(goHome);
    }

    /**
     * Moves the app to the finish screen.
     */
    @Override
    public void moveToFinishScreen() {
        String message = "Won the game manually.";

        Log.v("PlayAnimationActivity",message);
        Intent shortcutIntent = new Intent(PlayManuallyActivity.this, FinishActivity.class);
        shortcutIntent.putExtra("energy", -1);
        shortcutIntent.putExtra("win", true);
        shortcutIntent.putExtra("path",-1);
        startActivity(shortcutIntent);
    }
}