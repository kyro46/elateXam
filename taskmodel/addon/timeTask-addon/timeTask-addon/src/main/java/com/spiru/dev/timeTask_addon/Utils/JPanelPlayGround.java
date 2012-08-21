package com.spiru.dev.timeTask_addon.Utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

public class JPanelPlayGround extends JPanel {
	
	private List<Symbol> symbols = new ArrayList<Symbol>();
	private MyMouseListener mouseListener;
	private TimeLine timeLine;
	
	public JPanelPlayGround(MyMouseListener mouseListener /* List of DatePoints */){
		this.setBackground(Color.GRAY);
		this.mouseListener = mouseListener;
		this.addMouseListener(mouseListener);
		this.setLayout(null);		
		timeLine = new TimeLine(this);			
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		drawTimeLine(g);
		drawConnectionLine(g);		
	}
	
	private void drawTimeLine(Graphics g){
		// draw Line
		// draw first and last Date
		// draw given dates (only border, when visible flag is false)
		timeLine.draw(g);
	}
	
	private void drawConnectionLine(Graphics g){
		for(Symbol n: symbols){
			n.drawConnectionLine(g);
		}
	}
	
	public void addElement(Element e, Point maus){		
		for(Symbol n: symbols){
			if (e.getColor() == n.getBackground()){
				return; // Element schon vorhanden
			}
		}		
		Symbol symbol = new Symbol(maus, e.getColor());
		symbol.addMouseListener(mouseListener);
		symbols.add(symbol);
		this.add(symbol);		
		this.repaint();
	}
	
	public void addConnectionLine(Symbol symbol, Point maus){
		ConnectionLine line = new ConnectionLine(symbol, maus);
		symbol.setConnectionLine(line);
		this.repaint();
	}
	
	public void removeElement(Element e){
		
	}
	
	public void removeConnectionLine(ConnectionLine line){
		
	}
}
