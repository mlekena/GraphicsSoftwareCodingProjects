/**
 * 
 */
package com.graphics.draw.primitives;

/**
 * @author mlekena
 *
 */
public class Vector {

	private double[] vector;
	
	public Vector(double x, double y, double z){
		vector = new double[3];
		vector[0] = x;
		vector[1] = y;
		vector[2] = z;
	}
	
	public Vector(int x, int y, int z){
		this((double)x, (double)y, (double)z);
	}
	
	public double x(){
		return vector[0];
	}
	
	public double y(){
		return vector[1];
	}
	
	public double z(){
		return vector[2];
	}
	
	public double[] getVector(){
		return this.vector;
	}
	
	public double dotProduct(Vector v2){
		return this.x() * v2.x() + this.y() * v2.y() + this.z() * v2.z(); 
	}
	
	public Vector crossProduct(Vector v2){
		double x = this.y() * v2.z() - this.z() * v2.y();
		double y = this.z() * v2.x() - this.x() * v2.z();
		double z = this.x() * v2.y() - this.y() * v2.x();
		return new Vector(x, y, z);
	}
	
	public double magnitude(){
		return Math.sqrt((Math.pow(x(),2)) + (Math.pow(y(), 2)) + (Math.pow(y(), 2)));
	}
	
	public double angleBetween(Vector v2){
		return Math.acos((this.dotProduct(v2)/(this.magnitude() * v2.magnitude())));
	}
	
	public Vector normalize(){
		double mag = this.magnitude();
		return new Vector(this.x()/mag, this.y()/mag, this.z()/mag);
	}
}
