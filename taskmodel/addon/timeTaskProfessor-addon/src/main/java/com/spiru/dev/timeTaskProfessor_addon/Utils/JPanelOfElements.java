package com.spiru.dev.timeTaskProfessor_addon.Utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class JPanelOfElements extends JPanel {
	
	private List<DragElement> elementsList = new ArrayList<DragElement>();
	
	public JPanelOfElements(List<Object> objects, MyMouseListener mouseListener){
		this.setBackground(Color.LIGHT_GRAY);
		init(mouseListener);
	}
	
	private void init(MyMouseListener mouseListener){	
		// create Elements 
		this.addMouseListener(mouseListener);
		/*
		this.add(new Element("Wann kam ich zur Welt?", Color.GREEN, mouseListener));
		this.add(new Element("Wo muss man selbst ein Datum eintragen?", Color.BLUE, mouseListener));
		this.add(new Element("Wann war der 17.08.2011?", Color.CYAN, mouseListener));
		*/
	}
	
	public List<DragElement> getElementList(){
		return elementsList;
	}
}
