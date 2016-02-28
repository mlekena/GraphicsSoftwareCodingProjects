/**
 * 
 */
package com.graphics.draw.primitives;

/**
 * @author mlekena
 *
 */
public class LinePoints {

	private Coords startCoords;
	private Coords endCoords;
	
	public LinePoints(int _x, int _y, int _xEnd, int _yEnd){
		startCoords = new Coords(_x,_y);
		endCoords = new Coords(_xEnd,_yEnd);
	}
	public LinePoints(Coords start, Coords end){
		this.startCoords = start;
		this.endCoords = end;
	}
	
	/**
	 * @return the x
	 */
	public int getX() {
		return startCoords.getX();
	}
	/**
	 * @return the y
	 */
	public int getY() {
		return startCoords.getY();
	}
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.startCoords.setX(x);
	}
	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.startCoords.setY(y);
	}
	
	/**
	 * @return the xEnd
	 */
	public int getxEnd() {
		return endCoords.getX();
	}

	/**
	 * @return the yEnd
	 */
	public int getyEnd() {
		return endCoords.getY();
	}

	/**
	 * @param xEnd the xEnd to set
	 */
	public void setxEnd(int xEnd) {
		this.endCoords.setX(xEnd);
	}

	/**
	 * @param yEnd the yEnd to set
	 */
	public void setyEnd(int yEnd) {
		this.endCoords.setY(yEnd);
	}
	
	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LinePoints [startCoords=" + startCoords + ", endCoords=" + endCoords + "]";
	}
}
