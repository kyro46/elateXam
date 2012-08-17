package com.spiru.dev.timeTask_addon.Utils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyMouseListener implements MouseListener {

	private Element selectedElement = null;
	private Symbol selectedSymbol = null;
	private JPanelPlayGround panel;
	
	public void setPlayGround(JPanelPlayGround panel){
		this.panel = panel;
	}
	
	public void mouseClicked(MouseEvent event) {
		Object objekt = event.getSource();
		
		if(objekt instanceof Element){
			if (selectedElement != null)
				selectedElement.markElement(false);
			selectedElement = (Element)objekt;
			selectedElement.markElement(true);
		}
		else if (objekt instanceof JPanelOfElements){
			if (selectedElement != null){
				selectedElement.markElement(false);
				selectedElement = null;
			}
		}
		else if (objekt instanceof JPanelPlayGround){			
			if (selectedElement != null){
				panel.addElement(selectedElement, event.getPoint());
					selectedElement.markElement(false);
					selectedElement = null;
			}
			if(selectedSymbol != null){
				panel.addConnectionLine(selectedSymbol, event.getPoint());
				selectedSymbol = null;
			}
		}
		else if (objekt instanceof Symbol){
			selectedSymbol = (Symbol)objekt;
		}
			
		
		
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
