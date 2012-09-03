package com.spiru.dev.timeTask_addon.Utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class JPanelOfElements extends JPanel {
	
	private List<Element> elementsList = new ArrayList<Element>();
	
	public JPanelOfElements(String[][] elements, MyMouseListener mouseListener){
		this.setBackground(Color.LIGHT_GRAY);
		init(elements, mouseListener);
	}
	
	private void init(String[][] elements, MyMouseListener mouseListener){	
		// create Elements 
		this.addMouseListener(mouseListener);
		
		for(int i=0; i<elements.length; i++){
			this.add(new Element(elements[i][0],Color.decode(elements[i][1]), mouseListener));
		}
	}
}
