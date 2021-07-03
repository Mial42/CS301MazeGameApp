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
import edu.wm.cs.cs301.nickwilson.generation.Maze;

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
     * Energy text showing how much energy has been consumed
     */
    private TextView energyText;
    /**
     * Button for starting and pausing the animation
     */
    private Button startPauseButton;
    /**
     * int recording the total path length.
     */
    private int pathLength = 0;
    /**
     * Text View displaying the path length
     */
    private TextView pathLengthText;
    /**
     * Boolean representing the "won" status of the game.
     */
    private boolean won = false;
    /**
     * int recording the total energy consumed
     */
    private int energyConsumed = 0;
    /**
     * A MazePanel to hold the graphics for the game
     */
    private MazePanel myMazePanel;
    /**
     * A StatePlaying to actually play the game
     */
    private StatePlaying myStatePlaying;
    /**
     * A Robot used to play the game
     */
    private Robot myRobot;
    /**
     * A RobotDriver used to control myRobot
     */
    private RobotDriver myRobotDriver;
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
        myMazePanel = findViewById(R.id.maze_panel);
        wallsToggle = (ToggleButton)findViewById(R.id.walls_button);
        solutionToggle = (ToggleButton)findViewById(R.id.solution_button);
        mapToggle = (ToggleButton)findViewById(R.id.map_button);
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
        setStartPauseButtonListener();
        setToggleListeners();

        myStatePlaying = new StatePlaying();
        myStatePlaying.setMazeConfiguration(GeneratingActivity.myMaze);
        myStatePlaying.start(myMazePanel, this);
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
     * Make the start/pause button a listener. This button either starts or pauses
     * the animation.
     */
    private void setStartPauseButtonListener(){
        startPauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message = startPauseButton.getText() + " Clicked";
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
     */
    private void setToggleListeners(){
        //Make wallToggle a listener
        wallsToggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message = "Wall Toggle Clicked";
                Log.v("PlayAnimationActivity",message);
                myStatePlaying.keyDown(Constants.UserInput.TOGGLELOCALMAP, 0);
            }
        });
        //Make solutionToggle a listener
        solutionToggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message = "Solution Toggle Clicked";
                Log.v("PlayAnimationActivity",message);
                myStatePlaying.keyDown(Constants.UserInput.TOGGLESOLUTION, 0);
            }
        });
        //Make mapToggle a listener
        mapToggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message = "Map Toggle Clicked";
                Log.v("PlayAnimationActivity",message);
                myStatePlaying.keyDown(Constants.UserInput.TOGGLEFULLMAP, 0);
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
    @Override
    public MazePanel getMazePanel(){
        return myMazePanel;
    }
    @Override
    public Maze getMaze() {
        return GeneratingActivity.myMaze;
    }

    /**
     * Updates the energy and odometer with the values from myRobot.
     * Updates win status with isAtExit from myRobot.
     */
    @Override
    public void update(){
        energyConsumed = (int)(2000 - myRobot.getBatteryLevel());
        pathLength = myRobot.getOdometerReading();
        pathLengthText.setText("Path Length: " +  pathLength);
        energyText.setText("Energy Consumed: " + energyConsumed);
        if(myRobot.isAtExit()){
            won = true;
        }
    }
    /**
     * @return the Robot used to play the maze. Null if nonexistent.
     */
    @Override
    public Robot getRobot() {
        return myRobot;
    }

    /**
     * @return the RobotDriver used to control the robot. Null if nonexistent.
     */
    @Override
    public RobotDriver getRobotDriver() {
        return myRobotDriver;
    }
}