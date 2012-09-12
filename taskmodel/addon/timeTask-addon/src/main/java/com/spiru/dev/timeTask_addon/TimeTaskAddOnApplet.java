package com.spiru.dev.timeTask_addon;

import java.applet.Applet;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;


public class TimeTaskAddOnApplet extends Applet{
	
	private TimeTaskAddOnJPanel gpanel;
	
	    @Override
	    public void init() {	 	    	
	    	this.setSize(new Dimension(600,600));
	    	this.setMinimumSize(new Dimension(600,600));
	    	this.setPreferredSize(new Dimension(600,600));
	    	this.setLayout(null);	  
	    	
	    	String[][] elements; 
	    	try{
	    		File targetFile = new File("C:\\Users\\wikjichess\\Dropbox\\ElateXamV2Team\\SHK\\Yves\\TimeTest.xml");
	    		Document doc;				
			    doc = new SAXBuilder().build(targetFile);				
	    		Element memento = doc.getRootElement();
	    		Element timelineSubTaskDef = memento.getChild("timelineSubTaskDef");
	    		List<Element> assignedEvent = timelineSubTaskDef.getChildren("assignedEvent");
	    		elements = new String[assignedEvent.size()][2];	    		
				for (int i = 0; i < assignedEvent.size(); i++) {
					String name = assignedEvent.get(i).getAttributeValue("name");
					String color = assignedEvent.get(i).getAttributeValue("color");
					elements[i][0] = name;
					elements[i][1] = color;
					System.out.println("s");
				}
				gpanel = new TimeTaskAddOnJPanel(elements);	        
		        add(gpanel);	
	    	}
	    	catch(Exception e){
		        e.printStackTrace();
	    	}	    	
	    }

	    @Override
	    public void start() {	   	    	
	    }

	    @Override
	    public void stop() {
	    }	    
}
