package com.spiru.dev.timeTask_addon.Utils;

import java.awt.Color;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * JPanel with Date-String and ConnectionLine to TimeLine
 * @author Yves
 */
public class DatePoint extends JPanel{
	
	/**if not visible then show TextField*/
	private boolean visible;
	/** The TextField, if visible = false */
	private JTextField textfield; 
	private int id;
	private String caption;
	private Symbol sym = null;
	
	/**
	 * A JPanel with a Date or a TextField to insert a Date (if visible = false)
	 * @param caption The Date-String
	 * @param visible true, show Date; false, show TextField
	 */
	public DatePoint(String caption, boolean visible, int id, MyKeyListener keyLis){
		this.setLayout(null);
		this.id = id;
		this.setBounds(0,0,70,25);
		this.visible = visible;
		this.caption = caption;
		// visible? -> show String
		if (visible){
			JLabel label = new JLabel(caption);
			label.setBounds(2,2,67,22);
			this.add(label);
		}else{	
			// else show empty TextField
			textfield = new JTextField();
			textfield.setBounds(2,2,67,22);			
			textfield.addKeyListener(keyLis);
			this.add(textfield);			
		}	
		
		Border margin = new LineBorder(Color.BLACK,1);
		this.setBorder(margin);
	}	
	
	public void setSymbol(Symbol sym){
		this.sym = sym;
	}
	
	public Symbol getSymbol(){
		return sym;
	}
	
	public int getId(){
		return id;
	}
	
	public void setCorrected(boolean value){
		if (value == true){
			if (textfield != null){
				//textfield.setEnabled(false);
				textfield.setEditable(false);
			}
		}
	}
	
	public String getDateFromStudent(){
		if (visible){
			return "";
		}
		return textfield.getText();
	}
	
	public void setDateFromStudent(String input){
		if (visible){
			return;
		}
		textfield.setText(input);
	}
	
	/**
	 * if format is correct?
	 *  if dd.mm.jjjj then Color is black,
	 *  else red
	 */
	public void testDate(){		
		if(!visible){
			if (textfield.getText().length() != 10){
				textfield.setForeground(Color.RED);
			}
			else textfield.setForeground(Color.BLACK);
		}
	}
	
	public String getCaption(){
		return caption;
	}
	
	public boolean isDateVisible(){
		return this.visible;
	}
	
	public Date getDate(){			
		DateFormat datForm = DateFormat.getDateInstance();
		datForm.setLenient(false);
		try {
			Date date = datForm.parse(caption);
			return date;
		} catch (ParseException e) {
			return null;
		}
		
		
		
	}


}
