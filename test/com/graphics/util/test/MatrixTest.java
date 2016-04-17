/**
 * 
 */
package com.graphics.util.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.graphics.draw.primitives.Matrix;

/**
 * @author mlekena
 *
 */
public class MatrixTest {
	
	
	@Test
	public void test() {
		double [] arr = {2,0,0,0,0,2,0,0,0,0,2,0,0,0,0,2};
		double [] zero = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		Matrix mat = new Matrix(arr);
		Matrix zm = new Matrix(zero);
		
		
		
		System.out.println(mat.matMult(zm));
		//fail("Not yet implemented");
	}

}
