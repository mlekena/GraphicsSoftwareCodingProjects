/**
 * 
 */
package com.graphics.draw.primitives;

import java.util.ArrayList;
import java.util.Arrays;

import com.graphics.main.DrawAndHandleInput;
import com.jogamp.opengl.awt.GLCanvas;

/**
 * @author mlekena
 *
 */
public class Draw {

	private static int height = 0;
	private static int width = 0;
	private static boolean [][] pixels = new boolean[20][20];
	private static DrawAndHandleInput dahi = null;

	/**
	 * @param int x0
	 * @param int y0
	 * @param int xEnd
	 * @param int yEnd
	 */
	public static void line(int x0, int y0, int xEnd, int yEnd){
		LineBres(x0, y0, xEnd, yEnd);
	}
	/**
	 * @param Coords2d start
	 * @param Coords2d end
	 */
	public static void line(Coords2d start, Coords2d end){
		line(start.getX(), start.getY(), end.getX(), end.getY());
	}
	
	/**
	 * @param LinePoint lp
	 */
	public static void line(LinePoints lp){
		line(lp.getX(), lp.getY(), lp.getxEnd(), lp.getyEnd());
	}
	
	/**
	 * @param DrawAndHandleInput _dahi
	 */
	public static void setDrawAndHandleInput(DrawAndHandleInput _dahi){
		dahi = _dahi;
	}
	
	/**
	 * @param h
	 */
	public static void setHeight(int h){
		height = h;
	}
	
	/**
	 * @param w
	 */
	public static void setWidth(int w){
		width = w;
	}
	
	/**
	 * @param height
	 * @param width
	 */
	public static void setSize(int height, int width){
		setHeight(height);
		setWidth(width);
	}
	
	/**
	 * @param int x0
	 * @param int y0
	 * @param int xEnd
	 * @param int yEnd
	 */
	private static void LineBres(int x0, int y0, int xEnd, int yEnd){
		if (xEnd < x0){
			int xTemp = xEnd;
			int yTemp = yEnd;
			
			xEnd = x0;
			yEnd = y0;
			
			x0 = xTemp;
			y0 = yTemp;
		}
		int dy = yEnd - y0;
		Coords2d [] linePoints;
		if (dy >= 0){///////fix
			 linePoints = positiveSlopelineBres(x0,y0,xEnd,yEnd);
		}
		else{
			linePoints = negativeSlopelineBres(x0,y0,xEnd,yEnd);
		}
		
		for (Coords2d c : linePoints){
			setPixel(c.getX(), c.getY());
		}
	}

	/**
	 * @param int x0
	 * @param int y0
	 * @param int xEnd
	 * @param int yEnd
	 */
	private static Coords2d[] positiveSlopelineBres(int x0, int y0, int xEnd, int yEnd) {
		int dx = Math.abs(xEnd - x0);
		int dy = Math.abs(yEnd - y0);
		ArrayList<Coords2d> result = new ArrayList<Coords2d>();
		int x , y;

		if (dy <= dx){
			int p = 2 * dy - dx;
			int twoDy = 2 * dy;
			int twoDyMinusDx = 2 * (dy - dx);
			/* Determine which endpoint to use as a start position*/
			if(x0 > xEnd){
				x = xEnd;
				y = yEnd;
				xEnd = x0;
			}
			else{
				x = x0;
				y = y0;
			}
			//setPixel(x,y);
			result.add(new Coords2d(x,y));

			while (x < xEnd){
				x++;
				if (p < 0){
					p += twoDy;
				}
				else{
					y++;
					p+= twoDyMinusDx;
				}
				//setPixel(x, y);
				result.add(new Coords2d(x,y));
			}
			
			
		}
		else{//is dy is greater than dy
			//System.out.println("Got to this line");
			int p = 2* dx - dy;
			int twoDx = 2 * dx;
			int twoDxMinusDy = 2 * (dx - dy);
			if (y0 > yEnd){
				x = xEnd;
				y = yEnd;
				yEnd = y0;
			}
			else{
				x = x0;
				y = y0;
			}
			//setPixel(x,y);
			result.add(new Coords2d(x,y));
			
			while (y < yEnd){
				y++;
				if (p < 0){
					p += twoDx;
				}
				else{
					x++;
					p += twoDxMinusDy;
				}
				//setPixel(x,y);
				result.add(new Coords2d(x,y));
			}
		}
		
		return Arrays.copyOf(result.toArray(), result.size(), Coords2d[].class);//(Coords2d[])result.toArray();
	}

