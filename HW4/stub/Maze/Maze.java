package Maze;
import java.util.*;
/**
 * Class that solves maze problems with backtracking.
 * @author Koffman and Wolfgang
 **/
public class Maze implements GridColors {
	
	
    /** The maze */
    private TwoDimGrid maze;

    public Maze(TwoDimGrid m) {
        maze = m;
    }

    /** Wrapper method. */
    public boolean findMazePath() {	
    	// test findAllMazePaths
    	System.out.println("All Paths:");
    	findAllMazePaths(0, 0);

    	System.out.println("");
    	System.out.println("The shortest path is");
    	// test findMazePathMin
    	findMazePathMin(0, 0);
    	

    	
        return findMazePath(0, 0); // (0, 0) is the start point.
    }

    /**
     * Attempts to find a path through point (x, y).
     * @pre Possible path cells are in BACKGROUND color;
     *      barrier cells are in ABNORMAL color.
     * @post If a path is found, all cells on it are set to the
     *       PATH color; all cells that were visited but are
     *       not on the path are in the TEMPORARY color.
     * @param x The x-coordinate of current point
     * @param y The y-coordinate of current point
     * @return If a path through (x, y) is found, true;
     *         otherwise, false
     */
    public boolean findMazePath(int x, int y) {
        // COMPLETE HERE FOR PROBLEM 1
    	int cols = maze.getNCols();
    	int rows = maze.getNRows();
  
    	// base case 1: out the grid or cell has no NON_BACKGROUND
    	if (x < 0 || x >= cols || y < 0 || y >= rows || maze.getColor(x, y) != NON_BACKGROUND) {
    		return false;
    	}
    	
    	// base case 2: arrive at the exit cell
    	
    	if (x == cols - 1 && y == rows - 1) {
    		maze.recolor(x, y, PATH);
    		return true;
    	}
    	
    	// set color to PATH
    	maze.recolor(x, y, PATH);
    	
    	// explore 4 directions, order: top -> right -> down -> left
    	int[] colOffsets = {0, 1, 0, -1};
    	int[] rowOffsets = {-1, 0, 1, 0};
    	for (int d = 0; d < 4; d++) {
    		if (findMazePath(x + colOffsets[d], y + rowOffsets[d])) {
    			return true;
    		}
    	}
    	
    	// set color to TEMPORARY means visited
    	maze.recolor(x, y, TEMPORARY);
    	
    	return false;
    }
    
    // ADD METHOD FOR PROBLEM 2 HERE
    public ArrayList<ArrayList<PairInt>> findAllMazePaths(int x, int y){
    	ArrayList <ArrayList<PairInt>> result = new ArrayList <ArrayList<PairInt>>();
    	Stack <PairInt> trace = new Stack <PairInt>();
    	// use helper function with entry point(0, 0) to start the algorithm
    	findMazePathStackBased(0, 0, result, trace);
    	
    	// output the paths as string
    	if (result.size() == 0) {
    		System.out.print("[[]]");
    	}
    	else {
    		System.out.print("[");
        	for (int i = 0; i < result.size(); i++) {
        		ArrayList<PairInt> path = result.get(i);
        		for (int j = 0; j < path.size(); j++) {
        			System.out.print(path.get(j).toString());
        			if (j < path.size() - 1) {
        				System.out.print(",");
        			}
        		}
        		System.out.print("]");
        		if (i < result.size() - 1) {
    				System.out.println(",");
    			}
        	}
    	}	
    	return result;
    }
    
    // helper function
    private void findMazePathStackBased(int x, int y, ArrayList<ArrayList<PairInt>> result, Stack<PairInt> trace) {
    	int cols = maze.getNCols();
    	int rows = maze.getNRows();
  
    	// base case 1: out the grid or cell has no NON_BACKGROUND(it may be BACKGROUND or PATH)
    	if (x < 0 || x >= cols || y < 0 || y >= rows || maze.getColor(x, y) != NON_BACKGROUND) {
    		return;
    	}
    	
    	// set color to PATH
    	maze.recolor(x, y, PATH);
    	
    	PairInt pair;
    	// create PairInt object with x and y
    	try {
    		pair = new PairInt(x, y);
    	}catch (Exception excp) {
			return;
		}
    	
    	// push the object to trace
    	trace.push(pair);
    	
    	// base case 2: arrive at the exit cell
    	if (x == cols - 1 && y == rows - 1) {
    		ArrayList<PairInt> path = new ArrayList <PairInt>();
    		path.addAll(trace);
    		result.add(path);
    		
    		// reset color and pop the stack for next possible path use
    		// reset color back to NON_BACKGROUND
        	maze.recolor(x, y, NON_BACKGROUND);
        	// pop our the trace stack
        	trace.pop();
    		return;
    	}

    	// explore 4 directions, order: top -> right -> down -> left
    	int[] colOffsets = {0, 1, 0, -1};
    	int[] rowOffsets = {-1, 0, 1, 0};
    	for (int d = 0; d < 4; d++) {
    		findMazePathStackBased(x + colOffsets[d], y + rowOffsets[d], result, trace);
    	}
    	
    	// reset color back to NON_BACKGROUND
    	maze.recolor(x, y, NON_BACKGROUND);
    	
    	// pop our the trace stack
    	trace.pop();
    	return;
    }
    
    // ADD METHOD FOR PROBLEM 3 HERE
    public ArrayList<PairInt> findMazePathMin(int x, int y){
    	ArrayList <ArrayList<PairInt>> result = new ArrayList <ArrayList<PairInt>>();
    	Stack <PairInt> trace = new Stack <PairInt>();
    	// use helper function with entry point(0, 0) to start the algorithm
    	findMazePathStackBased(0, 0, result, trace);
    	
    	// find out the shortest path
    	int minPath = Integer.MAX_VALUE;
    	ArrayList<PairInt> shortestPath = new ArrayList<PairInt>();
    	if (result.size() == 0) { // if size = 0, return empty ArrayList
    		return shortestPath;
    	}
    	else if (result.size() == 1) { // if size = 1, return the only path
    		shortestPath = result.get(0);
    	}
    	else { // if size > 1, find the smallest
    		for (int i = 0; i < result.size(); i++) {
        		ArrayList<PairInt> path = result.get(i);
        		if (path.size() < minPath) {
        			minPath = path.size();
        			shortestPath = path;
        		}
        	}
    		// now we have the shortest path
    		System.out.print("[");
    		for (int i = 0; i < shortestPath.size(); i++) {
    			System.out.print(shortestPath.get(i).toString());
    			if (i < shortestPath.size() - 1) {
    				System.out.print(",");
    			}
    		}
    		System.out.println("]");
    	}

    	return shortestPath;
    }

    /*<exercise chapter="5" section="6" type="programming" number="2">*/
    public void resetTemp() {
        maze.recolor(TEMPORARY, BACKGROUND);
    }
    /*</exercise>*/

    /*<exercise chapter="5" section="6" type="programming" number="3">*/
    public void restore() {
        resetTemp();
        maze.recolor(PATH, BACKGROUND);
        maze.recolor(NON_BACKGROUND, BACKGROUND);
    }
    /*</exercise>*/
}
/*</listing>*/
