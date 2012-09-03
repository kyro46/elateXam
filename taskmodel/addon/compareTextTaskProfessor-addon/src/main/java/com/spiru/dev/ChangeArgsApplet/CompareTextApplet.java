package com.spiru.dev.ChangeArgsApplet;

/*
 * YOU NEED TO DISABLE "Temporary Internet Files" in
 * Java Web Start Settings to avoid loading old JARs
 *
 * @url www.linuxquestions.org/questions/linux-software-2/firefox-java-applet-cache-551644/ 
 */

import java.applet.Applet;

@SuppressWarnings("serial")
public class CompareTextApplet extends Applet {
	private ProfessorenPanel jpanel;
	@Override
	public void init() {
		String text = this.getParameter("initialText");
		String xmldef = this.getParameter("xmlDef");
		this.setSize(800, 450);
		jpanel = new ProfessorenPanel(text, xmldef, this.getWidth(), this.getHeight());
		add(jpanel);
	}
	public String getInitialText() {
		return jpanel.getInitialText();
	}
	public String getAvaiableTags() {
		return jpanel.getAvaiableTags();
	}
	@Override
	public void start() {
	}
	@Override
	public void stop() {
	}
}
