/**
 * 
 */
package com.graphics.draw.primitives;

/**
 * @author mlekena
 *
 */
public class Draw {

	private static boolean [][] pixels = new boolean[20][20];

	public static void line(int x0, int y0, int xEnd, int yEnd){
		lineBres(x0,y0,xEnd,yEnd);
	}

	private static void lineBres(int x0, int y0, int xEnd, int yEnd) {
		int dx = Math.abs(xEnd - x0);
		int dy = Math.abs(yEnd - y0);
		
		int x , y;

		if (dy < dx){
			int p = 2*(dy - dx);
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
			int p = 2*(dx - dy);
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

	public static void setPixel(int x, int y){
		pixels[x][y] = true;
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
		printArr();
	}
}
