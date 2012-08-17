package com.spiru.dev.compareTextTask_addon.Utils;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.spiru.dev.compareTextTask_addon.CompareTextPanel;

public class SwitchSentenceCaretListener implements CaretListener {
	
	private CompareTextPanel panelObject;

	public SwitchSentenceCaretListener(CompareTextPanel panel) {
		super();
		panelObject = panel;
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		//System.out.println(e);
		this.panelObject.onCaretMove(e.getDot(), e.getMark());
	}

}
