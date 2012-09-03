package com.spiru.dev.timeTask_addon.Utils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MyMouseMotionListener implements MouseMotionListener {

	private JPanelPlayGround panel;
	
	public MyMouseMotionListener(JPanelPlayGround panel){
		this.panel = panel;	
	}
	
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseMoved(MouseEvent e) {
		panel.getTimeLine().setMouseOnLine_x(-20);
		if (e.getY()>200){
			// test, ob da die linie ist,
			// wenn ja, dann kleine markierung zeichnen
			if (panel.getTimeLine().getLine().intersects(e.getX()-5, e.getY()-30, 10, 50)){
				panel.getTimeLine().setMouseOnLine_x(e.getX());				
			}
		} 
		panel.repaint();
	}

}
