package com.spiru.dev.timeTask_addon;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.spiru.dev.timeTask_addon.Utils.JPanelOfElements;
import com.spiru.dev.timeTask_addon.Utils.JPanelPlayGround;
import com.spiru.dev.timeTask_addon.Utils.MyMouseListener;


/**
 * Panel auf dem alle anderen Objekte liegen
 * @author Yves
 *
 */
public class TimeTaskAddOnJPanel extends JPanel {
	
	private JPanelOfElements  jpanelOfElements;
	private JPanelPlayGround jpanelPlayground;
	private JPanel jpanelButtons;
	private JButton jbuttonDelete;
	private JButton jbuttonDeleteAll;
	private MyMouseListener mouseListener;
	
    public TimeTaskAddOnJPanel() {
    	init();
    }
    
    private void init(){
    	mouseListener = new MyMouseListener();
    	// The elements 
    	List<Object> objects = new ArrayList<Object>(); 
    	jpanelOfElements = new JPanelOfElements(objects, mouseListener);
    	jpanelButtons = new JPanel();
    	jbuttonDelete = new JButton("Markierung entfernen");
    	jbuttonDeleteAll = new JButton("Alles entfernen");    	
    	jpanelButtons.add(jbuttonDelete);
    	jpanelButtons.add(jbuttonDeleteAll);
    	
    	jpanelPlayground = new JPanelPlayGround(mouseListener);
    	mouseListener.setPlayGround(jpanelPlayground);
    	
    	JScrollPane scrollPane = new JScrollPane(jpanelOfElements);
    	scrollPane.setBounds(0,0,400,60);
    	jpanelButtons.setBounds(0,60,400,40);
    	jpanelPlayground.setBounds(0,100,400,300);    	
    	this.add(scrollPane);
    	this.add(jpanelButtons);
    	this.add(jpanelPlayground);
    	this.setLayout(null);
    	this.setBounds(0,0,400,400);
    }
}
