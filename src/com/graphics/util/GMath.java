package com.graphics.util;

public class GMath {
	
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
}
