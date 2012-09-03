package com.spiru.dev.timeTask_addon.Utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyListener implements KeyListener  {

	public void keyPressed(KeyEvent k) {
		//				
	}

	public void keyReleased(KeyEvent e) {
		//
	}

	public void keyTyped(KeyEvent e) {
		// only numbers and '.'
		char c = e.getKeyChar();			
		if (!Character.isDigit(c) && c != '.'){
			e.setKeyChar('\0');
		}			
	}	
}
