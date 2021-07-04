package edu.wm.cs.cs301.nickwilson.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.wm.cs.cs301.nickwilson.R;
public class FinishActivity extends AppCompatActivity {
    /**
     * Button to restart the game
     */
    private Button restartButton;
    /**
     * Text showing whether or not you won/lost
     */
    private TextView victoryText;
    /**
     * Text showing remaining energy if played in automated mode
     */
    private TextView energyText;
    /**
     * Text showing path length if played in automated mode
     */
    private TextView pathText;

    /**
     * This method sets the various fields to the corresponding
     * XML components. It also retrieves data from the previous activity
     * and displays the appropriate win/loss/energy consumption/path length
     * data.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        //Retrieve data from previous activity
        Intent i = getIntent();
        boolean victory = i.getBooleanExtra("win", false);
        int pathLength = i.getIntExtra("path", -1);
        int energyConsumed = i.getIntExtra("energy", -1);
        //Set various fields to the correct XML widgets
        victoryText = findViewById(R.id.win_text);
        energyText= findViewById(R.id.energy_consumed_text);
        pathText = findViewById(R.id.path_length_text);
        restartButton = findViewById(R.id.restart_button);
        //Display victory state
        if(!victory){
            victoryText.setText(R.string.lose_string);
        }
        pathText.setText("Path Length: " + pathLength);
        //Display energy consumption/path length data
        if(energyConsumed > -1){//If playing animated
            energyText.setText("Energy Consumed: " + energyConsumed);
        }
        else{ //If playing manually, don't need to see energy or path length
            energyText.setVisibility(View.INVISIBLE);
            //pathText.setVisibility(View.INVISIBLE);
        }
        setRestartButtonListener();
    }

    /**
     * This method sets the listener for the restart button. Moved from onCreate
     * for easier readability.
     */
    private void setRestartButtonListener(){
        //Make restart button a listener to return to the start screen
        restartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //For now, just toast + log
                String message = "Restart Button Clicked";
//                Toast.makeText(FinishActivity.this, message,
//                        Toast.LENGTH_SHORT).show();
                Log.v("PlayManuallyActivity",message);
                Intent goHome = new Intent(FinishActivity.this, AMazeActivity.class);
                startActivity(goHome);
            }
        });
    }
    /**
     * Returns to the starting screen upon pressing the back button.
     */
    @Override
    public void onBackPressed(){
        Log.v("Back Press PlayManuallyActivity", "Back Press Called");
        Intent goHome = new Intent(FinishActivity.this, AMazeActivity.class);
        startActivity(goHome);
    }
}