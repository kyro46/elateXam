package com.spiru.dev.compareTextTask_addon;

/*
 * YOU NEED TO DISABLE "Temporary Internet Files" in
 * Java Web Start Settings to avoid loading old JARs
 *
 * @url www.linuxquestions.org/questions/linux-software-2/firefox-java-applet-cache-551644/
 */

import java.applet.Applet;


@SuppressWarnings("serial")
public class CompareTextProfessorApplet extends Applet {
	private CompareTextProfessorenPanel jpanel;
	@Override
	public void init() {
		String text = this.getParameter("initialText");
		String xmldef = this.getParameter("xmlDef");
		String solution = this.getParameter("sampleSolution");
		//this.setSize(800, 450);
		jpanel = new CompareTextProfessorenPanel(text, xmldef, solution, this.getWidth(), this.getHeight());
		//jpanel.setSize(800, 450);
		add(jpanel);
	}
	public String getInitialText() {
		return jpanel.getInitialText();
	}
	public String getAvaiableTags() {
		return jpanel.getAvaiableTags();
	}
	public String getSampleSolution() {
		return jpanel.getSampleSolution();
	}
	@Override
	public void start() {
	}
	@Override
	public void stop() {
	}
}
