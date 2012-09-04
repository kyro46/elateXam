package com.spiru.dev.compareTextTask_addon;

/*
 * YOU NEED TO DISABLE "Temporary Internet Files" in
 * Java Web Start Settings to avoid loading old JARs
 *
 * @url www.linuxquestions.org/questions/linux-software-2/firefox-java-applet-cache-551644/ 
 */

import java.applet.Applet;

@SuppressWarnings("serial")
public class CompareTextApplet extends Applet {
	private CompareTextPanel jpanel;
	/**
	 * Initialization method that will be called after the applet is loaded into
	 * the browser.
	 */
	@Override
	public void init() {
		//this.getParameter("..."); // HTML: <parma name="" value=""></param>
		// http://docs.oracle.com/javase/tutorial/deployment/applet/invokingAppletMethodsFromJavaScript.html
		// http://stackoverflow.com/questions/7278626/javascript-to-java-applet-communication
		String text = this.getParameter("initialText");
		String xmldef = this.getParameter("xmlDef");
		this.setSize(800, 400);
		jpanel = new CompareTextPanel(text, xmldef, this.getWidth(), this.getHeight());
		jpanel.setSize(800, 400);
		add(jpanel);
	}
	public String getResult() {
		return "";//return jpanel.getRightTextAreaContent();
	}
	@Override
	public void start() {
	}
	@Override
	public void stop() {
	}
}
