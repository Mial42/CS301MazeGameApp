package edu.wm.cs.cs301.nickwilson.gui;

import edu.wm.cs.cs301.nickwilson.generation.Maze;

/**
 * This interface encapsulates what's needed to move to the finish screen
 * and return the MazePanel used to draw the screen for
 * PlayAnimationActivity and PlayManuallyActivity.
 *
 * Used to replace controller.switchFromPlayingToWinning in StatePlaying.java.
 * @author Nick Wilson
 */
public interface PlayingActivity {
    /**
     * Moves the app to the finish screen.
     */
    public void moveToFinishScreen();

    /**
     *
     * @return the MazePanel used to draw on the screen
     */
    public MazePanel getMazePanel();

    /**
     *
     * @return the Maze being played
     */
    public Maze getMaze();

    /**
     *
     * @return the Robot used to play the maze. Null if nonexistent.
     */
    public Robot getRobot();
    /**
     *
     * @return the RobotDriver used to control the robot. Null if nonexistent.
     */
    public RobotDriver getRobotDriver();

    /**
     * Updates various robot-related fields. Does nothing with no robot.
     */
    public void update();
}
