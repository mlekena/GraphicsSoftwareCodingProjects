package com.graphics.util;

/*
   Code to read an object file and a viewing parameter file

   There is also code provided to print an object's information
   and the viewing parameter information to the console.

   For use in program 2, CS325 Computer Graphics
   Spring 2016
   Skidmore College
   Instructor: Michael Eckmann

   example usage:
     java ReadObjectAndViewingFiles house.object view1.view

     OR in Eclipse:
      enter the object file name followed by the view file name separated 
      by a space in the 
      Run, Run Configurations, Arguments tab, Program arguments text area

   Revised on 04/04/2016
      - to have polygon information (vertex indices, color) stored in a
        Polygon class
      - put all the data variables that need to be used in other classes
        as public static global variables
      - allocated space for the Vertex array and Polygon array only after
        we read the size from the object file.

*/
/*
 * file formats for the object and view files modelled after
 * that used in csc313 - Computer Graphics, Lehigh University
 * taught by G. Drew Kessler
 */
import java.io.*;
import java.util.*;
import com.graphics.draw.primitives.*;
import com.graphics.draw.primitives.Vector;

public class ReadObjectAndViewingFiles 
{

	 private static Vertex v[];
	 private static Polygon polygon[];

	/* Viewing parameters */
	 private static Vertex vrp;
	 private static Vector vpn;
	 private static Vector vup;
	 private static Vertex prp;
	 private static double umin=0, umax=0, vmin=0, vmax=0;
	 private static double frontClip=0, backClip=0;



