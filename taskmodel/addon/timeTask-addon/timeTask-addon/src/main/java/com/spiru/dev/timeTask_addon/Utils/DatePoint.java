package com.spiru.dev.timeTask_addon.Utils;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DatePoint extends JPanel{
	
	private boolean visible;
	private String caption;	
	
	public DatePoint(String caption, boolean visible, int x, int y){
		this.setLayout(null);
		this.setBounds(x,y,70,25);
		this.caption = caption;
		this.visible = visible;
		
		if (visible){
			JLabel label = new JLabel(caption);
			label.setBounds(2,2,67,22);
			this.add(label);
		}else{
			JTextField textfield = new JTextField(10);					
			textfield.setBounds(2,2,67,22);
			this.add(textfield);
			
		}
		
		/*
		 * DatePoints haben feste Position zur TimeLine, 
		 * Verbindung muss einmal gezeichnet werden, 
		 * von der Mitte des DatePoints zu, von der
		 * TimeLine vorgegebenen, Koordinaten. 
		 */
		
	}

}
