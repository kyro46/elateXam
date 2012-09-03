package com.spiru.dev.groupingTaskProfessor_addon;

import java.applet.Applet;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;


public class GroupingTaskAddOnApplet extends Applet{
	
	private GroupingTaskAddOnJPanel gpanel;
	
	    @Override
	    public void init() {	 	    	
	    	this.setSize(new Dimension(400,400));
	    	this.setMinimumSize(new Dimension(400,400));
	    	this.setPreferredSize(new Dimension(400,400));
	    	this.setLayout(null);    	
	    	String[][] elements = new String[3][3];
	    	for (int i=0; i<elements.length; i++){
	    		elements[i][0] = "Cap"+i;
	    		elements[i][1] = i+"";
	    	}
	    	elements[0][1] = "n";
	    	
	    	gpanel = new GroupingTaskAddOnJPanel(elements);	        
	        add(gpanel);
	        
	       
	    }

	    @Override
	    public void start() {	      
	    }

	    @Override
	    public void stop() {
	    }
}
