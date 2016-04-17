package com.graphics.util;

import com.graphics.draw.primitives.Coords2d;
import com.graphics.draw.primitives.Vector;

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
		System.out.println(xEnd+"-"+x0+" "+yEnd+" "+y0);
		System.out.println((xEnd - x0) +"<_>"+ (yEnd - y0));
		int result = (int)Math.hypot(xEnd - x0, yEnd - y0);
		System.out.println("Radius = " + result);
		return result;
	}
	
	/**
	 * @param start
	 * @param end
	 * @return
	 */
	public static int distance(Coords2d start, Coords2d end){
		return distance(start.getX(), start.getY(), end.getX(),  end.getY());
	}
	
	
}