	/**
	 * @param x0
	 * @param y0
	 * @param xEnd
	 * @param yEnd
	 */
	private static Coords2d[] negativeSlopelineBres(int x0, int y0, int xEnd, int yEnd) {
		int dx = Math.abs(xEnd - x0);
		int dy = Math.abs(yEnd - y0);
		ArrayList<Coords2d> result = new ArrayList<Coords2d>();
		int x , y;

		if (dy <= dx){
			int p = 2 * dy - dx;
			int twoDy = 2 * dy;
			int twoDyMinusDx = 2 * (dy - dx);
			/* Determine which endpoint to use as a start position*/
			if(x0 < xEnd){
				x = xEnd;
				y = yEnd;
				xEnd = x0;
			}
			else{
				x = x0;
				y = y0;
			}
			//setPixel(x,y);
			result.add(new Coords2d(x,y));

			while (x > xEnd){
				x--;
				if (p < 0){
					p += twoDy;
				}
				else{
					y++;
					p+= twoDyMinusDx;
				}
				//setPixel(x, y);
				result.add(new Coords2d(x,y));
			}
		}
		else{//is dy is greater than dy
			//System.out.println("Got to this line");
			int p = 2* dx - dy;
			int twoDx = 2 * dx;
			int twoDxMinusDy = 2 * (dx - dy);
			if (y0 < yEnd){
				x = xEnd;
				y = yEnd;
				yEnd = y0;
			}
			else{
				x = x0;
				y = y0;
			}
			//setPixel(x,y);
			result.add(new Coords2d(x,y));
			
			while (y > yEnd){
				y--;
				if (p < 0){
					p += twoDx;
				}
				else{
					x++;
					p += twoDxMinusDy;
				}
				//setPixel(x,y);
				result.add(new Coords2d(x,y));
			}
		}
		return Arrays.copyOf(result.toArray(), result.size(), Coords2d[].class);   //(Coords2d[])result.toArray();
	}
	
	/**
	 * @param x0
	 * @param y0
	 * @param radius
	 */
	public static void circle( int x0, int y0,int radius){
		circleMidPoint(x0, y0, radius);
	}
	
	/**
	 * @param center
	 * @param radius
	 */
	public static void circle(Coords2d center, int radius){
		circle(center.getX(), center.getY(), radius);
	}
	
	/**
	 * @param circlePoints
	 */
	public static void circles(CirclePoints circlePoints) {
		circle(circlePoints.getX(), circlePoints.getY(), circlePoints.getRadius());
	}
	
	/**
	 * @param xC
	 * @param yC
	 * @param radius
	 */
	private static void  circleMidPoint(int xC, int yC, int radius){
		ScreenPt circPt = new ScreenPt();
		
		int param = 1 - radius;
		circPt.setCoords(0, radius);
		
		circlePlotPoints(xC, yC, circPt);
		
		while(circPt.getX() < circPt.getY()){
		//	System.out.println("calc");
			circPt.incX();
			if (param < 0){
				param += 2 *circPt.getX() + 1;
			}
			else{
				circPt.decY();
				param += 2 * (circPt.getX() - circPt.getY()) + 1;
			}
			circlePlotPoints(xC,yC, circPt);
		}
	}
	
	/**
	 * @param xCenter
	 * @param yCenter
	 * @param circPt
	 */
	private static void  circlePlotPoints(int xCenter, int yCenter, ScreenPt circPt){
		/*if (validCoords())*/	setPixel(xCenter + circPt.getX(), yCenter + circPt.getY());
		setPixel(xCenter - circPt.getX(), yCenter + circPt.getY());
		setPixel(xCenter + circPt.getX(), yCenter - circPt.getY());
		setPixel(xCenter - circPt.getX(), yCenter - circPt.getY());
		setPixel(xCenter + circPt.getY(), yCenter + circPt.getX());
		setPixel(xCenter - circPt.getY(), yCenter + circPt.getX());
		setPixel(xCenter + circPt.getY(), yCenter - circPt.getX());
		setPixel(xCenter - circPt.getY(), yCenter - circPt.getX());

	}
	
