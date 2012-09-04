package com.spiru.dev.groupingTask_addon;

import java.applet.Applet;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.spiru.dev.groupingTask_addon.Utils.PanelSpielplatz;


public class GroupingTaskAddOnApplet extends Applet{
	
	private GroupingTaskAddOnJPanel gpanel;
	
	    @Override
	    public void init() {	 	    	
	    	this.setSize(new Dimension(400,400));
	    	this.setMinimumSize(new Dimension(400,400));
	    	this.setPreferredSize(new Dimension(400,400));
	    	this.setLayout(null);
	    	
	    	String[][] elements = new String[2][2]; 
	    	/*
	    //	try{
	    		File targetFile = new File("C:\\Users\\Yves\\Desktop\\Test.xml");
	    		Document doc = null;
				try {
					doc = new SAXBuilder().build(targetFile);
				} catch (JDOMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
				*/
	    	elements[0][0] = "name";
	    	elements[0][1] = "6";
	    	elements[1][0] = "name323";
	    	elements[1][1] = "98";
				gpanel = new GroupingTaskAddOnJPanel(elements);	        
		        add(gpanel);		        
		        
	    //	}
	    	//catch(Exception e){
	    		
	    	//}	    		       
	    }

	    @Override
	    public void start() {
	    	//
	    }

	    @Override
	    public void stop() {
	    /*	String text = gpanel.getPlayGround().getBase64StringFromImage();
	    	System.out.println(text);
	    	*/
	    }	    	    
}
