package com.spiru.dev.groupingTask_addon;

import java.applet.Applet;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;


public class GroupingTaskAddOnApplet extends Applet{
	
	private GroupingTaskAddOnJPanel gpanel;
	
	    @Override
	    public void init() {	 	    	
	    	this.setSize(new Dimension(400,400));
	    	this.setMinimumSize(new Dimension(400,400));
	    	this.setPreferredSize(new Dimension(400,400));
	    	this.setLayout(null);
	    	
	    	String[][] elements; 
	    	try{
	    		File targetFile = new File("C:\\Users\\Yves\\Desktop\\Test.xml");
	    		Document doc = new SAXBuilder().build(targetFile);
	    		Element memento = doc.getRootElement();
	    		Element dragSubTaskDef = memento.getChild("dragSubTaskDef");
	    		List<Element> xmlList = dragSubTaskDef.getChildren("BoxContainer");
	    		elements = new String[xmlList.size()][2];
				for (int i = 0; i < xmlList.size(); i++) {
					String name = xmlList.get(i).getAttributeValue("BoxName");
					String count = xmlList.get(i).getAttributeValue("count");
					elements[i][0] = " "+name+" ";
					elements[i][1] = count;
				}
				gpanel = new GroupingTaskAddOnJPanel(elements);	        
		        add(gpanel);
		        
	    	}
	    	catch(Exception e){
	    		
	    	}	    		       
	    }

	    @Override
	    public void start() {	      
	    }

	    @Override
	    public void stop() {
	    }
}
