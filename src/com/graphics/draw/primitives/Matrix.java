/**
 * 
 */
package com.graphics.draw.primitives;

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

	public Matrix(double[] array){
		this(); // Call default constructor to init mat instance variable
		
		if (array.length != 16){
			System.out.println("ARRAY TO SMALL!!!!");
			return;
		}
		int k = 0;
		for (int row = 0; row < length; row++){
			for (int col = 0; col < length; col++){
				System.out.println(k);
				mat[row][col] = array[k++];
			}
		}	
	}

	public double get(int x, int y){
		return mat[x][y];
	}

	public void set(int x, int y, double value){
		mat[x][y] = value;
	}

	public Matrix matMult(Matrix matrix){
		Matrix result = new Matrix();

		for (int row = 0; row < matrix.length; row++){
			for (int col = 0; col < matrix.length; col++){
				double sum = 0.0;
				for (int k = 0; k < matrix.length; k++){
					sum += this.mat[col][k] * matrix.get(k, col);
				}
				result.set(row, col, sum);
			}
		}

		return result;
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
