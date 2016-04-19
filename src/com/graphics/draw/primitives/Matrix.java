/**
 * 
 */
package com.graphics.draw.primitives;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author mlekena
 *
 */
public class Matrix {

	private double[][] mat;
	public final static Integer length = 4;

	public Matrix(){
		mat = new double[length][length];
	}

	public <N extends Number> Matrix(N[] array){
		this(); // Call default constructor to init mat instance variable
		
		if (array.length != 16){
			System.out.println("ARRAY TO SMALL!!!!");
			return;
		}
		int k = 0;
		for (int row = 0; row < length; row++){
			for (int col = 0; col < length; col++){
				//System.out.println(k);
				mat[row][col] = (Double)array[k++];
			}
		}	
	}
	
	public Matrix(Vector3 u, Vector3 v, Vector3 n){
		this(new Double[]{u.x(),u.y(),u.z(),0.0,
				v.x(),v.y(),v.z(),0.0,
				n.x(),n.y(),n.z(),0.0,
				0.0,0.0,0.0,1.0});
		
	}

	public double get(int x, int y){
		return mat[x][y];
	}

	public void set(int x, int y, double value){
		mat[x][y] = value;
	}

	public Matrix multiply(Matrix matrix){
		Matrix result = new Matrix();

		for (int row = 0; row < matrix.length; row++){
			for (int col = 0; col < matrix.length; col++){
				double sum = 0.0;
				//System.out.println(">>>>>>" + sum);
				String test = "";
				for (int k = 0; k < matrix.length; k++){
					sum += this.mat[row][k] * matrix.get(k, col);
					test = mat[row][k] + "*" + matrix.get(k, col) + " = " + sum + "||";
					//System.out.println(row+":"+k + " dot " + k+":"+col + "  " + test);
				}
				result.set(row, col, sum);
			}
		}

		return result;
	}
	
	public Vector3 multiply(Vector3 v){
		ArrayList<Double> a = new ArrayList<Double>();
		Vector3 result = new Vector3();
		double cal = 0.0;
		for (int i = 0; i < length; i++){
			cal = 0.0;
			for (int k = 0; k < length; k++){
				if (k == 3){
					cal += mat[i][k];
				} else {
					cal += mat[i][k]*v.getVector()[k];
				}
			}
			a.add(cal);
		}
		
		
		return new Vector3(a.get(0), a.get(1), a.get(2));
	}
	
	public Vertex multiply(Vertex v){
		ArrayList<Double> a = new ArrayList<Double>();
		for (int row = 0; row < length; row++){
			double sum = 0.0;
			for (int col = 0; col < length; col++){
				sum += mat[row][col] * v.get(col);
			}
			a.add(sum);
		}
		return new Vertex(a.get(0), a.get(1), a.get(2), a.get(3));
	}

	public static Matrix toMatrix(double[] array){
		Matrix result = new Matrix();
		for (int row = 0; row < length; row++){
			for (int col = 0; col < length; col++){
				result.set(row, col, array[row*col]);
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String output = "";
		for (int row = 0; row < length; row++){
			for (int col = 0; col < length; col++){
				output += "["+ this.get(row,col) +"]";
			}
			output += System.getProperty("line.separator");
;
		} 
		return output;
	}



}
