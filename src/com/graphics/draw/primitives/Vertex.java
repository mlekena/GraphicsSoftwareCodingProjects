/**
 * 
 */
package com.graphics.draw.primitives;

import java.util.Arrays;

/**
 * @author mlekena
 *
 */
public class Vertex {

	private double[] points;
	
	public Vertex(double x, double y, double z, double h){
		points = new double[4];
		points[0] = x;
		points[1] = y;
		points[2] = z;
		points[3] = h;
	}
	
	public Vertex(double x, double y, double z){
		this(x,y,z,1);
	}
	
	public double x(){
		return points[0];
	}
	
	public double y(){
		return points[1];
	}
	
	public double z(){
		return points[2];
	}
	
	public double h(){
		return points[4];
	}
	
	public double get(int index){
		return points[index];
	}

	@Override
	public String toString() {
		return "Vector => [x: "+ points[0] +
				", y: "+ points[1] + 
				", z: "+ points[2] +
				", h: " + points[3] + "]";
	}
	
	public Vertex addition(Vertex v){
		return new Vertex(x() + v.x(),
				y() + v.y(),
				z() + v.z(),
				h() + v.h());
	}
	
	public Vertex subtract(Vertex v){
		return new Vertex(x() - v.x(),
				y() - v.y(),
				z() - v.z(),
				h() - v.h());
	}
	
	
}
