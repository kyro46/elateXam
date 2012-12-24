package com.spiru.dev.timeTaskProfessor_addon.Utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	/** List with all DatePoints*/
	private LinkedList<DatePoint> datePoints;
	/** The TimeLine */
	private Line2D line;
	/** position of the mouse over the TimeLine */
	private int mouseOnLine_x = -20;
	private JPanelPlayGround panel;
	/**
	 * 	Constructor for a TimeLine 
	 */
	public TimeLine(JPanelPlayGround panel/* List of DatePoints *//* groesse mit angeben */){	
		// create DatePoints
		datePoints = new LinkedList<DatePoint>();
		this.line = new Line2D.Double(10,115,300,115);
		this.panel = panel;						
	}		
	
	/**
	 * Getter for the Line2D-Object
	 * @return Line2d-Object
	 */
	public Line2D getLine(){
		return line;
	}
	
	/**
	 * Getter for List of DatePoints
	 * @return List<DatePoints>
	 */
	public List<DatePoint> getDatePoints(){
		return datePoints;
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
					   135);
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
		g2D.fillOval(mouseOnLine_x-5, 110, 10, 10);						
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
	
	private void checkOrder(){
		Collections.sort(datePoints, new SortComparator());					
		ArrayList<DatePoint> points = new ArrayList<DatePoint>();
		for(int i=0; i<datePoints.size(); i++){
			points.add(datePoints.get(i));
		}
		for(int i=1; i<points.size(); i++){
			DatePoint dp1 = points.get(i-1);
		    DatePoint dp2 = points.get(i);
			if(dp1.getPos() == dp2.getPos()){
				dp1.setPos(dp1.getPos()+1);
				break;
			}
		}
		datePoints.clear();
		Collections.sort(points, new SortComparator());
		for(int i=0; i<points.size(); i++){
			DatePoint dp = points.get(i);
			dp.setPos(i+1);
			datePoints.add(dp);
		}
	}
	
	public void sortObjects(){
		if (datePoints.size() == 0)
			return;
		checkOrder();		
		datePoints.getFirst().setLocation(10, 140);
		
		for(int i = 1; i<datePoints.size(); i++){			
			int x = datePoints.get(i-1).getX()+datePoints.get(i-1).getWidth()+10;
			datePoints.get(i).setLocation(x, 140);						
		}	
		int start = datePoints.get(0).getX()+datePoints.get(0).getWidth()/2;		
		int end = datePoints.getLast().getX()+datePoints.getLast().getWidth()/2;
		this.line = new Line2D.Double(start,115,end,115);	
		for(DatePoint n:datePoints){
			if (n.getSymbol() != null){
				n.getSymbol().setLocation(n.getX()+n.getWidth()/2-10, n.getY()-100);
				ConnectionLine line = new ConnectionLine(n.getSymbol(),n.getX()+n.getWidth()/2,110);
				n.getSymbol().setConnectionLine(line);
			}
		}
		this.panel.setPreferredSize(new Dimension(end+55,panel.getHeight()));
	}
	
	public void addObject(DatePoint dp, int pos){		
		if (pos-1 >= datePoints.size())
			pos = datePoints.size()+1;	
		
	    datePoints.add(dp);		
		dp.setPos(pos);
		sortObjects();				
	}		
	
	public void removeObject(DatePoint dp){
		datePoints.remove(dp);
		sortObjects();
	}
}

class SortComparator implements Comparator<DatePoint> {       
		@Override
		public int compare(DatePoint dp1, DatePoint dp2) {
            if(dp1.getPos() > dp2.getPos())
                return 1;
            if(dp1.getPos() < dp2.getPos())
                return -1;  
            return 0;
		}
    }
