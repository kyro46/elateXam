package com.spiru.dev.groupingTaskProfessor_addon.Utils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseListenerElement implements MouseListener {

	private DragElement element = null;
	
	//@Override
	public void mouseClicked(MouseEvent e) {
		DragElement el = (DragElement)e.getComponent();
		if (element != null){
			element.markiereElement(false);
			if (el == element){
				element = null;	
				return;
			}
			element = null;					
		}		
		if (el.isElementMarked()){
			el.markiereElement(false);			
		}else{
			el.markiereElement(true);
			element = el;			
		}
				
		
	}

	//@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
