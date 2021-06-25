package edu.wm.cs.cs301.nickwilson.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import edu.wm.cs.cs301.nickwilson.R;
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
    ProgressBar progressBar;
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
        generateMaze();
    }
    /**
     * Performs background calculations on a background thread. For P4, Counts to 100.
     * For P5, generates a Maze.
     */
    private void generateMaze(){
        new Thread(new Runnable() {
            public void run() {
                for(int i = 0; i < 100; i++){
                    try{
                        progressBar.incrementProgressBy(1);
                        Log.v("GenerateMaze Progress Count", "Progress Count: " + i);
                        Thread.sleep(100);
                    }
                    catch(Exception e){
                        Log.v("generateMaze", "Should never through an Exception here");
                    }
                }
            }
        }).start();
    }
}