package edu.wm.cs.cs301.nickwilson.gui;

import edu.wm.cs.cs301.nickwilson.generation.CardinalDirection;
import edu.wm.cs.cs301.nickwilson.generation.Maze;
import edu.wm.cs.cs301.nickwilson.gui.Robot.Direction;
import edu.wm.cs.cs301.nickwilson.gui.Robot.Turn;
/**
 * Wizards implement the RobotDriver interface and so operate a Robot to escape a maze.
 * Uses the Distance Array to escape the maze.
 * Responsibilities: Come up with the sequence of moves necessary to escape the Maze.
 * Collaborators: RobotDrivers control Robots. Wizards access the Maze through the Controller class.
 * anything about the Maze itself
 * @author Nicholas Wilson
 *
 */
public class Wizard extends WallFollower implements RobotDriver {

	@Override
	public boolean drive1Step2Exit() throws Exception {
		if(myRobot.isAtExit()) {
			//Exit is either left, right, or forward
			//Don't need to do anything if it's forward
			if(myRobot.canSeeThroughTheExitIntoEternity(Direction.LEFT)) {
				myRobot.rotate(Turn.LEFT);
			}
			else if(myRobot.canSeeThroughTheExitIntoEternity(Direction.RIGHT)) {
				myRobot.rotate(Turn.RIGHT);
			}
			return false;
		}
		//If out of power, throw an Exception
		if(myRobot.getBatteryLevel() < 4) {
			throw new Exception();
		}
		//If not:
		//Find the closest neighbor to the exit
		CardinalDirection wayToTurn = getClosestNeighbor();
		//Move to that neighbor
		switch (wayToTurn) {
			case North: 
				if(myRobot.getCurrentDirection().equals(CardinalDirection.South)) {
					myRobot.rotate(Turn.AROUND);
				}
				if(myRobot.getCurrentDirection().equals(CardinalDirection.West)) {
					myRobot.rotate(Turn.LEFT);
				}
				if(myRobot.getCurrentDirection().equals(CardinalDirection.East)) {
					myRobot.rotate(Turn.RIGHT);
				}
				myRobot.move(1);
				break;
			case South:
				if(myRobot.getCurrentDirection().equals(CardinalDirection.North)) {
					myRobot.rotate(Turn.AROUND);
				}
				if(myRobot.getCurrentDirection().equals(CardinalDirection.East)) {
					myRobot.rotate(Turn.LEFT);
				}
				if(myRobot.getCurrentDirection().equals(CardinalDirection.West)) {
					myRobot.rotate(Turn.RIGHT);
				}
				myRobot.move(1);
				break;
			case East:
				if(myRobot.getCurrentDirection().equals(CardinalDirection.West)) {
					myRobot.rotate(Turn.AROUND);
				}
				if(myRobot.getCurrentDirection().equals(CardinalDirection.North)) {
					myRobot.rotate(Turn.LEFT);
				}
				if(myRobot.getCurrentDirection().equals(CardinalDirection.South)) {
					myRobot.rotate(Turn.RIGHT);
				}
				myRobot.move(1);
				break;
			case West:
				if(myRobot.getCurrentDirection().equals(CardinalDirection.East)) {
					myRobot.rotate(Turn.AROUND);
				}
				if(myRobot.getCurrentDirection().equals(CardinalDirection.South)) {
					myRobot.rotate(Turn.LEFT);
				}
				if(myRobot.getCurrentDirection().equals(CardinalDirection.North)) {
					myRobot.rotate(Turn.RIGHT);
				}
				myRobot.move(1);
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + wayToTurn);
		}
		//Return true
		return true;
	}
	/**
	 * This method tells you the CardinalDirection to the closest neighbor to the exit
	 * @return a CardinalDirection that tells the way to go to the closest neighbor to the exit
	 * @throws Exception 
	 */
	private CardinalDirection getClosestNeighbor() throws Exception {
		//Find each neighbor's distance
		//Compare distances
		//Find the minimum one
		//Return that neighbor's direction
		int myX = myRobot.getCurrentPosition()[0];
		int myY = myRobot.getCurrentPosition()[1];
		int closestDistance = myMaze.getDistanceToExit(myX, myY);
		CardinalDirection result = null;
		if(!myMaze.hasWall(myX, myY, CardinalDirection.North) && myMaze.getDistanceToExit(myX, myY - 1) < closestDistance) { 
			//Northern Neighbor closer to exit
			closestDistance = myMaze.getDistanceToExit(myX, myY - 1);
			result = CardinalDirection.North;
		}
		if(!myMaze.hasWall(myX, myY, CardinalDirection.South) && myMaze.getDistanceToExit(myX, myY + 1) < closestDistance) { //Southern Neighbor
			closestDistance = myMaze.getDistanceToExit(myX, myY + 1);
			result = CardinalDirection.South;
		}
		if(!myMaze.hasWall(myX, myY, CardinalDirection.East) && myMaze.getDistanceToExit(myX + 1, myY) < closestDistance) { //Eastern Neighbor
			closestDistance = myMaze.getDistanceToExit(myX + 1, myY);
			result = CardinalDirection.East;
		}
		if(!myMaze.hasWall(myX, myY, CardinalDirection.West) && myMaze.getDistanceToExit(myX - 1, myY) < closestDistance) { //Western Neighbor
			closestDistance =  myMaze.getDistanceToExit(myX - 1, myY);
			result = CardinalDirection.West;
		}
		return result;
	}
}
