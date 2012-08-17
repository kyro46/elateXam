package com.spiru.dev.timeTask_addon;

import java.applet.Applet;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;


public class TimeTaskAddOnApplet extends Applet{
	
	private TimeTaskAddOnJPanel gpanel;
	
	    @Override
	    public void init() {	 	    	
	    	this.setSize(new Dimension(400,400));
	    	this.setMinimumSize(new Dimension(400,400));
	    	this.setPreferredSize(new Dimension(400,400));
	    	this.setLayout(null);	    	
	    	gpanel = new TimeTaskAddOnJPanel();	        
	        add(gpanel);
	        
	       
	    }

	    @Override
	    public void start() {	      
	    }

	    @Override
	    public void stop() {
	    }
}
