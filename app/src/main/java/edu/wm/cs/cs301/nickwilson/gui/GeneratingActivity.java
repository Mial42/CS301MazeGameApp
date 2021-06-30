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
import edu.wm.cs.cs301.nickwilson.generation.Factory;
import edu.wm.cs.cs301.nickwilson.generation.Maze;
import edu.wm.cs.cs301.nickwilson.generation.MazeBuilderPrim;
import edu.wm.cs.cs301.nickwilson.generation.MazeFactory;
import edu.wm.cs.cs301.nickwilson.generation.Order;

public class GeneratingActivity extends AppCompatActivity implements Order {
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
     * The builder that actually creates the Maze.
     */
    private Builder myBuilder;
    /**
     * Int representing the seed. Hardcoded as 13 for deterministic purposes.
     */
    private int mySeed = 5;
    /**
     * A Factory to build the Maze
     */
    protected Factory myFactory;
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
        myFactory = new MazeFactory();
        if(generationAlgorithm.equalsIgnoreCase("Prim")){
            myBuilder = Builder.Prim;
        }
        else if(generationAlgorithm.equalsIgnoreCase("DFS")){
            myBuilder = Builder.DFS;
        }
        else{ //Kruskal's
            myBuilder = Builder.Kruskal;
        }
        solutionAlgorithm = i.getStringExtra("solut");
        mySeed = i.getIntExtra("seed", 5);
        Log.v("GeneratingActivitySeed", "" + mySeed);
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
        myFactory.cancel();
        super.onBackPressed();
    }
    /**
     * Performs background calculations on a background thread. For P4, Counts to 100.
     * For P5, generates a Maze.
     * Then starts the next activity.
     */
    private void generateMaze(){
        myFactory.order(this);
    }

    /**
     * Gives the required skill level, range of values 0,1,2,...,15.
     *
     * @return the skill level or size of maze to be generated in response to an order
     */
    @Override
    public int getSkillLevel() {
        return skillLevel;
    }

    /**
     * Gives the requested builder algorithm, possible values
     * are listed in the Builder enum type.
     *
     * @return the builder algorithm that is expected to be used for building the maze
     */
    @Override
    public Builder getBuilder() {
        return myBuilder;
    }

    /**
     * Describes if the ordered maze should be perfect, i.e. there are
     * no loops and no isolated areas, which also implies that
     * there are no rooms as rooms can imply loops
     *
     * @return true if a perfect maze is wanted, false otherwise
     */
    @Override
    public boolean isPerfect() {
        return !rooms;//If rooms is true, perfect is false and vice-versa
    }

    /**
     * Gives the seed that is used for the random number generator
     * used during the maze generation.
     *
     * @return the current setting for the seed value of the random number generator
     */
    @Override
    public int getSeed() {
        return mySeed; //Hardcoding 5 as my default seed
    }

    /**
     * Delivers the produced maze.
     * This method is called by the factory to provide the
     * resulting maze as a MazeConfiguration.
     * It is a call back function that is called some time
     * later in response to a client's call of the order method.
     *
     * @param mazeConfig is the maze that is delivered in response to an order
     */
    @Override
    public void deliver(Maze mazeConfig) {
        myMaze = mazeConfig;
        if(!stopGeneration) { //Start the next activity when the Maze is delivered
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

    /**
     * Provides an update on the progress being made on
     * the maze production. This method is called occasionally
     * during production, there is no guarantee on particular values.
     * Percentage will be delivered in monotonously increasing order,
     * the last call is with a value of 100 after delivery of product.
     *
     * @param percentage of job completion
     */
    @Override
    public void updateProgress(int percentage) {
        progressBar.setProgress(percentage);
    }
}