/**
 * 
 */
package com.graphics.draw.primitives;

/**
 * @author mlekena
 *
 */
public class CirclePoints {
	
	private Coords2d coordinates;
	private int radius;

	/**
	 * @param _x
	 * @param _y
	 * @param _radius
	 */
	public CirclePoints(int _x, int _y, int _radius){
		coordinates = new Coords2d(_x,_y);
		radius = _radius;
	}
	
	public CirclePoints(Coords2d c, int _radius){
		 this(c.getX(), c.getY(), _radius);
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return coordinates.getX();
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return coordinates.getY();
	}

	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.coordinates.setX(x);
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.coordinates.setY(y);
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CirclePoints [coordinates=" + coordinates + ", radius=" + radius + "]";
	}
	
	
}
