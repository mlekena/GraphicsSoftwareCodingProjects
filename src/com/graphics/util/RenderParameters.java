/**
 * 
 */
package com.graphics.util;

import java.util.ArrayList;

import com.graphics.draw.primitives.Matrix;
import com.graphics.draw.primitives.Polygon;
import com.graphics.draw.primitives.Vector3;
import com.graphics.draw.primitives.Vertex;

/**
 * @author mlekena
 *
 */
public class RenderParameters {
	public static Vertex cache[];
	public static Polygon polygon[];
	public static Vertex v[];
	//public static 
	//public static ArrayList<Vertex[]> historyOfRenderedVertices;

	/* Viewing parameters */
	public static Vertex vrp;
	public static Vector3 vpn;
	public static Vector3 vup;
	public static Vertex prp;
	public static double umin=0, umax=0, vmin=0, vmax=0;
	public static double frontClip=0, backClip=0;

	private static Matrix Nper = null;
	private static boolean rerender = false;

	public static Matrix getPerpMatrix(){
			Double[] vrpOriginTranlationMatrix = {1.0,0.0,0.0,-vrp.x(),
					0.0,1.0,0.0,-vrp.y(),
					0.0,0.0,1.0,-vrp.z(),
					0.0,0.0,0.0,1.0};
			Matrix vrpToOrigin = new Matrix(vrpOriginTranlationMatrix);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>CALCULATING VIEW VALUES<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			System.out.println(vrpToOrigin);

			// Calculate the directional values of the View Reference Point
			System.out.println("VPN : " + vpn);
			Vector3 n = vpn.normalize();
			System.out.println("N :" + n);
			Vector3 crossUN = vup.crossProduct(n);
			Vector3 u = crossUN.normalize();
			System.out.println("U : " + u);
			Vector3 v = n.crossProduct(u);
			System.out.println("V : " + v);
			
			System.out.println(u.dotProduct(v));
			System.out.println(v.dotProduct(v));
			System.out.println(n.dotProduct(v));

			Matrix r = new Matrix(u,v,n);

			System.out.println();
			System.out.println("CALCULATING VIEW NORMALS");
			System.out.println("VPN NORMAL (n): " + n);
			System.out.println("VUP NORMAL: (u)" + u);
			System.out.println("V: " + v);
			System.out.println("R: " + r);

			Matrix prpToOrigin = new Matrix(new Double[]{1.0,0.0,0.0,-prp.x(),
					0.0,1.0,0.0,-prp.y(),
					0.0,0.0,1.0,-prp.z(),
					0.0,0.0,0.0,1.0});

			System.out.println("prp Origin: " + prpToOrigin);

			double shx = ((((umin + umax))/2)-prp.x())/ prp.z(); //(prp.x() - (umax - umin))/2/(-1*prp.z()); //
			double shy = ((((vmin + vmax))/2)-prp.y())/ prp.z(); //(prp.y() - (vmax - vmin))/2/(-1*prp.z());// (((umin + umax)*prp.y())/2-prp.z()) + 1/prp.y();

			Matrix shper = new Matrix(new Double[]{1.0, 0.0, shx, 0.0,
					0.0, 1.0, shy, 0.0,
					0.0, 0.0, 1.0, 0.0,
					0.0, 0.0, 0.0, 1.0});
			System.out.println("SHper: " + shper);

			double sx1 = (2*prp.z())/(umax - umin);
			double sy1 = (2*prp.z())/(vmax - vmin);

			Matrix s1 = new Matrix(new Double[]{sx1, 0.0, 0.0, 0.0,
					0.0, sy1, 0.0, 0.0,
					0.0, 0.0, 1.0, 0.0,
					0.0, 0.0, 0.0, 1.0});

			double sz = -1/(- prp.z() + backClip);

			Matrix s2 = new Matrix(new Double[]{sz, 0.0, 0.0, 0.0,
					0.0, sz, 0.0, 0.0,
					0.0, 0.0, sz, 0.0,
					0.0, 0.0, 0.0, 1.0});

			System.out.println("s1: " + s1);
			System.out.println("s2: " + s2);

			Matrix scaleMat = s1.multiply(s2);

			System.out.println("scale: " + scaleMat);

			Matrix nper = scaleMat.multiply(shper).multiply(prpToOrigin).multiply(r).multiply(vrpToOrigin);
			System.out.println("Nper: " + nper);
			
			Nper = nper;
			updateVectices();
		
		return Nper;
		
	}
	
	private static void updateVectices(){
		if (cache == null){
			cache = new Vertex[v.length];
		}
		Vertex newVertexSet[] = new Vertex[cache.length];
		for (int i = 0; i < cache.length; i++){
			newVertexSet[i] = Nper.multiply(v[i]);
		}
		cache = newVertexSet;
		
	}
}
