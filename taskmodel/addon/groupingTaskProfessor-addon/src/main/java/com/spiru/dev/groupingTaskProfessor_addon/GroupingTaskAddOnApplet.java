package com.spiru.dev.groupingTaskProfessor_addon;

import java.applet.Applet;
import java.awt.Dimension;


@SuppressWarnings("serial")
public class GroupingTaskAddOnApplet extends Applet{

	private GroupingTaskAddOnJPanel gpanel;

	@Override
	public void init() {	 	    	
		this.setSize(new Dimension(600,400));
		this.setMinimumSize(new Dimension(600,400));
		this.setPreferredSize(new Dimension(600,400));
		this.setLayout(null);    	

		// Get Parameter from HTML <applet> Tag
		String mementostr = getParameter("memento");

		gpanel = new GroupingTaskAddOnJPanel(mementostr);	        
		add(gpanel);
	}

	/**
	 * Interface for Javascript, accessible via
	 *   document.getElementsById("appletid").getMemento()
	 *
	 * @return XML containing the Memento Part of the Addon as String 
	 */
	public String getMemento() {
		return gpanel.save();
	}

	@Override
	public void start() {	      
	}

	@Override
	public void stop() {
	}
}
