package com.spiru.dev.timeTask_addon.Utils;

import java.awt.Graphics;
import java.awt.Point;

public class ConnectionLine {
	
	private Symbol e;
	private Point point;
	
	public ConnectionLine(Symbol symbol, Point point){		
		this.e = symbol;
		this.point = point;
	}
	
	public void drawConnectionLine(Graphics g){
			g.drawLine(e.getX()+e.getWidth()/2, e.getY()+e.getHeight(), point.x, point.y);				
	}
}
