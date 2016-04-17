/**
 * 
 */
package com.graphics.util;

import com.graphics.draw.primitives.Polygon;
import com.graphics.draw.primitives.Vector;
import com.graphics.draw.primitives.Vertex;

/**
 * @author mlekena
 *
 */
public class RenderParameters {
	public static Vertex v[];
	public static Polygon polygon[];

	/* Viewing parameters */
	public static Vertex vrp;
	public static Vector vpn;
	public static Vector vup;
	public static Vertex prp;
	public static double umin=0, umax=0, vmin=0, vmax=0;
	public static double frontClip=0, backClip=0;
}