	public static void readViewParameters(String args[]) {

		// NOTE: this program as written expects the 2 command line arguments
		// the first is the object file name 
		// the second is the viewing paramater file name
		// in Eclipse, enter these separated by a space in the 
		// Run..., Java Application name, Arguments tab, Program arguments text area
		
		/* this is Java code to read in an object file and
		 a viewing parameter file */

		BufferedReader objFileBR;
		String line, tempstr;
		StringTokenizer st;

		String objfName = args[0]; // get the object file name from first command line parameter

		int numVs = 0, numPolys = 0;

		try {
			objFileBR = new BufferedReader(new FileReader(objfName));

			line = objFileBR.readLine(); // should be the VERTICES line
			st = new StringTokenizer(line, " ");
			tempstr = st.nextToken();
			if (tempstr.equals("VERTICES")) {
				tempstr = st.nextToken();
				numVs = Integer.parseInt(tempstr);
				v = new Vertex[numVs];
			} else {
				numVs = 0;
				System.out.println("Expecting VERTICES line in file "
						+ objfName);
				System.exit(1);
			}

			line = objFileBR.readLine(); // should be the POLYGONS line
			st = new StringTokenizer(line, " ");
			tempstr = st.nextToken();
			if (tempstr.equals("POLYGONS")) {
				tempstr = st.nextToken();
				numPolys = Integer.parseInt(tempstr);
				polygon = new Polygon[numPolys];
			} else {
				System.out.println("Expecting POLYGONS line in file "
						+ objfName);
				System.exit(1);
			}

			line = objFileBR.readLine(); // should be the VERTEX LIST line
			st = new StringTokenizer(line, " ");
			tempstr = st.nextToken();
			if (tempstr.equals("VERTEX")) {
				tempstr = st.nextToken();
				if (!tempstr.equals("LIST")) {
					System.out.println("Expecting VERTEX LIST line in file "
							+ objfName);
					System.exit(1);
				}
			} else {
				System.out.println("Expecting VERTEX LIST line in file "
						+ objfName);
				System.exit(1);
			}
			// if we get here we successfully processed the VERTEX LIST line

			// reads each of the vertex coordinates and creates a Vertex object for each one 
			for (int i = 0; i < numVs; i++) {
				line = objFileBR.readLine();
				st = new StringTokenizer(line, " ");
				double x1=0, y1=0, z1=0;				
					tempstr = st.nextToken();
					x1 = Double.parseDouble(tempstr);
					tempstr = st.nextToken();
					y1 = Double.parseDouble(tempstr);
					tempstr = st.nextToken();
					z1 = Double.parseDouble(tempstr);
				v[i] = new Vertex(x1,y1,z1,1.0);
			}

			line = objFileBR.readLine(); // should be the POLYGON LIST line
			st = new StringTokenizer(line, " ");
			tempstr = st.nextToken();
			if (tempstr.equals("POLYGON")) {
				tempstr = st.nextToken();
				if (!tempstr.equals("LIST")) {
					System.out.println("Expecting POLYGON LIST line in file "
							+ objfName);
					System.exit(1);
				}
			} else {
				System.out.println("Expecting POLYGON LIST line in file "
						+ objfName);
				System.exit(1);
			}
			// if we get here we successfully processed the POLYGON LIST line

			for (int i = 0; i < numPolys; i++) {
				line = objFileBR.readLine();
				st = new StringTokenizer(line, " ");
				st.nextToken(); // ignore the string COUNT 
				tempstr = st.nextToken(); // this is the value of count (number of vertices for this poly)
				int numVsForThisPoly = Integer.parseInt(tempstr);
				polygon[i] = new Polygon(numVsForThisPoly);
				st.nextToken(); // ignore the string VERTICES 

				//example line: COUNT 5 VERTICES 5 4 3 2 1 COLOR .4 .2 .4

				for (int j = 1; j <=numVsForThisPoly; j++) {
					tempstr = st.nextToken();
					polygon[i].addVIndex(Integer.parseInt(tempstr) - 1);
				}

				st.nextToken(); // ignore the string COLOR

				tempstr = st.nextToken();
				polygon[i].setRed(Double.parseDouble(tempstr));
				tempstr = st.nextToken();
				polygon[i].setGreen(Double.parseDouble(tempstr));
				tempstr = st.nextToken();
				polygon[i].setBlue(Double.parseDouble(tempstr));

			}

			objFileBR.close();

		} catch (FileNotFoundException fnfe) {
			System.out.println("File not found");
		} catch (IOException ioe) {
			System.out.println("couldn't read from file");
		}

		// loops to print out the information just read ...
		// ******************************************************
                // ******************************************************
                // remove this printing before you submit the program
                // ******************************************************
                // ******************************************************
		
		// write code here to print out the vertices 

		for (int i = 0; i < numPolys; i++) {
			System.out.println("Polygon number " + i + ":");
			for (int j = 0; j < polygon[i].getNumVs(); j++)
				System.out.println(" " + v[polygon[i].vertexIndices[j]].toString());
			System.out.println();
			System.out.println(" with color: " + polygon[i].getColorInfo() + "\n");
		}

		// ================================================================
		// ------READ VIEWING PARAMETER FILE  
		// ================================================================

		String viewfName = args[1]; // second command line arg
		BufferedReader viewFileBR;

		try {
			viewFileBR = new BufferedReader(new FileReader(viewfName));

			line = viewFileBR.readLine(); // should be the VRP line
			st = new StringTokenizer(line, " ");
			tempstr = st.nextToken();
			if (tempstr.equals("VRP")) {
				double x1=0, y1=0, z1=0;				
					tempstr = st.nextToken();
					x1 = Double.parseDouble(tempstr);
					tempstr = st.nextToken();
					y1 = Double.parseDouble(tempstr);
					tempstr = st.nextToken();
					z1 = Double.parseDouble(tempstr);
				vrp = new Vertex(x1,y1,z1,1.0);
			} else {
				System.out.println("Expecting VRP line in file "
						+ viewfName);
				System.exit(1);
			}

			line = viewFileBR.readLine(); // should be the VPN line
			st = new StringTokenizer(line, " ");
			tempstr = st.nextToken();
			if (tempstr.equals("VPN")) {
				double x1=0, y1=0, z1=0;				
					tempstr = st.nextToken();
					x1 = Double.parseDouble(tempstr);
					tempstr = st.nextToken();
					y1 = Double.parseDouble(tempstr);
					tempstr = st.nextToken();
					z1 = Double.parseDouble(tempstr);
				vpn = new Vector(x1,y1,z1);
			} else {
				System.out.println("Expecting VPN line in file "
						+ viewfName);
				System.exit(1);
			}

			line = viewFileBR.readLine(); // should be the VUP line
			st = new StringTokenizer(line, " ");
			tempstr = st.nextToken();
			if (tempstr.equals("VUP")) {
				double x1=0, y1=0, z1=0;				
					tempstr = st.nextToken();
					x1 = Double.parseDouble(tempstr);
					tempstr = st.nextToken();
					y1 = Double.parseDouble(tempstr);
					tempstr = st.nextToken();
					z1 = Double.parseDouble(tempstr);
				vup = new Vector(x1,y1,z1);
			} else {
				System.out.println("Expecting VUP line in file "
						+ viewfName);
				System.exit(1);
			}

			line = viewFileBR.readLine(); // should be the PRP line
			st = new StringTokenizer(line, " ");
			tempstr = st.nextToken();
			if (tempstr.equals("PRP")) {
				double x1=0, y1=0, z1=0;				
					tempstr = st.nextToken();
					x1 = Double.parseDouble(tempstr);
					tempstr = st.nextToken();
					y1 = Double.parseDouble(tempstr);
					tempstr = st.nextToken();
					z1 = Double.parseDouble(tempstr);
				prp = new Vertex(x1,y1,z1,1.0);
			} else {
				System.out.println("Expecting PRP line in file "
						+ viewfName);
				System.exit(1);
			}
			
			
			line = viewFileBR.readLine(); // should be the WINDOW line
			st = new StringTokenizer(line, " ");
			tempstr = st.nextToken();
			if (tempstr.equals("WINDOW")) {
				tempstr = st.nextToken();
				umin = Double.parseDouble(tempstr);
				tempstr = st.nextToken();
				umax = Double.parseDouble(tempstr);
				tempstr = st.nextToken();
				vmin = Double.parseDouble(tempstr);
				tempstr = st.nextToken();
				vmax = Double.parseDouble(tempstr);
				
			} else {
				System.out.println("Expecting WINDOW line in file "
						+ viewfName);
				System.exit(1);
			}

			line = viewFileBR.readLine(); // should be the FRONT line
			st = new StringTokenizer(line, " ");
			tempstr = st.nextToken();
			if (tempstr.equals("FRONT")) {
				tempstr = st.nextToken();
				frontClip = Double.parseDouble(tempstr);
				
			} else {
				System.out.println("Expecting FRONT line in file "
						+ viewfName);
				System.exit(1);
			}
			line = viewFileBR.readLine(); // should be the BACK line
			st = new StringTokenizer(line, " ");
			tempstr = st.nextToken();
			if (tempstr.equals("BACK")) {
				tempstr = st.nextToken();
				backClip = Double.parseDouble(tempstr);
				
			} else {
				System.out.println("Expecting BACK line in file "
						+ viewfName);
				System.exit(1);
			}
						
			viewFileBR.close();

		} catch (FileNotFoundException fnfe) {
			System.out.println("File not found");
		} catch (IOException ioe) {
			System.out.println("couldn't read from file");
		}


		// write code here to print out the VRP, PRP, VUP and VPN
		
		System.out.print("WINDOW =");
		System.out.println(" " + umin + " " + umax + " " + vmin + " " + vmax);

		System.out.print("FRONT =");
		System.out.println(" " + frontClip);

		System.out.print("BACK =");
		System.out.println(" " + backClip);
		
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		/*
		 * Setup the global RenderParameters variables
		 * 
		 */
		
		RenderParameters.v = v;
		RenderParameters.polygon = polygon;

		/* Viewing parameters */
		RenderParameters.vrp = vrp;
		RenderParameters.vpn = vpn;
		RenderParameters.vup = vup;
		RenderParameters.prp = prp;
		RenderParameters.umin = umin;
		RenderParameters.umax = umax;
		RenderParameters.vmin = vmin;
		RenderParameters.vmax = vmax;
		RenderParameters.frontClip = frontClip;
		RenderParameters.backClip = backClip;
		

	} // end of main		

} // end of class
