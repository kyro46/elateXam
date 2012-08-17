package com.spiru.dev.timeTask_addon.Utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Symbol extends JPanel {	
	
	private ConnectionLine line;
	
	public Symbol(Point maus, Color color){		
		this.setLayout(null);
		this.setBackground(color);
		this.setBounds(maus.x, maus.y, 20,20);	
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}	
	
	public void setConnectionLine(ConnectionLine line){
		this.line = line;
	}
	
	public void drawConnectionLine(Graphics g){
		if (line != null)
			line.drawConnectionLine(g);
	}
		
}
