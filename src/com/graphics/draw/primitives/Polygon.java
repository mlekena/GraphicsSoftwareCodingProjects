package com.graphics.draw.primitives;

import com.graphics.util.RenderParameters;

public class Polygon
{

	private int numberOfVs;  // the number of vertices for this polygon
	public int vertexIndices[]; // an array of indices into the vertex array
	private int current;
	private double red;
	private double green;
	private double blue;

	public Polygon(int numVs)
	{
		numberOfVs = numVs;
		vertexIndices = new int[numVs];
		current = 0;
	}

	public void addVIndex(int idx)
	{
		vertexIndices[current] = idx;
		current++;
	}

	public void setRed(double r)
	{
		if (r >= 0)
		{
			if (r <= 1)
			{
				red = r;
			}
			else
			{
				red = 1.0;
			}

		}
		else
		{
			red = 0.0;
		}
	}

	public void setGreen(double g)
	{
		if (g >= 0)
		{
			if (g <= 1)
			{
				green = g;
			}
			else
			{
				green = 1.0;
			}

		}
		else
		{
			green = 0.0;
		}
	}

	public void setBlue(double b)
	{
		if (b >= 0)
		{
			if (b <= 1)
			{
				blue = b;
			}
			else
			{
				blue = 1.0;
			}

		}
		else
		{
			blue = 0.0;
		}
	}
	
	public double getRed()
	{
		return red;
	}

	public double getGreen()
	{
		return green;
	}

	public double getBlue()
	{
		return blue;
	}

	public int getNumVs()
	{
		return numberOfVs;
	}
	
	public String getColorInfo()
	{
		return "red: " + red + ", green: " + green + ", blue: " + blue;
	}
	
	public Vector3 normal(){
		Vertex center = RenderParameters.v[vertexIndices[0]];
		Vertex one = RenderParameters.v[vertexIndices[1]];
		Vertex end = RenderParameters.v[vertexIndices[getNumVs()-1]];
		
		Vertex dirVertexOne = one.subtract(center);
		Vertex dirVertexTwo = end.subtract(center);
		
		return Vector3.toVector3(dirVertexOne).crossProduct(Vector3.toVector3(dirVertexTwo));
	}
	
	public Vertex planeEquation(){
		Vector3 normal = normal();
		Vertex point = RenderParameters.v[vertexIndices[0]];
		double paramD = normal.x()*point.x() + normal.y()*point.y() + normal.z()*point.z();
		
		return new Vertex(normal.x(), normal.y(), normal.z(), paramD);
	}
}
