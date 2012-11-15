package com.spiru.dev.groupingTaskProfessor_addon.Utils;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class MyKeyListener implements KeyListener  {

	private JTextField field;
	
	public MyKeyListener(JTextField field){
		this.field = field;
	}
	
	public void keyPressed(KeyEvent k) {
		//
	}

	public void keyReleased(KeyEvent e) {
		// string is a number?
		if (field.getText().equals("n")){
			field.setForeground(Color.BLACK);
		}else
		try{
			Integer.parseInt(field.getText());
			field.setForeground(Color.BLACK);
		}
		catch (Exception ex){
			// Fehler! keine integer Zahl!
			field.setForeground(Color.RED);				
		}	
	}

	public void keyTyped(KeyEvent e) {
		// only numbers and 'n'		
		char c = e.getKeyChar();			
		if (!Character.isDigit(c) && c != 'n'){
			e.setKeyChar('\0');
		}			
	}	
}
