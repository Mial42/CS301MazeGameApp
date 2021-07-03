package edu.wm.cs.cs301.nickwilson.gui;

import edu.wm.cs.cs301.nickwilson.generation.CardinalDirection;
import edu.wm.cs.cs301.nickwilson.generation.Maze;
import edu.wm.cs.cs301.nickwilson.gui.Robot.Direction;

/**
 * A BasicSensor is a DistanceSensor.
 * A DistanceSensor provides information how for it is
 * to a wall for a given position and in a specific direction.
 * 
 * The sensor is assumed to be mounted at a particular angle
 * relative to the forward direction of the robot.
 * This way one can have a left sensor, a right sensor, 
 * a forward, or a backward sensor.
 * 
 * So if asked about the distance to an obstacle, one needs
 * to provide the current position in the maze and the 
 * forward direction such that one can calculate the distance
 * to an obstacle a sensor measures in the relative direction
 * that it is mounted on the robot. 
 * 
 * The sensor consumes energy for its sensing operations, which
 * is why sensing expects a power supply to deduct the consumed
 * energy from.
 * 
 * A sensor can experience temporary failures of its operations.
 * So there are alternating time periods when it is up and running 
 * and down and broken. 
 * The down time of a sensor is characterized by its the mean time
 * it takes to repair itself. This could be the time it takes
 * for a reboot of the subsystem if it is a software failure.
 * The up time is described by the mean time between failures 
 * which is the time when the system comes up again and the next
 * failure that brings it down. 
 *  
 * The power consumption for the repair operations is ignored.
 * Responsibilities: Figure out the distance to obstacles in a relative direction from the
 * robot the sensor is mounted on. For instance, tell how many empty spaces there are to the
 * left/right/forwards/backwards.
 * Collaborators: BasicSensors are mounted on Robots. Since each sensor looks in a
 * cardinal direction, you can mount up to 4 sensors on a Robot (N, E, S, W). Communicates
 * energy consumption to the RobotDriver through the Robot.
 * @author Nicholas Wilson
 *
 */
