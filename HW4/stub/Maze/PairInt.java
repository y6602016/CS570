package Maze;

public class PairInt {
	private int x;
	private int y;
	
	// constructor
	public PairInt(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	// get x value
	public int getX() {
		return this.x;
	}
	
	// get y value
	public int getY() {
		return this.y;
	}
	
	// set x value
	public void setX(int x) {
		this.x = x;
	}
	
	// set y value
	public void setY(int y) {
		this.y= y; 
	}
	
	// return this object equals to p or not
	public boolean equals(Object p) {
		return this == p;
	}
	
	// convert x and y value as string "(x,y)"
	public String toString() {
		return "(" + String.valueOf(this.x) + "," + String.valueOf(this.y) + ")";
	}
	
	// return new copy object with same x and y
	public PairInt copy() {
		return new PairInt(this.x, this.y);
	}
}
