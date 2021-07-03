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
import edu.wm.cs.cs301.nickwilson.generation.Maze;

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
     * StatePlaying to encapsulate actually playing the game
     */
    private StatePlaying myStatePlaying;
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
     * The MazePanel that actually draws what needs to be drawn
     */
    private MazePanel myMazePanel;
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

        leftButton = findViewById(R.id.left_button);
        rightButton = findViewById(R.id.right_button);
        jumpButton = findViewById(R.id.jump_button);
        forwardsButton = findViewById(R.id.forwards_button);
        backwardsButton = findViewById(R.id.back_button);

        myMazePanel = findViewById(R.id.maze_panel);
        Log.v("TroubleWithMazePanel", "MazePanel: " + myMazePanel);
        //Instantiate myStatePlaying and pass the needed variables
        myStatePlaying = new StatePlaying();
        myStatePlaying.setMazeConfiguration(GeneratingActivity.myMaze);
        myStatePlaying.start(myMazePanel, this);

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
        //Make wallToggle a listener to call the appropriate StatePlaying method
        wallsToggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message = "Wall Toggle Clicked";
                Log.v("PlayManuallyActivity",message);
                myStatePlaying.keyDown(Constants.UserInput.TOGGLELOCALMAP, 0);
            }
        });
        //Make solutionToggle a listener to call the appropriate StatePlaying method
        solutionToggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message = "Solution Toggle Clicked";
                Log.v("PlayManuallyActivity",message);
                myStatePlaying.keyDown(Constants.UserInput.TOGGLESOLUTION, 0);
            }
        });
        //Make mapToggle a listener to call the appropriate StatePlaying method
        mapToggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message = "Map Toggle Clicked";
                Log.v("PlayManuallyActivity",message);
                myStatePlaying.keyDown(Constants.UserInput.TOGGLEFULLMAP, 0);
            }
        });
    }
    /**
     * This method sets the listeners to the control buttons, that is, left, right,
     * forwards, backwards, and jump.
     */
    private void setDirectionalListeners(){

        //Make leftButton a listener to call the appropriate StatePlaying method
        leftButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message = "Left Button Clicked";
                Log.v("PlayManuallyActivity",message);
                myStatePlaying.keyDown(Constants.UserInput.LEFT, 0);
            }
        });
        //Make rightButton a listener to call the appropriate StatePlaying method
        rightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message = "Right Button Clicked";
                Log.v("PlayManuallyActivity",message);
                myStatePlaying.keyDown(Constants.UserInput.RIGHT, 0);
            }
        });
        //Make forwards button a listener to call the appropriate StatePlaying method
        forwardsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message = "Forwards Button Clicked";
                Log.v("PlayManuallyActivity",message);
                myStatePlaying.keyDown(Constants.UserInput.UP, 0);
            }
        });
        //Make jump button a listener to call the appropriate StatePlaying method
        jumpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message = "Jump Button Clicked";
                Log.v("PlayManuallyActivity",message);
                myStatePlaying.keyDown(Constants.UserInput.JUMP, 0);
            }
        });
        //Make backwards button a listener to call the appropriate StatePlaying method
        backwardsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message = "Back Button Clicked";
                Log.v("PlayManuallyActivity",message);
                myStatePlaying.keyDown(Constants.UserInput.DOWN, 0);
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
    @Override
    public MazePanel getMazePanel(){
        return myMazePanel;
    }

    /**
     * @return the Maze being played
     */
    @Override
    public Maze getMaze() {
        return GeneratingActivity.myMaze;
    }
}