public class BasicSensor implements DistanceSensor {
	/**
	 * A Maze that gives us access to the Floorplan allowing us to see where the wall are
	 */
	private Maze myMaze;
	/**
	 * A relative direction (left/right/forwards/backwards) telling you how on the Robot the sensor is mounted
	 */
	private Direction myDirection;
	/**
	 * Boolean saying whether or not the sensor is functional
	 */
	private boolean functional = true;
	/**
	 * Default constructor
	 */
	public BasicSensor() {
	}
	@Override
	public int distanceToObstacle(int[] currentPosition, CardinalDirection currentDirection, float[] powersupply)
			throws Exception {
		//Throw IllegalArgumentException
		if(currentDirection == null || powersupply == null || currentPosition == null //Any argument is null
				|| currentPosition[0] < 0 || currentPosition [0] >= myMaze.getWidth() //Illegal Width Argument
				|| currentPosition[1] < 0 || currentPosition[1] >= myMaze.getHeight()) { //Illegal height argument
			throw new IllegalArgumentException();
		}
		if(powersupply[0] < 0) {//Illegal powersupply
			throw new IndexOutOfBoundsException();
		}
		if(!functional) { //Not functional
			throw new Exception("SensorError");
		}
		if(powersupply[0] < getEnergyConsumptionForSensing()) { //Not enough power
			throw new Exception("PowerFailure");
		}
		int x = currentPosition[0];
		int y = currentPosition[1];
		//This sensor is pointed North when:
		//Robot is North and Sensor is Forwards
		//Robot is South and Sensor is Backwards
		//Robot is East and Sensor is right
		//Robot is West and Sensor is left
		if(currentDirection.equals(CardinalDirection.North) && myDirection.equals(Direction.FORWARD)
				|| currentDirection.equals(CardinalDirection.South) && myDirection.equals(Direction.BACKWARD)
				|| currentDirection.equals(CardinalDirection.East) && myDirection.equals(Direction.RIGHT)
				||currentDirection.equals(CardinalDirection.West) && myDirection.equals(Direction.LEFT)) {
			//When the Sensor is pointed North, the distance is my Y coord - the nearest wall's Y coord
			int wallY = y;
			while(myMaze.getFloorplan().hasNoWall(x, wallY, CardinalDirection.North)) { //Walk North to find the nearest
				//wall to the North
				wallY--;
				if(myMaze.getFloorplan().isExitPosition(x, wallY)) { //If at the exit, return Integer.MAX
					return Integer.MAX_VALUE;
				}
			}
			//When you have the nearest wall to the North, return the my Y coord- the wall's Y coord 
			return y - wallY;
		}
		//This sensor is pointed South when:
		//Robot is South and Sensor is Forwards
		//Robot is North and Sensor is Backwards
		//Robot is West and Sensor is right
		//Robot is East and Sensor is left
		if(currentDirection.equals(CardinalDirection.South) && myDirection.equals(Direction.FORWARD)
				|| currentDirection.equals(CardinalDirection.North) && myDirection.equals(Direction.BACKWARD)
				|| currentDirection.equals(CardinalDirection.West) && myDirection.equals(Direction.RIGHT)
				||currentDirection.equals(CardinalDirection.East) && myDirection.equals(Direction.LEFT)) {
			//When the Sensor is pointed South, the distance is the nearest wall's Y coord - my y coord
			int wallY = y;
			while(myMaze.getFloorplan().hasNoWall(x, wallY, CardinalDirection.South)) { //Walk South to find the nearest
				//wall to the South
				wallY++;
				if(myMaze.getFloorplan().isExitPosition(x, wallY)) { //If at the exit, return Integer.MAX
					return Integer.MAX_VALUE;
				}
			}
			//When you have the nearest wall to the North, return the the wall's Y coord - my y coord
			return wallY - y;
		}
		//This sensor is pointed East when:
		//Robot is East and Sensor is Forwards
		//Robot is West and Sensor is Backwards
		//Robot is North and Sensor is Left
		//Robot is South and Sensor is Right
		if(currentDirection.equals(CardinalDirection.East) && myDirection.equals(Direction.FORWARD)
				|| currentDirection.equals(CardinalDirection.West) && myDirection.equals(Direction.BACKWARD)
				|| currentDirection.equals(CardinalDirection.North) && myDirection.equals(Direction.LEFT)
				||currentDirection.equals(CardinalDirection.South) && myDirection.equals(Direction.RIGHT)) {
			//When the Sensor is pointed East, the distance is the nearest wall's X coord - my X corrd
			int wallX = x;
			while(myMaze.getFloorplan().hasNoWall(wallX, y, CardinalDirection.East)) { //Walk East to find the nearest
				//wall to the East
				wallX++;
				if(myMaze.getFloorplan().isExitPosition(wallX, y)) { //If at the exit, return Integer.MAX
					return Integer.MAX_VALUE;
				}
			}
			//When you have the nearest wall to the East, return the wall's X coord - my X coord
			return wallX - x;
		}
		//This sensor is pointed East when:
		//Robot is West and Sensor is Forwards
		//Robot is East and Sensor is Backwards
		//Robot is South and Sensor is Left
		//Robot is North and Sensor is Right
		if(currentDirection.equals(CardinalDirection.West) && myDirection.equals(Direction.FORWARD)
				|| currentDirection.equals(CardinalDirection.East) && myDirection.equals(Direction.BACKWARD)
				|| currentDirection.equals(CardinalDirection.South) && myDirection.equals(Direction.LEFT)
				||currentDirection.equals(CardinalDirection.North) && myDirection.equals(Direction.RIGHT)) {
			//When the Sensor is pointed West, the distance is my X coord - nearest wall's x coord
			int wallX = x;
			while(myMaze.getFloorplan().hasNoWall(wallX, y, CardinalDirection.West)) { //Walk West to find the nearest
				//wall to the West
				wallX--;
				if(myMaze.getFloorplan().isExitPosition(wallX, y)) { //If at the exit, return Integer.MAX
					return Integer.MAX_VALUE;
				}
			}
			//When you have the nearest wall to the West, return my X coord - the wall's x coord
			return x - wallX;
		}
		return -1;//Should never happen
	}

	@Override
	public void setMaze(Maze maze) {
		//Set myMaze to maze
		myMaze = maze;

	}

	@Override
	public void setSensorDirection(Direction mountedDirection) {
		//Set myDirection to mountedDirection
		myDirection = mountedDirection;

	}

	@Override
	public float getEnergyConsumptionForSensing() {
		// Return 1 as instructed
		return 1;
	}

	@Override
	public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void stopFailureAndRepairProcess() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();

	}

}
