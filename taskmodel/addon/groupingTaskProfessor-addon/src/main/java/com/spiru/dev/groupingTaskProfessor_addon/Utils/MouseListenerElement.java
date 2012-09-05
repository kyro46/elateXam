package com.spiru.dev.groupingTaskProfessor_addon.Utils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseListenerElement implements MouseListener {

	//@Override
	public void mouseClicked(MouseEvent e) {
		DragElement el = (DragElement)e.getComponent();
		el.markiereElement(!el.isElementMarked());
		
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
