package com.spiru.dev.timeTask_addon.Utils;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class TimeLine {
	
	private List<DatePoint> datePoints;
	private JPanelPlayGround panel;
	
	public TimeLine(JPanelPlayGround panel/* List of DatePoints *//* groesse mit angeben */){
		this.panel = panel;
		// create DatePoints
		datePoints = new ArrayList<DatePoint>();
		// panel.add(datePoints);
		
		// StartDatePoint
		panel.add(new DatePoint("19.08.1991",true,1,270));
		// EndDatePoint
		panel.add(new DatePoint("17.08.2011",true, 400-75, 270));
		
		DatePoint test = new DatePoint("20.04.1992",false, 100, 270);
		datePoints.add(test);
		panel.add(test);
		/*
		 * zähle DatePoints und setzte Markierung auf timeLine
		 * erstelle DatePoints und ziehe Verbindung zur timeLine
		 * -> roter rahmen, rote verbindung, roter punkt auf timeLine?
		 */
		
	}
	
	public void draw(Graphics g){
		// timeline
		g.drawLine(10,panel.getHeight()-50, panel.getWidth()-10, panel.getHeight()-50);
		g.drawLine(10,panel.getHeight()-50-15, 10, panel.getHeight()-50+15);
		g.drawLine(panel.getWidth()-10,panel.getHeight()-50-15, panel.getWidth()-10, panel.getHeight()-50+15);
		
		// draw a line from DatePoint to timeLine
		for(DatePoint n:datePoints){
			g.drawLine(n.getX()+n.getWidth()/2,
					   n.getY(),
					   n.getX()+n.getWidth()/2,
					   panel.getHeight()-50-10);
		}
	}
}
