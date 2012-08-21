package com.spiru.dev.timeTask_addon.Utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class JPanelOfElements extends JPanel {
	
	private List<Element> elementsList = new ArrayList<Element>();
	
	public JPanelOfElements(List<Object> objects, MyMouseListener mouseListener){
		this.setBackground(Color.LIGHT_GRAY);
		init(mouseListener);
	}
	
	private void init(MyMouseListener mouseListener){	
		// create Elements 
		this.addMouseListener(mouseListener);
		this.add(new Element("Wann kam ich zur Welt?", Color.GREEN, mouseListener));
		this.add(new Element("Element2", Color.BLUE, mouseListener));
		this.add(new Element("Langer Text, der angezeigt werden muss.", Color.CYAN, mouseListener));
	}
}
