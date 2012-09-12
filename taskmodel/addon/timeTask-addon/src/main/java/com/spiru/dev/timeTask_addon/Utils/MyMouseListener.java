package com.spiru.dev.timeTask_addon.Utils;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class MyMouseListener implements MouseListener {

	private DragElement selectedElement = null;
	private Symbol selectedSymbol = null;
	private ConnectionLine selectedConnectionLine = null;
	private JPanelPlayGround panel;
	
	public void setPlayGround(JPanelPlayGround panel){
		this.panel = panel;
	}
	
	private void markSymbol(boolean b){
		if (b){
			
		} else{
			if (selectedSymbol != null)
			selectedSymbol.markSymbol(false);
			selectedSymbol = null;
		}
	}
	
	public void mouseClicked(MouseEvent event) {
		Object objekt = event.getSource();
		
		if(objekt instanceof DragElement){
			if (selectedElement != null)
				selectedElement.markElement(false);
			selectedElement = (DragElement)objekt;
			selectedElement.markElement(true);
			markSymbol(false);
			if (selectedConnectionLine != null){
				selectedConnectionLine = panel.ClickLine(new Point(-1,-1));
			}
		}
		else if (objekt instanceof JPanelOfElements){
			if (selectedElement != null){
				selectedElement.markElement(false);
				selectedElement = null;
				markSymbol(false);
			}
		}
		else if (objekt instanceof JPanelPlayGround){	
			((JPanelPlayGround) objekt).requestFocus();
			if (selectedElement != null){
				panel.addSymbol(selectedElement, event.getPoint());
					selectedElement.markElement(false);
					selectedElement = null;
			} else
			if(selectedSymbol != null){
				panel.setConnectionLine(selectedSymbol);
				markSymbol(false);
			}
			else{
				selectedConnectionLine = panel.ClickLine(event.getPoint());
			}
		}
		else if (objekt instanceof Symbol){
			markSymbol(false);
			selectedSymbol = (Symbol)objekt;
			selectedSymbol.markSymbol(true);
			if (selectedElement != null){
				selectedElement.markElement(false);
				selectedElement = null;
			}
			if (selectedConnectionLine != null){
				selectedConnectionLine = panel.ClickLine(new Point(-1,-1));
			}
		}
		else if (objekt instanceof JButton){
			JButton button = (JButton)objekt;
			if (button.getActionCommand().equals("DELETE")){
				if (selectedSymbol != null){
					panel.removeSymbol(selectedSymbol);
					selectedSymbol = null;
				}
				else if (selectedConnectionLine != null){
					panel.removeConnectionLine(selectedConnectionLine);
					selectedConnectionLine = null;
				}
			}else if (button.getActionCommand().equals("DELETE_ALL")){
				if (selectedElement != null){
					selectedElement.markElement(false);
					selectedElement = null;
				}
				if (selectedSymbol != null){
					selectedSymbol.markSymbol(false);
					selectedSymbol = null;
				}
				if (selectedConnectionLine != null){
					selectedConnectionLine = null;
				}
				panel.removeAllStuff();
			}
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
