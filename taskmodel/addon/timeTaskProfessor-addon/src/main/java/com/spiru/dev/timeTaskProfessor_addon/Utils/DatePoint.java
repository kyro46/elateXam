package com.spiru.dev.timeTaskProfessor_addon.Utils;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

/**
 * JPanel with Date-String and ConnectionLine to TimeLine
 * @author Yves
 */
public class DatePoint extends JPanel{	
	
	private String caption;
	private boolean mark = false;
	private boolean visible;
	
	/**
	 * A JPanel with a Date or a TextField to insert a Date (if visible = false)
	 * @param caption The Date-String
	 * @param visible true, show Date; false, show TextField
	 */
	public DatePoint(String caption, boolean visible){
		this.setLayout(null);
		this.setBounds(0,0,70,25);
		this.caption = caption;
		this.visible = visible;
		this.addMouseListener(new ExMouseListener());
		JLabel label = new JLabel(caption);
		label.setBounds(2,2,67,22);
		this.add(label);
		// visible? -> black color, else blue
		if (visible){
			label.setForeground(Color.BLACK);
		}				
		else label.setForeground(Color.BLUE);
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
	
	public String getCaption(){
		return caption;
	}
	
	public void setMarked(boolean mark){		
		Border border = this.getBorder();
		Border margin;
		
		if (mark){
			this.mark = true;
			margin = new LineBorder(Color.YELLOW,2);
			this.setBorder(new CompoundBorder(border, margin));	
		}
		else{
			this.mark = false;
			margin = new LineBorder(Color.BLACK,1);
			this.setBorder(margin);
		}
	}
	
	public boolean isMarked(){
		return mark;
	}

}

class ExMouseListener implements MouseListener{

	public void mouseClicked(MouseEvent e) {
		DatePoint dp = (DatePoint)e.getComponent();
		dp.setMarked(!dp.isMarked());
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
