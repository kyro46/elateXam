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

/**
 * JPanel with Date-String and ConnectionLine to TimeLine
 * @author Yves
 */
public class DatePoint extends JPanel{
	
	/**if not visible then show TextField*/
	private boolean visible;
	/** The TextField, if visible = false */
	private JTextField textfield; 
	
	private String caption;
	
	/**
	 * A JPanel with a Date or a TextField to insert a Date (if visible = false)
	 * @param caption The Date-String
	 * @param visible true, show Date; false, show TextField
	 */
	public DatePoint(String caption, boolean visible){
		this.setLayout(null);
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
			textfield.addKeyListener(new MyKeyListener());
			this.add(textfield);			
		}				
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
	
	private void irgendetwas(){		
		String str = "19.08.0001";
		String zwei = "18.08.1991";
		String drei = "01.01.1900";
		ArrayList<Date> dates = new ArrayList<Date>();		
	
		DateFormat datForm = DateFormat.getDateInstance();
		datForm.setLenient(false);
		try {
			Date date = datForm.parse(str);
			dates.add(date);
			Date date2 = datForm.parse(zwei);
			dates.add(date2);
			Date date3 = datForm.parse(drei);
			dates.add(date3);
			Collections.sort(dates);
			for(int i=0; i<dates.size(); i++){
				System.out.println(dates.get(i));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
