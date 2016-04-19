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

import com.graphics.draw.primitives.Matrix;
import com.graphics.draw.primitives.Polygon;
import com.graphics.draw.primitives.Vector3;
import com.graphics.draw.primitives.Vertex;
import com.graphics.util.RenderParameters;
import com.jogamp.opengl.DebugGL2;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
//import com.jogamp.nativewindow.util.*;

public class DrawAndHandleInput implements GLEventListener, KeyListener, MouseListener
{

	/* this object will give us access to the gl functions */
	private GL2            gl;
	/* this object will give us access to the glu functions */
	private GLU            glu;

	/* define the world coordinate limits */
	public static  final int MIN_X =-1;
	public static  final int MIN_Y =-1;
	public static  final int MAX_X =1;
	public static  final int MAX_Y =1;

	private GLCanvas canvas;

	public DrawAndHandleInput(GLCanvas c)
	{
		this.canvas = c;
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
		Polygon objects[] = RenderParameters.polygon;
		float r1, g1, b1; /* red, green and blue values */

		/* clear the color buffer */
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);


		/* sets up the current color for drawing 
	       for polygons, it's the "fill color" */
		/*gl.glColor3f(1,1,1);


		gl.glBegin(GL2.GL_POLYGON);
		// loop in here 4 or 5 times for the 4 or 5 vertices of the polygon
		//	       gl.glVertex2d( __, __ );
		gl.glEnd();*/
		for (int p = 0; p < RenderParameters.polygon.length; p++){
			Polygon poly = objects[p];
			Vertex planeE = poly.planeEquation();
			if (planeE.h() < 0){
				drawPolygon(poly);
			}
		}

		// uses the global double variables that were set when user
		// clicked as the coordinates of the bigpixel to be drawn.
		//drawBigPixel((int)bigpixelx,(int)bigpixely);

		/* force any buffered calls to actually be executed */
		gl.glFlush();


		// this will swap the buffers (when double buffering)
		// canvas.swapBuffers() should do the same thing
		drawable.swapBuffers();

	} // end display

	private void drawPolygon(Polygon poly){
		gl.glColor3f((float)poly.getRed(), (float)poly.getGreen(), (float)poly.getBlue());
		gl.glBegin(GL2.GL_POLYGON);
		for (int index = 0; index < poly.vertexIndices.length; index++){
			Vertex currentVert = RenderParameters.cache[poly.vertexIndices[index]];
			double x = (-1*currentVert.x())/currentVert.z();
			double y = (-1*currentVert.y())/currentVert.z();
			gl.glVertex2d(x , y);
		}
		gl.glEnd();
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
		double speed = 1.0;
		switch (ch) 
		{
		case 'q': 
			System.exit(0);
			break;
			//panning I think
		case 'w':
			Matrix positiveZ = new Matrix(new Double[]{1.0, 0.0, 0.0, 0.0,
					0.0, 1.0, 0.0, 0.0,
					0.0, 0.0, 1.0, speed,
					0.0, 0.0, 0.0, 1.0});
			RenderParameters.vrp = positiveZ.multiply(RenderParameters.vrp);
			RenderParameters.getPerpMatrix();
			break;
		case 's':
			Matrix negativeZ = new Matrix(new Double[]{1.0, 0.0, 0.0, 0.0,
					0.0, 1.0, 0.0, 0.0,
					0.0, 0.0, 1.0, -speed,
					0.0, 0.0, 0.0, 1.0});
			RenderParameters.vrp = negativeZ.multiply(RenderParameters.vrp);
			RenderParameters.getPerpMatrix();
			break;
		case 'a':
			Matrix positiveX = new Matrix(new Double[]{1.0, 0.0, 0.0, speed,
					0.0, 1.0, 0.0, 0.0,
					0.0, 0.0, 1.0, 0.0,
					0.0, 0.0, 0.0, 1.0});
			RenderParameters.vrp = positiveX.multiply(RenderParameters.vrp);
			RenderParameters.getPerpMatrix();
			break;
		case 'd':
			Matrix negativeX = new Matrix(new Double[]{1.0, 0.0, 0.0, -speed,
					0.0, 1.0, 0.0, 0.0,
					0.0, 0.0, 1.0, 0.0,
					0.0, 0.0, 0.0, 1.0});
			RenderParameters.vrp = negativeX.multiply(RenderParameters.vrp);
			RenderParameters.getPerpMatrix();
			break;
		case 'c':
			Matrix positiveY = new Matrix(new Double[]{1.0, 0.0, 0.0, 0.0,
					0.0, 1.0, 0.0, speed,
					0.0, 0.0, 1.0, 0.0,
					0.0, 0.0, 0.0, 1.0});
			RenderParameters.vrp = positiveY.multiply(RenderParameters.vrp);
			RenderParameters.getPerpMatrix();
			break;
		case 'x':
			Matrix negativeY = new Matrix(new Double[]{1.0, 0.0, 0.0, 0.0,
					0.0, 1.0, 0.0, -speed,
					0.0, 0.0, 1.0, 0.0,
					0.0, 0.0, 0.0, 1.0});
			RenderParameters.vrp = negativeY.multiply(RenderParameters.vrp);
			RenderParameters.getPerpMatrix();
			break;//end of Panning
			//start of rotate
		case 'i':

			break;
		case 'k':

			break;
		case 'j':

			break;
		case 'l':

			break;
		case 'm':

			break;
		case 'n':

			break;//end of rotate
			//start of zoom
		case 'f':

			break;
		case 'h':

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

		// example code for how to check which button was clicked
		if (button == MouseEvent.BUTTON1)
		{
			System.out.println("LEFT click");
		}
		else
			if (button == MouseEvent.BUTTON2)
			{
				System.out.println("MIDDLE click");
			}
			else
				if (button == MouseEvent.BUTTON3)
				{
					System.out.println("RIGHT click");
				}

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

	}

	public void mousePressed(MouseEvent me) { }

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub

	}

	//
	// End of dealing with Mouse Events
	//
	// ====================================================================================


} // end class
