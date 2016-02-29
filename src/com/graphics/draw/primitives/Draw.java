/**
 * 
 */
package com.graphics.draw.primitives;

import com.graphics.main.DrawAndHandleInput;
import com.jogamp.opengl.awt.GLCanvas;

/**
 * @author mlekena
 *
 */
public class Draw {

	private static boolean [][] pixels = new boolean[20][20];
	private static DrawAndHandleInput dahi = null;

	public static void line(int x0, int y0, int xEnd, int yEnd){
		LineBres(x0, y0, xEnd, yEnd);
	}
	public static void line(Coords start, Coords end){
		line(start.getX(), start.getY(), end.getX(), end.getY());
	}
	
	public static void setDrawAndHandleInput(DrawAndHandleInput _dahi){
		dahi = _dahi;
	}
	
	private static void LineBres(int x0, int y0, int xEnd, int yEnd){
		int dy = yEnd - y0;
		if (dy >= 0){///////fix
			positiveSlopelineBres(x0,y0,xEnd,yEnd);
		}
		else{
			negativeSlopelineBres(x0,y0,xEnd,yEnd);
		}
	}

	private static void positiveSlopelineBres(int x0, int y0, int xEnd, int yEnd) {
		int dx = Math.abs(xEnd - x0);
		int dy = Math.abs(yEnd - y0);
		
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
			setPixel(x,y);

			while (x < xEnd){
				x++;
				if (p < 0){
					p += twoDy;
				}
				else{
					y++;
					p+= twoDyMinusDx;
				}
				setPixel(x, y); 
			}
		}
		else{//is dy is greater than dy
			System.out.println("Got to this line");
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
			setPixel(x,y);
			
			while (y < yEnd){
				y++;
				if (p < 0){
					p += twoDx;
				}
				else{
					x++;
					p += twoDxMinusDy;
				}
				setPixel(x,y);
			}
		}
	}

	private static void negativeSlopelineBres(int x0, int y0, int xEnd, int yEnd) {
		int dx = Math.abs(xEnd - x0);
		int dy = Math.abs(yEnd - y0);
		
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
			setPixel(x,y);

			while (x > xEnd){
				x--;
				if (p < 0){
					p += twoDy;
				}
				else{
					y++;
					p+= twoDyMinusDx;
				}
				setPixel(x, y); 
			}
		}
		else{//is dy is greater than dy
			System.out.println("Got to this line");
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
			setPixel(x,y);
			
			while (y > yEnd){
				y--;
				if (p < 0){
					p += twoDx;
				}
				else{
					x++;
					p += twoDxMinusDy;
				}
				setPixel(x,y);
			}
		}
	}
	
	public static void circle( int x0, int y0,int radius){
		circleMidPoint(x0, y0, radius);
	}
	
	public static void circle(Coords center, int radius){
		circle(center.getX(), center.getY(), radius);
	}
	
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
	
	private static boolean validCoords(int xTest, int yTest, int xMaxBound, int yMaxBound){
		if (xTest >= 0 && xTest < xMaxBound && yTest >= 0 && yTest < yMaxBound){
			return true;
		}
		return false;
	}

	public static void setPixel(int x, int y){
		dahi.drawBigPixel(x, y);
	}

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

	public static void main(String [] args){
		//line (1,1,15,7);
		//line(2,2,4,15);
		//line TODO test for negative gradients
		//setPixel(1,1);
		//setPixel(4,8);
		//line(1,7,6,19);
		//line(3,3,4,1);
		circle(19,19,2);
		printArr();
	}
} 

class ScreenPt{
	
	private int x,y;
	
	public ScreenPt(){
		x = 0;
		y = 0;
	}
	
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
