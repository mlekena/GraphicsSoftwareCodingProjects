package com.graphics.util;

import com.graphics.draw.primitives.Coords;

public class GMath {
	
	/**
	 * @param A
	 * @param B
	 * @return
	 */
	public static Long[][] SqrMatrixMult(Long[][] A, Long[][] B){
		Long[][] result = new Long[A.length][A.length];
		for (int i = 0; i < A.length; i++){
			for (int j = 0; j < A.length; j++){
				result[i][j] = new Long(0);
				for (int k = 0; k < A.length; k++){
					result[i][j] += A[i][k] * B[k][i];
				}
			}
		}
		
		return null;
	}
	
	/**
	 * @param x0
	 * @param y0
	 * @param xEnd
	 * @param yEnd
	 * @return
	 */
	public static int distance(int x0, int y0, int xEnd, int yEnd){
		return (int)Math.hypot(xEnd - x0, yEnd - y0);
	}
	
	/**
	 * @param start
	 * @param end
	 * @return
	 */
	public static int distance(Coords start, Coords end){
		return distance(start.getX(), end.getX(), start.getY(), end.getY());
	}
}
