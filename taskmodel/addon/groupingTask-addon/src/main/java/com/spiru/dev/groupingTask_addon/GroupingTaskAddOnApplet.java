package com.spiru.dev.groupingTask_addon;

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
	    	// read parameter	    	
	    	/* moegliche Fehler beachten!
	    	 * kein Zeichen statt zaheln
	    	 * weniger Parameter als angegeben
	    	 */	
	    	/*
	    	int[] counts = null;
    		String[] captions = null;
	    	try{
	    		int count = Integer.parseInt(getParameter("CountElements"));
	    		captions = new String[count];
	    		counts = new int[count];	    		    
	    		for(int i = 0; i<count; i++){
	    			String parameter = getParameter("e"+i);
	    			// split String in caption and count
	    			String[] splitResult = parameter.split("&");
	    			captions[i] = splitResult[0];
	    			counts[i] = Integer.parseInt(splitResult[1]);
	    		}
		        gpanel = new GroupingTaskAddOnJPanel(captions, counts);	        
		        add(gpanel);	
	    	} catch(Exception e){
	    		// Error...
	    	} 
	    	*/     	
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
