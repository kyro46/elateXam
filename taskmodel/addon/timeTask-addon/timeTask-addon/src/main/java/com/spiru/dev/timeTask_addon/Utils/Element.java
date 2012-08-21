package com.spiru.dev.timeTask_addon.Utils;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

public class Element extends JPanel {
	
	private String caption;
	private JLabel label;
	private JPanel colorPanel;
	
	public Element(String caption, Color color, MyMouseListener mouseListener){
		colorPanel = new JPanel();
		colorPanel.setBackground(color);
		this.add(colorPanel);
		this.caption = caption;		
		
		this.label = new JLabel(caption);						
		this.add(label);	
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.addMouseListener(mouseListener);
	}	
	
	public void markElement(boolean mark){
		Border border = this.getBorder();
		Border margin;
		
		if (mark){
			margin = new LineBorder(Color.YELLOW,2);
			this.setBorder(new CompoundBorder(border, margin));	
		}
		else{
			margin = new LineBorder(Color.BLACK,1);
			this.setBorder(margin);
		}
			
	}
	
	public Color getColor(){
		return colorPanel.getBackground(); 
	}
}
