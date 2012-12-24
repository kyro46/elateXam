package com.spiru.dev.timeTaskProfessor_addon.Utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JPanelOfElements extends JScrollPane{

	private List<DragElement> elementsList = new ArrayList<DragElement>();
	private JPanel pan;
	
	public JPanelOfElements(int width){
		super(JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		width -=15;
		this.setBounds(0, 0, width, 55);
		this.setPreferredSize(new Dimension(width, 55));
		this.setBackground(Color.black);
		
		pan = new JPanel();								
		pan.setBounds(0, 0, 15, 55);
		pan.setPreferredSize(new Dimension(15, 55));
		
		this.getViewport().add(pan);
	}	
	
	private void checkWidth(){
		int w = 15;
		for(int i=0; i<elementsList.size(); i++){
			String caption = ((DragElement)elementsList.get(i)).getCaption();
			FontMetrics fm = getFontMetrics(elementsList.get(i).getFont());			
			w += fm.stringWidth(caption)+40;
		}	
				
		pan.setBounds(0, 0, w, 55);
		pan.setPreferredSize(new Dimension(w, 55));
		this.revalidate();
	}
	
	public boolean addElement(DragElement e){
		for(DragElement n:elementsList){
			if(n.getCaption().equals(e.getCaption())){
				return false;
			}
			if (n.getColor().equals(e.getColor())){
				return false;
			}
		}
		elementsList.add(e);
		pan.add(e);
		checkWidth();
		return true;
	}
	
	public void removeElement(DragElement e){
			elementsList.remove(e);
			pan.remove(e);
			checkWidth();		
	}
}
