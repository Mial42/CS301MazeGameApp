package edu.wm.cs.cs301.nickwilson.generation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeSet;

public class MazeBuilderKruskal extends MazeBuilder implements Runnable {
	/**
	 * A no-argument constructor that simply calls the superclass constructor (for MazeBuilder)
	 * and prints the string "MazeBuilderKruskal uses Kruskal's algorithm to generate maze."
	 */
	public MazeBuilderKruskal() {
		super();
		System.out.println("MazeBuilderKruskal uses Kruskal's algorithm to generate maze.");
	}
	
	
	/**
	 * Kruskal's algorithm generates a spanning tree for a graph. 
	 * The cells are the nodes of the graph and the spanning tree. An edge represents that one can move from one cell to an adjacent cell.
	 * So an edge implies that its nodes are adjacent cells in the maze and that there is no wallboard separating these cells in the maze.
	 * 
	 * Creates an array of arrays of Trees. Each Tree contains one node, so there should be width * height trees total in the array of arrays.
	 * Creates a list of all possible edges in the Maze. By default, edges are blocked (there is a wallboard between the two nodes neighboring the edge).
	 * Then, loops until the list of edges is empty. Within that loop:
	 * 1) Removes the next edge specified by the seed from the list of edges.
	 * 2) Calculates the endpoint of the edge (for instance, if the edge has x coord 2, y coord 2, and points south, it's connected to x coord 2, y coord 3).
	 * 3) Creates references to the trees corresponding to the start and end points of the edge.
	 * 4) If the two nodes are not in the same Tree, merges their Trees and removes the appropriate wall from the maze.
	 */
	@Override
	protected void generatePathways() {
		//Create an array of arrays of Trees. Each Tree contains one node, so there should be width * height nodes in the set.
		Tree[][] nodeTrees = createListOfSetsOfNodes();
		//Create a list of all possible edges in the Maze
		ArrayList<Wallboard> edges = createListOfPossibleEdges();
		//Then, loop until the list of edges is empty. 
		while(!edges.isEmpty()) {
			//Within that loop:
			//Remove the next edge from the list
			Wallboard currentEdge = edges.remove(random.nextIntWithinInterval(0, edges.size() - 1));//Loop based on the seed.
			//Calculate the endpoint of the edge (for instance, if the edge has x coord 2, y coord 2, and points south, it's connected to x coord 2, y coord 3)
			int currX = currentEdge.getX();
			int currY = currentEdge.getY();
			int neighX = currentEdge.getNeighborX();
			int neighY = currentEdge.getNeighborY();

			Tree currTree = nodeTrees[currX][currY]; //Create references to the two relevant Trees
			Tree neighTree = nodeTrees[neighX][neighY];
			if(!currTree.connected(neighTree) && floorplan.canTearDown(currentEdge)) { //If the two nodes are not in the same Tree, merge their Trees and remove the appropriate wall from the maze
				//Make sure it can be torn down; Shouldn't be an issue but worth checking
				currTree.connect(neighTree); //Merge trees
				floorplan.deleteWallboard(currentEdge);//Delete the wallboard

			}
			//Either way, discard the edge
		}

		//Once all edges are gone, there should be one Tree of all nodes
		//Done
	}
	/**
	 * This method returns an ArrayList of all possible edges in a maze, represented as Wallboards. 
	 * Each Wallboard stores an x coord, a y coord, and a direction.
	 * Since each Wallboard can be represented in two different ways (east or west for horizontal,
	 * north or south for vertical), this method represents Wallboards
	 * as though they are south or east of a given cell.
	 * There are a total of n(m-1) + m(n-1) potential edges in the graph, not counting the borders.
	 * Creates all possible edges by: 
	 * 1) Going to the right for edges between horizontal nodes. Skips the last column to avoid the borders.
	 * 2) Down for edges between vertical nodes. Skips the last row to avoid the borders.
	 * @return List of wallboards representing all possible edges.
	 */
	private ArrayList<Wallboard> createListOfPossibleEdges(){
		//There are a total of n(m-1) + m(n-1) potential edges in the graph, not counting the borders
		//Use Wallboards to represent edges
		//Create all possible edges by going to the right for edges between horizontal nodes
		//And down for edges between vertical nodes
		ArrayList<Wallboard> tempArrayList = new ArrayList<>(height*(width - 1) + width * (height - 1));
		for(int x = 0; x < width; x++){//x is the column; skip the last column to avoid the borders
			for(int y = 0; y < height; y++) {//y is the row, skip the last row to avoid the borders
				if(x < width - 1) { //If not in the last column
					Wallboard tempWallboard = new Wallboard(x, y, CardinalDirection.East);
					tempArrayList.add(tempWallboard);
				}
				if(y < height - 1) { //If not in the last row
					Wallboard tempWallboard = new Wallboard(x, y, CardinalDirection.South);
					tempArrayList.add(tempWallboard);
				}
				
			}
		}		
		return tempArrayList; 
	}
	/**
	 * Nodes are represented as x, y coordinates. In the array of arrays of Trees that this method returns, a Tree at
	 * the x, y positions on the array corresponds to the node at the x, y positions on the Maze.
	 * Corresponds to the same coords in Floorplan.
	 * Loops through all possible x and y coordinate combinations, creating a new Tree each time within the array.
	 * @return array of array of Trees containing all possible nodes. Each Tree contains one node. 
	 */
	private Tree[][] createListOfSetsOfNodes(){
		//Loop through all possible x and y coordinate combinations, creating a new set each time with an array
		Tree[][] tempTrees = new Tree[width][height];
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				tempTrees[x][y] = new Tree();
			}
		}
		return tempTrees;
	}
	//Note from Nicholas Wilson: I did not make this Tree class. I copied it directly from here: https://github.com/psholtz/Puzzles/blob/master/mazes/maze-04/java/Kruskal.java
	//It is quite similar to the Ruby tree class from Buckblog (https://weblog.jamisbuck.org/2011/1/3/maze-generation-kruskal-s-algorithm.html). Rather than reproduce this tree myself, I decided to copy-paste the Java version from this source.
	//The purpose is to efficiently be able to merge sets, as described on Buckblog.
	/***********************************************************************
	 * 
	 * Note from Nicholas Wilson: I did not make this Tree class. I copied it directly from here: https://github.com/psholtz/Puzzles/blob/master/mazes/maze-04/java/Kruskal.java
	 * It is quite similar to the Ruby tree class from Buckblog (https://weblog.jamisbuck.org/2011/1/3/maze-generation-kruskal-s-algorithm.html). 
	 * Rather than reproduce this tree myself, I decided to copy-paste the Java version from this source.
	 * The purpose is to efficiently be able to merge sets, as described on Buckblog.
	 * We will use a tree structure to model the "set" (or "vertex") 
	 * that is used in Kruskal to build the graph.
	 * 
	 * @author psholtz
	 ***********************************************************************/
	private class Tree {
		/**
		 * A Tree that is this Tree's parent. This allows us efficiently merge sets by setting
		 * the parent of all Trees/sets to be merged to the same Tree.
		 */
		private Tree _parent = null;
		
		/**
		* Builds a new tree object with no parameters.
		*/
		public Tree() {
			
		}
		
		/**
		* If we are joined, return the root. Otherwise, return this object instance.
		* @return Either the root if joined, or this Tree if not joined.
		*/
		public Tree root() {
			return _parent != null ? _parent.root() : this;
		}
		
		/**
		* Tests if we connected to another tree.
		* @param tree		A Tree argument to test this Tree's connection to.
		* @return True if this Tree is connected to the argument Tree, that is, we have the same root. 
		*/
		public boolean connected(Tree tree) {
			return this.root() == tree.root();
		}
		
		/**
		* Connects two trees, this one and the argument Tree, by setting the argument Tree's parent
		* to this Tree's parent.
		* @param tree		A Tree to connect this Tree to.
		*/
		public void connect(Tree tree) {
			tree.root().setParent(this);
		}
		
		/**
		* Sets the parent of this Tree to the argument Tree
		* @param parent		A Tree to set as this Tree's parent.
		*/
		public void setParent(Tree parent) {
			this._parent = parent;
		}
	}
}
