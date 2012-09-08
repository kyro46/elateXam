package com.spiru.dev.compareTextTask_addon;

/*
 * YOU NEED TO DISABLE "Temporary Internet Files" in
 * Java Web Start Settings to avoid loading old JARs
 *
 * @url www.linuxquestions.org/questions/linux-software-2/firefox-java-applet-cache-551644/ 
 */

import java.applet.Applet;

import javax.xml.bind.DatatypeConverter;

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
		String xmldef = this.getParameter("xmlDef"); // is expected to contain Base64 representation of avaiableTasks part in Memento
		byte[] xmldefs = null; // DocumentBuilder will need it as BytesArray later
		if(xmldef != null) xmldefs = DatatypeConverter.parseBase64Binary(xmldef);
		//this.setSize(800, 400);
		jpanel = new CompareTextPanel(text, xmldefs, this.getWidth(), this.getHeight());
		//jpanel.setSize(800, 400);
		add(jpanel);
	}
	public String getResult() {
		return jpanel.getRightTextAreaContent();
	}
	@Override
	public void start() {
	}
	@Override
	public void stop() {
	}
}
