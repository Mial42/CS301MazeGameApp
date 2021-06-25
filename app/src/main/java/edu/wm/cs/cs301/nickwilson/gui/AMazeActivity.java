package edu.wm.cs.cs301.nickwilson.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import edu.wm.cs.cs301.nickwilson.R;

public class AMazeActivity extends AppCompatActivity {
    /**
     * A SeekBar for choosing the Maze's difficulty.
     */
    private SeekBar sizeBar;
    /**
     * A Spinner for choosing the Maze's Generation Algorithm.
     */
    private Spinner generationSpinner;
    /**
     * A Spinner for choosing the Maze's Solution Algorithm.
     */
    private Spinner solutionSpinner;
    /**
     * Toggles the creation of rooms in the Maze.
     */
    private ToggleButton roomsButton;
    /**
     * Generates a new Maze.
     */
    private Button generateNewButton;
    /**
     * Revisits an old Maze.
     */
    private Button revisitOldButton;
    /**
     * The skill level of the Maze. Can be changed via sizeBar.
     */
    private int skillLevel = 0;
    /**
     * String representing the generation algorithm.
     */
    private String generationAlgorithm = "Kruskal";
    /**
     * String representing the solution algorithm.
     */
    private String solutionAlgorithm = "Manual";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_maze);

        sizeBar = (SeekBar)findViewById(R.id.difficulty_seek_bar);
        generationSpinner = (Spinner)findViewById(R.id.generation_spinner);
        solutionSpinner = (Spinner) findViewById(R.id.solution_spinner);
        roomsButton = (ToggleButton)findViewById(R.id.rooms_toggle);
        generateNewButton = (Button)findViewById(R.id.generate_new_button);
        revisitOldButton = (Button)findViewById(R.id.revisit_old_button);
        //Set up the sizeBar's functionality
        setSizeBarListener();
        //Set up the spinner's listeners
        setGenerationSpinnerListener();
        setSolutionSpinnerListener();
        //Set the default room value to true
        roomsButton.setChecked(true);
        //Set up the generation spinner's functionality
        String[] generationAlgorithms = {"Kruskal", "DFS", "Prim"};
        ArrayAdapter genAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,
                generationAlgorithms);
        genAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        generationSpinner.setAdapter(genAdapter);
        //Set up the solution spinner's functionality
        String[] solutionAlgorithms = {"Manual", "WallFollower", "Wizard"};
        ArrayAdapter solAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,
                solutionAlgorithms);
        solAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        solutionSpinner.setAdapter(solAdapter);
        //Set the generateNewButton's functionality
        generateNewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message = "Skill Level: " + skillLevel + "\nGeneration Algorithm: " +
                        generationAlgorithm + "\nSolution Algorithm: " + solutionAlgorithm
                        + "\nRooms Status: " + roomsButton.isChecked();
                Toast.makeText(AMazeActivity.this, message,
                        Toast.LENGTH_SHORT).show();
                Log.v("setSizeBarListener",message);

                Intent i = new Intent(AMazeActivity.this, GeneratingActivity.class);
                i.putExtra("rooms", roomsButton.isChecked());
                i.putExtra("skill", skillLevel);
                i.putExtra("gener", generationAlgorithm);
                i.putExtra("solut", solutionAlgorithm);
                startActivity(i);
            }
        });
        //Set the revisit old button's functionality. Will change for P5.
        revisitOldButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message = "Skill Level: " + skillLevel + "\nGeneration Algorithm: " +
                        generationAlgorithm + "\nSolution Algorithm: " + solutionAlgorithm
                        + "\nRooms Status: " + roomsButton.isChecked();
                Toast.makeText(AMazeActivity.this, message,
                        Toast.LENGTH_SHORT).show();
                Log.v("setSizeBarListener",message);

                Intent i = new Intent(AMazeActivity.this, GeneratingActivity.class);
                i.putExtra("rooms", roomsButton.isChecked());
                i.putExtra("skill", skillLevel);
                i.putExtra("gener", generationAlgorithm);
                i.putExtra("solut", solutionAlgorithm);
                startActivity(i);
            }
        });
    }

    /**
     * This method sets the listener for the sizeBar seekbar. It sets
     * the skillLevel to the progress of the sizeBar. For P4, it also spawns a toast
     * displaying this skillLevel when the slider is moved.
     */
    private void setSizeBarListener(){
        sizeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                skillLevel = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(AMazeActivity.this, "The skill level is: " + skillLevel,
                        Toast.LENGTH_SHORT).show();
                Log.v("setSizeBarListener","The skill level is: " + skillLevel);
            }
        });
    }

    /**
     * Sets the generation spinner's listener, allowing you to change the generationAlgorithm
     * String. For P4, also displays a Toast showing this String.
     */
    private void setGenerationSpinnerListener(){
        generationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                generationAlgorithm = parent.getItemAtPosition(position).toString();
                Toast.makeText(AMazeActivity.this, "The generation algorithm is: " + generationAlgorithm,
                        Toast.LENGTH_SHORT).show();
                Log.v("setSpinnerListeners","The generation algorithm is: " + generationAlgorithm);
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
                //Do nothing if nothing's selected
            }
        });
    }
    /**
     * Sets the solution spinner's listener, allowing you to change the solutionAlgorithm
     * String. For P4, also displays a Toast showing this String.
     */
    private void setSolutionSpinnerListener(){
        solutionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                solutionAlgorithm = parent.getItemAtPosition(position).toString();
                Toast.makeText(AMazeActivity.this, "The solution algorithm is: " + solutionAlgorithm,
                        Toast.LENGTH_SHORT).show();
                Log.v("setSpinnerListeners","The solution algorithm is: " + solutionAlgorithm);
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
                //Do nothing if nothing's selected
            }
        });
    }
}