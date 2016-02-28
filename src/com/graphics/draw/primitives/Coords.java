/**
 * 
 */
package com.graphics.draw.primitives;

/**
 * @author mlekena
 *
 */
public class Coords {

	private int x;
	private int y;
	
	
	/**
	 * @param x
	 * @param y
	 */
	public Coords(int x, int y){
		this.x = x;
		this.y = y;
	}
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Coords [x=" + x + ", y=" + y + "]";
	}
	
	
}
