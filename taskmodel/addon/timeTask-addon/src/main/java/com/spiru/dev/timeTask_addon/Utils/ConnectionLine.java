package com.spiru.dev.timeTask_addon.Utils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.io.Serializable;

/**
 * Line between Symbol and TimeLine
 * @author Yves
 */
public class ConnectionLine implements Serializable {	
	// Serializable, da ConnectionLine eine Klasse, 
	// die in Symbol benutzt wird, aber bei drag'n drop sonst ein fehler kommt
	
	/** StartPoint */
	private Symbol e;
	/** EndPoint */
	private Point point;
	/** Line */
	private Line2D line;
	
	/**
	 * constructor for a ConnectionLine
	 * @param symbol StartPoint from Symbol
	 * @param x x-coordinate (EndPoint)
	 * @param y y-coordinate (EndPoint)
	 */
	public ConnectionLine(Symbol symbol, int x, int y){		
		this.e = symbol;
		this.point = new Point(x,y+3);		
	}
	
	/**
	 * draws the ConnectionLine
	 * @param g the GraphicsObject to draw on it
	 */
	public void drawConnectionLine(Graphics g){
		Graphics2D g2D = (Graphics2D)g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		line = new Line2D.Double(e.getX()+e.getWidth()/2, e.getY()+e.getHeight(), point.x, point.y);
		g2D.draw(line);
	}
	
	/**
	 * Getter for Line
	 * @return Line
	 */
	public Line2D getLine(){
		return line;
	}
}
