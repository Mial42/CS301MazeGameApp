package edu.wm.cs.cs301.nickwilson.gui;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import java.sql.Time;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import edu.wm.cs.cs301.nickwilson.R;
import edu.wm.cs.cs301.nickwilson.generation.Maze;

public class GeneratingActivity extends AppCompatActivity {
    /**
     * The skill level of the Maze. Can be changed via sizeBar.
     */
    private int skillLevel;
    /**
     * String representing the generation algorithm.
     */
    private String generationAlgorithm;
    /**
     * String representing the solution algorithm.
     */
    private String solutionAlgorithm;

    /**
     * Boolean representing rooms status of the maze
     */
    boolean rooms;
    /**
     * The ProgressBar that displays the Maze Generation progress (1-100)
     */
    private ProgressBar progressBar;
    /**
     * Background thread that generates the maze
     */
    private Thread generateMaze;
    /**
     * Boolean that tells the generateMaze thread to stop what it's doing.
     * Used for the back button.
     */
    private boolean stopGeneration;
    /**
     * The Maze that is outputted. Static so that other
     * activities can reference it.
     */
    public static Maze myMaze;
    /**
     * This method retrieves the appropriate data from the previous activity
     * and sets the various fields to the corresponding XML widgets
     * and default values.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generating);

        Intent i = getIntent();
        skillLevel = i.getIntExtra("skill", 0);
        rooms = i.getBooleanExtra("rooms", true);
        generationAlgorithm = i.getStringExtra("gener");
        solutionAlgorithm = i.getStringExtra("solut");
        progressBar = (ProgressBar)findViewById(R.id.generating_progress_bar);
        stopGeneration = false;
        generateMaze();

    }
    /**
     * Stops the generation of the maze and returns to the starting screen.
     */
    @Override
    public void onBackPressed(){
        Log.v("Back Press GeneratingActivity", "Back Press Called");
        stopGeneration = true;
        super.onBackPressed();
    }
    /**
     * Performs background calculations on a background thread. For P4, Counts to 100.
     * For P5, generates a Maze.
     * Then starts the next activity.
     */
    private void generateMaze(){

        generateMaze = new Thread(new Runnable() {
            public void run() {
                for(int i = 0; i < 100; i++){
                    if(stopGeneration){
                        break;
                    }
                    progressBar.incrementProgressBy(1);
                    Log.v("GenerateMaze Progress Count", "Progress Count: " + progressBar.getProgress());
//                    try { //Commented out to save testing time
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
                if(!stopGeneration) {
                    Intent intent;

                    if ("Manual".equals(solutionAlgorithm)) {
                        //Go to PlayManuallyActivity
                        intent = new Intent(GeneratingActivity.this, PlayManuallyActivity.class);
                    } else {
                        //Go to PlayAnimationActivity
                        intent = new Intent(GeneratingActivity.this, PlayAnimationActivity.class);
                    }
                    startActivity(intent);
                }
            }
        });
        generateMaze.start();
    }
}