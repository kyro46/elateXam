package com.spiru.dev.compareTextTask_addon;

import java.applet.Applet;

public class CompareTextApplet extends Applet {
	/**
	 * Initialization method that will be called after the applet is loaded into
	 * the browser.
	 */
	public void init() {
		CompareTextPanel jpanel = new CompareTextPanel();
		jpanel.setSize(600, 400);
		add(jpanel);
		this.setSize(800, 400);
	}
	@Override
	public void start() {
	}
	@Override
	public void stop() {
	}
}
