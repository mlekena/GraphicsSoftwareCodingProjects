/**
 * 
 */
package com.graphics.draw.primitives;

import java.util.Arrays;

/**
 * @author mlekena
 *
 */
public class Vector3 {

	

	private Double[] vector;
	
	public int length;
	
	public Vector3(double x, double y, double z){
		vector = new Double[3];
		vector[0] = x;
		vector[1] = y;
		vector[2] = z;
		length = vector.length;
	}
	
	public Vector3(int x, int y, int z){
		this((double)x, (double)y, (double)z);
	}
	
	public Vector3(){
		this(0.0,0.0,0.0);
	}
	
	public Double x(){
		return vector[0];
	}
	
	public Double y(){
		return vector[1];
	}
	
	public Double z(){
		return vector[2];
	}
	
	public Double[] getVector(){
		return this.vector;
	}
	
	public Double dotProduct(Vector3 v2){
		return this.x() * v2.x() + this.y() * v2.y() + this.z() * v2.z(); 
	}
	
	public Vector3 crossProduct(Vector3 v2){
		double x = this.y() * v2.z() - this.z() * v2.y();
		double y = this.z() * v2.x() - this.x() * v2.z();
		double z = this.x() * v2.y() - this.y() * v2.x();
		return new Vector3(x, y, z);
	}
	
	public double magnitude(){
		return Math.sqrt((Math.pow(x(),2)) + (Math.pow(y(), 2)) + (Math.pow(z(), 2)));
	}
	
	public double angleBetween(Vector3 v2){
		return Math.acos((this.dotProduct(v2)/(this.magnitude() * v2.magnitude())));
	}
	
	public Vector3 normalize(){
		double mag = this.magnitude();
		System.out.println(mag);
		return new Vector3(this.x()/mag, this.y()/mag, this.z()/mag);
	}
	
	public static Vector3 toVector3(Vertex v){
		return new Vector3(v.x(), v.y(), v.z());
	}
	
	@Override
	public String toString() {
		return "Vector3 : <" + vector[0] + ", " + vector[1] + ", " + vector[2] + ">";
	}
}
