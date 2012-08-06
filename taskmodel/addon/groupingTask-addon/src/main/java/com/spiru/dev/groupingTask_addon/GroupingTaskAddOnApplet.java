package com.spiru.dev.groupingTask_addon;

import java.applet.Applet;


public class GroupingTaskAddOnApplet extends Applet{
	
	private GroupingTaskAddOnJPanel gpanel;
	
	

	
	    @Override
	    public void init() {
	        gpanel = new GroupingTaskAddOnJPanel();
	       
	        add(gpanel);
	    }


	    @Override
	    public void start() {
	       
	    }

	    @Override
	    public void stop() {
	        }

}
