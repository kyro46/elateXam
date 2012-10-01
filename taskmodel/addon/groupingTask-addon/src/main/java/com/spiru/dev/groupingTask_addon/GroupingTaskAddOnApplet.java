package com.spiru.dev.groupingTask_addon;

import java.applet.Applet;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;


public class GroupingTaskAddOnApplet extends Applet{
	
	private GroupingTaskAddOnJPanel gpanel;
	
	    @Override
	    public void init() {	
	    	int width = this.getWidth();
	    	int height = this.getHeight();
	    	this.setSize(new Dimension(width,height));
	    	this.setMinimumSize(new Dimension(width,height));
	    	this.setPreferredSize(new Dimension(width,height));
	    	this.setLayout(null);	    		    		    
	    	/*
	    	List<String> params = new ArrayList<String>();
	    	String param = this.getParameter("e0");
	    	int anz = 0;
	    	while(param != null){
	    		params.add(param);
	    		anz++;
	    		param = this.getParameter("e"+anz);	    		
	    	}
	    	
	    	String[][] elements = new String[params.size()/2][2];
	    	int j = 0;
	    	for(int k=0; k<params.size(); k++){	    		
	    		if(k % 2 == 0){
	    			// caption
	    			elements[j][0] = params.get(k);	    				    				    			
	    		}else{ 
	    			// count
	    			elements[j][1] = params.get(k);
	    			j++;
	    		}
	    	}*/
	    		boolean isHandling = Boolean.parseBoolean(this.getParameter("handling"));
	    		boolean isCorrected = Boolean.parseBoolean(this.getParameter("correction"));
				gpanel = new GroupingTaskAddOnJPanel(width, height, isCorrected);
				gpanel.load(isHandling, this.getParameter("memento"), isCorrected);
		        add(gpanel);	    		       
	    }

	    @Override
	    public void start() {
	    	//
	    }

	    @Override
	    public void stop() {
	    }	  
	    
		public String getResult() {
			return gpanel.save();
		}
		public boolean hasChanged() {
			return true;//jpanel.getRightTextAreaContent() != jpanel.getLeftTextAreaContent();
		}
}

