/**
 * 
 */
package com.graphics.util.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.graphics.draw.primitives.Matrix;
import com.graphics.draw.primitives.Vertex;

/**
 * @author mlekena
 *
 */
public class MatrixTest {
	
	
	@Test
	public void test() {
		Double [] arr = {2.0,0.0,0.0,0.0,0.0,2.0,0.0,0.0,0.0,0.0,2.0,0.0,0.0,0.0,.00,2.0};
		Double [] zero = {1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0};
		Matrix mat = new Matrix(arr);
		Matrix zm = new Matrix(zero);
		
		System.out.println(mat.multiply(zm));
		
		Vertex v = new Vertex(1,2,3,4);
		
		Matrix m = new Matrix(new Double[]{1.0,0.0,0.0,1.0,
											0.0,1.0,0.0,1.0,
											0.0,0.0,1.0,1.0,
											0.0,0.0,0.0,1.0});
		System.out.println(m.multiply(v));
		//fail("Not yet implemented");
	}

}
/*
 * 1,0,0,1 1
 * 0,1,0,1 2
 * 0,0,1,1 3
 * 0,0,0,1 4
 * 
 * 1*1+0+0+1*4 = 
 * */