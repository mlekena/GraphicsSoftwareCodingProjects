package com.graphics.util.test;

import org.junit.Test;

public class GMathTest {

	@Test
	public void testMatMult(){
		Integer[][] testA = idMat();//new Integer[3][3];
		printMat(testA);
		Integer[][] testB = new Integer[3][3];
		testB[0][0] = 0;
		testB[0][1] = 0;
		testB[0][2] = 0;
		testB[1][0] = 0;
		testB[1][1] = 0;
		testB[1][2] = 0;
		testB[2][0] = 1;
		testB[2][1] = 1;
		testB[2][2] = 1;
		/*Integer[][] testB = new Integer[3][3];
		for (int i = 0; i < testA.length; i++){
			for (int j = 0; j < testB.length; j++){
				
			}
		}*/
	}
	
	private Integer[][] idMat(){
		Integer[][] result = new Integer[3][3];
		int count = 0;
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < result.length; j++){
				if (count == j){
					result[i][j] = 1;
				}
				else{
					result[i][j] = 0;
				}
			}
			count++;
		}
		return result;
	}
	
	private void printMat(Integer[][] mat){
		String out = "";
		for (int i = 0; i < mat.length; i++){
			out += "[";
			for (int j = 0; j < mat[0].length; j++){
				out += mat[i][j]+"_";
			}
			out += "]\n";
		}
		System.out.println(out);
	}
}
