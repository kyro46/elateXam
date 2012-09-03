package com.spiru.dev.timeTask_addon.Utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class JPanelPlayGround extends JPanel {
	
	/** List with all Symbols */
	private List<Symbol> symbols = new ArrayList<Symbol>();
	private MyMouseListener mouseListener;
	/** The TimeLine */
	private TimeLine timeLine;
	
	public JPanelPlayGround(MyMouseListener mouseListener /* List of DatePoints */){
		this.setBackground(Color.LIGHT_GRAY);
		this.mouseListener = mouseListener;
		this.addMouseListener(mouseListener);		
		this.addMouseMotionListener(new MyMouseMotionListener(this));		
		timeLine = new TimeLine(this);		
		this.setPreferredSize(new Dimension((int)timeLine.getLine().getP2().getX()+40,300));
		this.setLayout(null);	
		
	}
	
	@Override
	public void paint(Graphics g){	
		super.paint(g);		
		drawTimeLine(g);
		drawConnectionLine(g);			
	}
	
	/**
	 * draws the TimeLine
	 * @param g Graphics to draw on it
	 */
	private void drawTimeLine(Graphics g){	
		timeLine.draw(g);
	}
	
	/**
	 * draws all ConnectionLines
	 * @param g Graphics to draw on it
	 */
	private void drawConnectionLine(Graphics g){
		for(Symbol n: symbols){
			n.drawConnectionLine(g);
		}
	}
	
	/**
	 * a new Symbol 
	 * @param e the original Element
	 * @param maus the Location for the Symbol
	 */
	public void addSymbol(Element e, Point maus){	
		if (maus.y<220){
			for(Symbol n: symbols){
				if (e.getColor() == n.getBackground()){
					return; // Element schon vorhanden
				}
			}		
			Symbol symbol = new Symbol(maus, e.getColor());
			symbol.addMouseListener(mouseListener);
			symbols.add(symbol);
			this.add(symbol);
		}
		this.repaint();
	}
	
	/**
	 * sets the ConnectionLine for a Symbol
	 * @param symbol Symbol to set ConnectionLine
	 */
	public void setConnectionLine(Symbol symbol){
		if (timeLine.getMouseOnLine_X()>0){
			for(Symbol n:symbols){
				if (n.getConnectionLine() == null)
					continue;
				if (n.equals(symbol))
					continue;
				int x = (int)n.getConnectionLine().getLine().getP2().getX();
				int xMouse = timeLine.getMouseOnLine_X();
				if(Math.abs(x-xMouse)<8){
					return;
				}
			}
			ConnectionLine line = new ConnectionLine(symbol, timeLine.getMouseOnLine_X(),245);		
			symbol.setConnectionLine(line);
		}
		this.repaint();
	}
	
	/**
	 * Removes a Symbol
	 * @param symbol The Symbol to remove.
	 */
	public void removeSymbol(Symbol symbol){
		this.remove(symbol);
		symbols.remove(symbol);
		repaint();
	}
	
	/**
	 * Mouseclick on ConnectionLine?
	 * @param maus the MousePoint to check
	 * @return Returns a Line or false
	 */
	public ConnectionLine ClickLine(Point maus){
		ConnectionLine line = null;
		for(Symbol n: symbols){
			n.setMarkLine(false);
		}
		for(Symbol n:symbols){
			if (n.getConnectionLine() != null)
				if (n.getConnectionLine().getLine().intersects(maus.getX()-5, maus.getY()-5, 10, 10)){
					n.setMarkLine(true);
					line = n.getConnectionLine();
					break;
				}
		}
		repaint();
		return line;
	}
	
	/**
	 * Removes a ConnectionLine
	 * @param line Line to remove
	 */
	public void removeConnectionLine(ConnectionLine line){
		for(Symbol n:symbols){
			if (n.getConnectionLine()!=null && n.getConnectionLine().equals(line)){
				n.setConnectionLine(null);
			}
		}
		repaint();
	}
	
	public TimeLine getTimeLine(){
		return timeLine;
	}
	
	/**
	 * Removes all Symbols
	 */
	public void removeAllStuff(){
		for(Symbol n:symbols){
			this.remove(n);
		}
		symbols.clear();
		repaint();
	}
	
	/**
	 * Moves the Symbol
	 * @param symbol Symbol to move
	 * @param maus new Location
	 */
	public void moveSymbol(Symbol symbol, Point maus){			
		for(Symbol n:symbols){			
			if(n.getBackground().equals(symbol.getBackground())){
				n.setLocation(maus);				
				break;
			}
		}
		repaint();
	}
}