	/**
	 * @param xTest
	 * @param yTest
	 * @param xMaxBound
	 * @param yMaxBound
	 * @return
	 */
	private static boolean validCoords(int xTest, int yTest, int xMaxBound, int yMaxBound){
		if (xTest >= 0 && xTest < xMaxBound && yTest >= 0 && yTest < yMaxBound){
			return true;
		}
		return false;
	}

	/**
	 * @param x
	 * @param y
	 */
	public static void setPixel(int x, int y){
		dahi.drawBigPixel(x, y);
	}

	/**
	 * 
	 */
	public static void printArr(){
		String out = "";
		String newLine = System.getProperty("line.separator");
		for (int i = 0; i < pixels.length; i++){
			for (int j = 0; j < pixels[0].length; j++){
				if (pixels[i][j]){
					out += "[X]";
				}
				else{
					out += "[ ]";
				}
			}
			out += newLine;
		}
		System.out.println(out);
	}
	
	/**
	 * This method will scan the pixels around to test if 3 pixels have been filled and thus the intensity of the orginal pixel
	 * 
	 * @param x
	 * @param y
	 * @param grid
	 * @return
	 */
	public static int filledSquares(int x, int y, boolean [][] grid){
		int count = 0;
		for (int i = x - 1; i <= x + 1; i++){
			for (int j = y - 1; j <= y + 1; j++){
				if (grid[i][j]) count++;
				if (count == 3) return count;
			}
		}
		return count;
	}
	
	/**
	 * [1,2,1]
	 * [2,4,2]
	 * [1,2,1]
	 * 
	 * @return
	 */
	public static float squareFilter(int x, int y, boolean grid[][]){
		int [][] filter = makeFilterMask(0);
		int count = 0;
		for (int i = x - 1; i <= x + 1; i++){
			for (int j = y - 1; j <= y + 1; j++){
				if (grid[i][j]) count += filter[i][j];
			}
		}
		return count;
	}
	
	private static int [][] makeFilterMask(int type){
		switch(type){
		case 0 : int [][] squareFilter = {{1,2,1},{2,4,2},{1,2,1}};
				return squareFilter;
		case 1 : int [][] coneFilter = {{1,2,1},{2,4,2},{1,2,1}};
				return coneFilter;
		case 2 : int [][] gaussianFilter = {{1,2,1},{2,4,2},{1,2,1}};
				return gaussianFilter;
		default : int [][] d = {{1,1,1},{1,1,1},{1,1,1}};
					return d;
		}
	}
	
	private static boolean[][] createSuperSamplingArray(int x0, int y0, int xEnd, int yEnd){
		int dx = Math.abs((xEnd - x0)*3);
		int dy = Math.abs((yEnd - y0)*3);
		
		return new boolean[dx][dy];
	}
	
	

	/*public static void main(String [] args){
		//line (1,1,15,7);
		//line(2,2,4,15);
		//line TODO test for negative gradients
		//setPixel(1,1);
		//setPixel(4,8);
		//line(1,7,6,19);
		//line(3,3,4,1);
		circle(19,19,2);
		printArr();
	}*/
	
} 

/**
 * This is a helper class that is used by the circle drawing.
 * 
 * @author mlekena
 *
 */
class ScreenPt{
	
	private int x,y;
	
	/**
	 * 
	 */
	public ScreenPt(){
		x = 0;
		y = 0;
	}
	
	/**
	 * @param xValue
	 * @param yValue
	 */
	void setCoords(int xValue, int yValue){
		x = xValue;
		y = yValue;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * @return y
	 */
	public int getY() {
		return y;
	}
	
	public void incX(){
		x++;
	}
	
	public void decY(){
		y--;
	}
	
}
