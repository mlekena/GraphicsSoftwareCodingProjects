package com.graphics.main;

/*

Example program for CS325

Author: Michael Eckmann

Updated to work with JOGL 2.3.2 (from October 2015 build)

This class DrawAndHandleInput which in a GLEventListener as well as a KeyListener and MouseListener
displays a grid of "big" pixels for student use to add code to draw Bresenham Lines, Circles, and
do antialiasing.

 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Dimension2D;
import java.util.Arrays;

import javax.swing.plaf.synth.SynthSeparatorUI;

import com.graphics.draw.primitives.CirclePoints;
import com.graphics.draw.primitives.Coords2d;
import com.graphics.draw.primitives.Draw;
import com.graphics.draw.primitives.LinePoints;
import com.graphics.util.DrawModes;
import com.graphics.util.GMath;
import com.graphics.util.Prompter;
import com.jogamp.opengl.DebugGL2;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;

import javafx.scene.shape.DrawMode;
//import com.jogamp.nativewindow.util.*;

public class DrawAndHandleInput1 implements GLEventListener, KeyListener, MouseListener
{

	/* this object will give us access to the gl functions */
	private GL2 gl;
	/* this object will give us access to the glu functions */
	private GLU glu;

	/* define the world coordinate limits */
	public static  final int MIN_X =0;
	public static  final int MIN_Y =0;
	public static  final int MAX_X =250;
	public static  final int MAX_Y =200;

	// height and width of the bigpixel area in world coordinate measurements
	public static final int BIGAREA_HEIGHT = 200;
	public static final int BIGAREA_WIDTH = 200;

	// number of rows and columns of big pixels to appear in the grid
	// eventually these will be set by user input or via a command line
	// parameter.  That is functionality you need to add for program 02.
	public static int BIGPIXEL_ROWS = 60;
	public static int BIGPIXEL_COLS = 60;

	// globals that hold the coordinates of the big pixel that is to be
	// "turned on"
	// you may want to change the way this is done.
	public double bigpixely=0;
	public double bigpixelx=0;
	
	//indexes to the current collection storage for a line and circle
	private int lineIndex = 0, circleIndex = 0;
	
	//click coordinates management data
	private Coords2d currentLeftClick = null;
	private Coords2d currentRightClick = null;
	
	// max nUmber of primitives to allow to be drawn
	int maxNumberOfPrimitives = 3;
	//lines collection
	private LinePoints [] renderedLines = new LinePoints[maxNumberOfPrimitives];
	
	//circle collection
	private CirclePoints[] renderedCircles = new CirclePoints[maxNumberOfPrimitives];
		
	public final int COLORRED = 1;
	public int newcolor = 2;
	private GLCanvas canvas;

	private DrawModes mode = DrawModes.LINE;

	public DrawAndHandleInput1(GLCanvas c)
	{
		this.canvas = c;
		/*Integer [] gridSize = Prompter.getScreenSize();
		BIGPIXEL_COLS =gridSize[0];
		BIGPIXEL_ROWS = gridSize[1];*///TODO uncomment before submittle
		Draw.setDrawAndHandleInput(this);
		Draw.setSize(BIGAREA_HEIGHT, BIGAREA_WIDTH);
	}
	// ====================================================================================
	//
	// Start of the methods in GLEventListener
	//

	/**
	   =============================================================
	   This method is called by the drawable to do initialization. 
	   =============================================================

	   @param drawable The GLCanvas that will be drawn to

	 */
	public void init(GLAutoDrawable drawable)
	{
		this.gl = drawable.getGL().getGL2();
		this.glu = new GLU(); // from demo for new version

		/* Set the clear color to black */
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		/* sets up the projection matrix from world to window coordinates */
		gl.glMatrixMode(GL2.GL_PROJECTION);

		gl.glLoadIdentity();
		/* show the whole world within the window */
		glu.gluOrtho2D(MIN_X, MAX_X, MIN_Y, MAX_Y);

		/* sets up the modelview matrix */
		/* ignore this for now
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, 0.0f);
		 */

		// wraps the GL to provide error checking and so
		// it will throw a GLException at the point of failure
		drawable.setGL( new DebugGL2(drawable.getGL().getGL2() ));

	} // end init

	/**
	   =============================================================
	   This method is called when the screen needs to be drawn.
	   =============================================================

	   @param drawable The GLCanvas that will be drawn to

	 */
	public void display(GLAutoDrawable drawable)
	{
		float r1, g1, b1; /* red, green and blue values */

		/* clear the color buffer */
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

		r1 = 0.5f;
		g1 = 0.5f;
		b1 = 0.0f;



		/* sets up the current color for drawing 
	       for polygons, it's the "fill color" */
		gl.glColor3f(1,1,1);

		// draw the "big pixel area" 
		gl.glBegin(GL2.GL_POLYGON);
		// These are the vertices of the polygon using world coordinates 
		gl.glVertex2i( 0, 0);  // vertex 1 
		gl.glVertex2i( BIGAREA_WIDTH, 0);  
		gl.glVertex2i( BIGAREA_WIDTH, BIGAREA_HEIGHT);
		gl.glVertex2i( 0, BIGAREA_HEIGHT);  
		gl.glEnd();

		// draw the "small pixel area" to the right of the "big pixel area" 
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d( BIGAREA_WIDTH, 
				BIGAREA_HEIGHT - 5.0*(MAX_Y-MIN_Y)/8);
		gl.glVertex2d( BIGAREA_WIDTH + 0.2*(MAX_X-MIN_X), 
				BIGAREA_HEIGHT - 5.0*(MAX_Y-MIN_Y)/8);
		gl.glVertex2d( BIGAREA_WIDTH + 0.2*(MAX_X-MIN_X), 
				BIGAREA_HEIGHT - 3.0*(MAX_Y-MIN_Y)/8);
		gl.glVertex2d( BIGAREA_WIDTH, 
				BIGAREA_HEIGHT - 3.0*(MAX_Y-MIN_Y)/8);
		gl.glEnd();

		// change color to black for grid lines
		gl.glColor3f(0,0,0);

		// draw the vertical lines for the grid
		for (int col=0; col <= BIGPIXEL_COLS; col++)
		{

			gl.glBegin(GL2.GL_LINES);
			gl.glVertex2d((double)col*BIGAREA_WIDTH/BIGPIXEL_COLS, 0);
			gl.glVertex2d((double)col*BIGAREA_WIDTH/BIGPIXEL_COLS, BIGAREA_HEIGHT);
			gl.glEnd();

		}

		// draw the horizontal lines for the grid
		for (int row=0; row <= BIGPIXEL_ROWS; row++)
		{
			gl.glBegin(GL2.GL_LINES);
			gl.glVertex2d(0, (double)row*BIGAREA_HEIGHT/BIGPIXEL_ROWS);
			gl.glVertex2d(BIGAREA_WIDTH,(double)row*BIGAREA_HEIGHT/BIGPIXEL_ROWS);
			gl.glEnd();
		}
		
		drawLines();
		drawCircles();
		// uses the global double variables that were set when user
		// clicked as the coordinates of the bigpixel to be drawn.
		if(currentLeftClick != null){
			drawBigPixel(currentLeftClick);
		}
		
		if(currentRightClick != null){
			drawBigPixel(currentRightClick);
		}

		/* force any buffered calls to actually be executed */
		gl.glFlush();


		// this will swap the buffers (when double buffering)
		// canvas.swapBuffers() should do the same thing
		drawable.swapBuffers();

	} // end display

	
	/**
	 * parses through the renderedLines and calls the Draw.line method on all the stored lines
	 */
	private void drawLines(){
		for (int i = 0; i < renderedLines.length; i++){
			if (renderedLines[i] != null) {
				Draw.line(renderedLines[i]);
			}
			else{
				break;
			}
		}
	}
	
	public void drawCircles(){
		for (int i = 0; i < renderedCircles.length; i++){
			if (renderedCircles[i] != null){
				Draw.circles(renderedCircles[i]);
			}
			else{
				break;
			}
		}
	}

	/* 

	 method name: drawBigPixel

	    takes in "big pixel" coordinates and displays the "big pixel" (polygon)
	    associated with those coordinates.
	    Note: the "big pixel" area is situated so that
	       0, 0 is the highest-leftmost big pixel and
	       BIXPIXEL_COLS - 1, BIGPIXELROWS - 1 is the lowest-rightmost big pixel

	 parameters:
	    x - the x coordinate of the big pixel
	    y - the y coordinate of the big pixel

	 */
	public void drawBigPixel(int x, int y)
	{
		if (x >BIGPIXEL_COLS-1 || x < 0 || y > BIGPIXEL_ROWS-1 || y < 0){
			return;
		}
		// because the y screen coordinates increase as we go down 
		// and the y world coordinates increase as we go up
		// we need to compute flip_y which will be the y coordinate
		// of the big pixel if the big pixel coordinates' y values
		// increased as we go up
		int flip_y = Math.abs((BIGPIXEL_ROWS-1) - y);
		gl.glColor3d(1.0, 0, 0);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d((double)x*BIGAREA_HEIGHT/BIGPIXEL_ROWS, 
				(double)flip_y*BIGAREA_WIDTH/BIGPIXEL_COLS);
		gl.glVertex2d((double)(x+1)*BIGAREA_HEIGHT/BIGPIXEL_ROWS, 
				(double)flip_y*BIGAREA_WIDTH/BIGPIXEL_COLS);
		gl.glVertex2d((double)(x+1)*BIGAREA_HEIGHT/BIGPIXEL_ROWS, 
				(double)(flip_y+1)*BIGAREA_WIDTH/BIGPIXEL_COLS);
		gl.glVertex2d((double)x*BIGAREA_HEIGHT/BIGPIXEL_ROWS, 
				(double)(flip_y+1)*BIGAREA_WIDTH/BIGPIXEL_COLS);
		gl.glEnd();

	}
	
	public void drawBigPixel(Coords2d c){
		drawBigPixel(c.getX(), c.getY());
	}
	/**
	   =============================================================
	   This method is called when the window is resized.
	   =============================================================

	   @param drawable The GLCanvas that will be drawn to

	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
	{  
		// System.out.println("In reshape");
	}

	/**
	   =============================================================
	   Called by the drawable when the display mode or the display 
	   device associated with the GLDrawable has changed.
	   =============================================================

	   @param drawable The GLCanvas that will be drawn to
	   @param modeChanged  not implemented
	   @param deviceChanged  not implemented

	 */
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged)
	{
		// System.out.println("In displayChanged");
	}

	//
	// End of methods in GLEventListener
	//
	// ====================================================================================



	// ====================================================================================
	// Deal with the keyboard events
	// KeyListener require the following methods to be 
	// specified:
	//
	//  keyReleased
	//  keyPressed
	//  keyTyped

	public void keyReleased(KeyEvent ke) {
	}
	public void keyPressed(KeyEvent ke) {
	}
	public void keyTyped(KeyEvent ke) {
		char ch = ke.getKeyChar();
		switch (ch) {
		case 'q': 
			System.exit(0);
			break;
		case 'r': 
			newcolor = COLORRED;
			break;
		case 'c':
			mode = DrawModes.CIRCLE;
			System.out.println("Draw mode to Circles");
			break;
		case 'l':
			mode = DrawModes.LINE;
			System.out.println("Draw mode to Lines");
			break;
		}
	} // end keyTyped

	//
	// End of dealing with Keyboard Events
	//
	// ====================================================================================



	// ====================================================================================
	//
	// Deal with the mouse events
	// MouseListener require the following methods to be 
	// specified:
	//
	//  mouseReleased
	//  mouseEntered
	//  mouseExited
	//  mouseClicked
	//  mousePressed

	public void mouseReleased(MouseEvent me) { }
	public void mouseEntered(MouseEvent me) { }
	public void mouseExited(MouseEvent me) { }
	public void mouseClicked(MouseEvent me) { 

		// to get the coordinates of the event 
		int x, y;
		// to store which button was clicked, left, right or middle
		int button;

		x = me.getX();
		y = me.getY();

		System.out.println("x = " + x + " y = " + y);
		button = me.getButton();



		/*
		   getButton() - returns an int which should be compared against
		      BUTTON1, BUTTON2, or BUTTON3
		   the left button is BUTTON1
		   the middle button is BUTTON2
		   the right button is BUTTON3

		   these are the correct designations for linux,
		   they should also be correct for windows, but
		   I recall some kind of difference for mac os.
		   try it and see

		 */

		// get the current size of the canvas
		Dimension2D d = canvas.getSize();

		//System.out.println("height of canvas = " + d.height + " and width of canvas = " + d.width);
		//System.out.println("x = " + x + " and y = " + y);

		// using the x,y screen coordinates of the point that was clicked and
		// using the current size of the canvas and the number of 
		// bigpixel rows and columns and the min and max of the 
		// world coordinates, compute the coordinates of the bigpixel
		// where bigpixelx increases from left to right starting at 0 and going to
		// BIGPIXEL_COLS - 1
		// and
		// bigpixely increases from top to bottom starting at 0 and going to
		// BIGPIXEL_ROWS - 1
		//
		bigpixely = y / (d.getHeight() / BIGPIXEL_ROWS);
		bigpixelx = x / ((d.getWidth()  / ((double)(MAX_X - MIN_X) / (MAX_Y - MIN_Y))) / BIGPIXEL_COLS);

		//
		// if either the x or y coordinate of the big pixel is "out of bounds"
		// place it at the nearest big pixel "in bounds"
		//
		if (bigpixelx >= BIGPIXEL_COLS)
			bigpixelx = BIGPIXEL_COLS - 1;
		if (bigpixely >= BIGPIXEL_ROWS)
			bigpixely = BIGPIXEL_ROWS - 1;
		if (bigpixelx < 0)
			bigpixelx = 0;
		if (bigpixely < 0)
			bigpixely = 0;

		// example code for how to check which button was clicked
		if (button == MouseEvent.BUTTON1)
		{
			System.out.println("LEFT click");
			onLeftClick((int)bigpixelx, (int)bigpixely);
		}
		else if (button == MouseEvent.BUTTON2)
		{
			System.out.println("MIDDLE click");
		}
		else if (button == MouseEvent.BUTTON3)
		{
			System.out.println("RIGHT click");
			onRightClick((int)bigpixelx, (int)bigpixely);
		}
		//System.out.println("bigpixel x, y  = " + bigpixelx + ", " + bigpixely);
	}

	private void onRightClick(int bigpixelx2, int bigpixely2) {
		currentRightClick = new Coords2d(bigpixelx2, bigpixely2);
		addPrimitive(currentLeftClick);
		
	}
	
	private void onLeftClick(int bigpixelx2, int bigpixely2) {
		currentLeftClick = new Coords2d(bigpixelx2, bigpixely2);
		addPrimitive(currentRightClick);
	}
	private void addPrimitive(Coords2d secondCoord){
		if (secondCoord != null){
			switch (mode){
			case LINE: renderedLines[lineIndex] = new LinePoints(currentLeftClick, currentRightClick);
						incrementLineIndex();
						printArray(renderedLines);
						break;
			case CIRCLE: int radius = GMath.distance(currentLeftClick, currentRightClick);
						//System.out.println(radius);
						renderedCircles[circleIndex] = new CirclePoints(currentLeftClick, radius);
						incrementCircleIndex();
						printArray(renderedCircles);
						break;
			}
			currentRightClick = null;
			currentLeftClick = null;
		}
	}

	
	
	private void incrementCircleIndex() {
		circleIndex = (circleIndex + 1) % maxNumberOfPrimitives;
	}

	private void incrementLineIndex() {
		lineIndex = (lineIndex + 1) % maxNumberOfPrimitives;
	}


	public void mousePressed(MouseEvent me) { }

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub

	}
	
	private <T> void printArray(T[] arr){
		for (int i = 0; i < arr.length; i++){
			if (arr[i] != null)	System.out.println(arr[i] + "");
		}
	}

	//
	// End of dealing with Mouse Events
	//
	// ====================================================================================


} // end class