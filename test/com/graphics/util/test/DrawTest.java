/**
 * 
 */
package com.graphics.util.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.graphics.draw.primitives.Draw;

/**
 * @author mlekena
 *
 */
public class DrawTest {

	@Test
	public void testCheckFilledSquare(){
		boolean [][] testMatrixAllFalse = {{false,false,false},{false, false, false},{false, false, false}};
		assertEquals("An all false matrix returns 0", Draw.filledSquares(1,1,testMatrixAllFalse),0);
		
		boolean [][] testMatrixOneTrue = {{false,false,false},{false, false, false},{false, false, true}};
		assertEquals("A matrix with one true returns 1", Draw.filledSquares(1, 1, testMatrixOneTrue),1);
		
		boolean [][] testMatrixTwoTrue = {{false,false,true},{false, false, false},{false, false, true}};
		assertEquals("A matrix with two true returns 2", Draw.filledSquares(1, 1, testMatrixTwoTrue),2);
		
		boolean [][] testMatrixThreeTrue = {{false,false,true},{true, false, false},{false, false, true}};
		assertEquals("A matrix with three true returns 3", Draw.filledSquares(1, 1, testMatrixThreeTrue),3);
		
		boolean [][] testMatrixForMoreTrues = {{false,false,true},{true, false, false},{true, false, true}};
		assertEquals("A matrix with more than true returns 3", Draw.filledSquares(1, 1, testMatrixForMoreTrues),3);
	}
}
