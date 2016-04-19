package com.graphics.draw.primitives;
import java.util.Arrays;

/**
 * @author mlekena
 *
 */
public class Vector4 {





	private Double[] vector;

	public int length;

	public Vector4(double x, double y, double z, double h){
		vector = new Double[4];
		vector[0] = x;
		vector[1] = y;
		vector[2] = z;
		vector[3] = h;
		length = vector.length;
	}

	public Vector4(int x, int y, int z, int h){
		this((double)x, (double)y, (double)z, (double)h);
	}

	public Vector4(){
		this(0.0,0.0,0.0,0.0);
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

	public Double h(){
		return vector[3];
	}

	public Double[] getVector(){
		return this.vector;
	}

	public Double dotProduct(Vector4 v2){
		return this.x() * v2.x() + this.y() * v2.y() + this.z() * v2.z() + this.h() * v2.h(); 
	}

	/*public Vector4 crossProduct(Vector4 v2){
			double x = this.y() * v2.z() - this.z() * v2.y();
			double y = this.z() * v2.x() - this.x() * v2.z();
			double z = this.x() * v2.y() - this.y() * v2.x();
			double h = this.y() * v2.h() - this.h() * v2.h();
			return new Vector4(x, y, z, h);
		}*/

	public double magnitude(){
		return Math.sqrt((Math.pow(x(),2)) + (Math.pow(y(), 2)) + (Math.pow(y(), 2))+ (Math.pow(y(), 2)));
	}

	/*public double angleBetween(Vector3 v2){
			return Math.acos((this.dotProduct(v2)/(this.magnitude() * v2.magnitude())));
		}*/

	public Vector4 normalize(){
		double mag = this.magnitude();
		return new Vector4(this.x()/mag, this.y()/mag, this.z()/mag, this.h()/mag);
	}

	@Override
	public String toString() {
		return "Vector4 : <" + vector[0] + ", " + vector[1] + ", " + vector[2] + vector[3] + ">";
	}
}


