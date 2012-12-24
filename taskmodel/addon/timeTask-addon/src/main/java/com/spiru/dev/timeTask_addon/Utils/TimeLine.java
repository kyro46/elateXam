package com.spiru.dev.timeTask_addon.Utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 * TimeLine, draw Line and stores DatePoints
 * @author Yves
 */
public class TimeLine {	
	private LinkedList<DatePoint> datePoints;
	/** The TimeLine */
	private Line2D line;
	/** position of the mouse over the TimeLine */
	private int mouseOnLine_x = 2000;
	
	/**
	 * 	Constructor for a TimeLine 
	 */
	public TimeLine(){	
		// create DatePoints		
		this.line = new Line2D.Double(10,250,400-10,250);
		datePoints = new LinkedList<DatePoint>();
	}
	
	/**
	 * Getter for List of DatePoints
	 * @return List<DatePoints>
	 */
	public List<DatePoint> getDatePoints(){
		return datePoints;
	}
	
	
	public void sortDatePoints(LinkedList<DatePoint> datePoints){
		if (datePoints.size() == 0)
			return;
		//checkOrder();		
		int pos = (int)getLine().getY1()+25;
		datePoints.getFirst().setLocation(10, pos);
		
		for(int i = 1; i<datePoints.size(); i++){			
			int x = datePoints.get(i-1).getX()+datePoints.get(i-1).getWidth()+10;
			datePoints.get(i).setLocation(x, pos);						
		}	
		int start = datePoints.get(0).getX()+datePoints.get(0).getWidth()/2;		
		int end = datePoints.getLast().getX()+datePoints.getLast().getWidth()/2;
		
		this.line = new Line2D.Double(start,250,end,250);
	}	
	
	/**
	 * Getter for the Line2D-Object
	 * @return Line2d-Object
	 */
	public Line2D getLine(){
		return line;
	}	
	
	/**
	 * draws the timeline 
	 * @param g Graphics to draw on it
	 */
	public void draw(Graphics g){		
		// thickness = 4		
		Graphics2D g2D = (Graphics2D) g;			
		g2D.setStroke(new BasicStroke( 4 ));
		// draw Line
		g2D.draw(line);
				
		int start_x = (int)line.getP1().getX();
		int end_x = (int)line.getP2().getX();
		int start_y = (int)line.getP1().getY();
		// draw start- and endLine
		g2D.drawLine(start_x,start_y-15, start_x, start_y+15);
		g2D.drawLine(end_x,start_y-15, end_x, start_y+15);
		
		// thickness = 1
		g2D.setStroke(new BasicStroke( 1 ));
		// draw a line from DatePoint to timeLine	
		for(DatePoint n:datePoints){
			g2D.drawLine(n.getX()+n.getWidth()/2,
					   (int)line.getP1().getY(),
					   n.getX()+n.getWidth()/2,
					   267);
		}
		// Mouse on TimeLine?
		drawDotOnTimeLine(g2D);
	}
	
	/**
	 * Mouse on TimeLine? 
	 * -> draw red circle
	 * @param g2D
	 */
	private void drawDotOnTimeLine(Graphics2D g2D){
		g2D.setColor(Color.RED);	
		
		for (DatePoint n:datePoints){
			int distance = n.getX()+n.getWidth()/2 - mouseOnLine_x;
			if( distance >= -20 && distance <= 20){
				mouseOnLine_x = n.getX()+n.getWidth()/2;
				break;
			}
		}		
		g2D.fillOval(mouseOnLine_x-5, 245, 10, 10);						
		g2D.setColor(Color.BLACK);
	}
	
	/**
	 * Getter for position of the mouse over the TimeLine
	 * @return position of the mouse over the TimeLine
	 */
	public int getMouseOnLine_X(){
		return mouseOnLine_x;
	}
	
	/**
	 * Setter for position of the mouse over the TimeLine
	 * @param x position of the mouse over the TimeLine
	 */
	public void setMouseOnLine_x(int x){
		mouseOnLine_x = x;
	}
	
	
}
