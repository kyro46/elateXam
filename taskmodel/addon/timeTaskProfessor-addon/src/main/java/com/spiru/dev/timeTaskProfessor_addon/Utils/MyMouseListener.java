package com.spiru.dev.timeTaskProfessor_addon.Utils;

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
	
	public void clearSelection(){		
		selectedConnectionLine = null;
		markSymbol(false);
		if (selectedElement != null)
			selectedElement.markElement(false);
		selectedElement = null;		
		panel.repaint();		
	}
	
	public void mouseClicked(MouseEvent event) {
		Object objekt = event.getSource();
		
		if(objekt instanceof DragElement){
			if (selectedElement != null)
				selectedElement.markElement(false);
			selectedElement = (DragElement)objekt;
			selectedElement.markElement(true);			
			markSymbol(false);
			panel.editElement(selectedElement);			
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
			panel.closeEditView();
			if (selectedElement != null){
				panel.addSymbol(selectedElement, event.getPoint());
				panel.closeEditView();
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
			panel.closeEditView();
			selectedSymbol.markSymbol(true);
			panel.editSymbol(selectedSymbol);
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
			if (button.getActionCommand().equals("Delete Selection")){
				if (selectedSymbol != null){
					panel.removeSymbol(selectedSymbol);
					selectedSymbol = null;
				}
				else if (selectedConnectionLine != null){
					panel.removeConnectionLine(selectedConnectionLine);
					selectedConnectionLine = null;
				}
			}else if (button.getActionCommand().equals("Delete All")){
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
			panel.closeEditView();
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
