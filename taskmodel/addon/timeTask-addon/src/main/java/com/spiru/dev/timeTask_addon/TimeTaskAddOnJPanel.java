package com.spiru.dev.timeTask_addon;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.spiru.dev.timeTask_addon.Utils.JPanelOfElements;
import com.spiru.dev.timeTask_addon.Utils.JPanelPlayGround;
import com.spiru.dev.timeTask_addon.Utils.MyDropTargetListener;
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
	private JScrollPane scrollPanePlayground;
	
    public TimeTaskAddOnJPanel(String[][] elements) {
    	init(elements);
    }
    
    @Override
    public void paint(Graphics g){
    	scrollPanePlayground.paintAll(scrollPanePlayground.getGraphics());
    	super.paint(g);
    }
    
    private void init(String[][] elements){
    	mouseListener = new MyMouseListener();    	
    	jpanelOfElements = new JPanelOfElements(elements, mouseListener);
    	jpanelButtons = new JPanel();
    	jbuttonDelete = new JButton("Markierung entfernen");
    	jbuttonDelete.addMouseListener(mouseListener);
    	jbuttonDelete.setActionCommand("DELETE");
    	jbuttonDeleteAll = new JButton("Alles entfernen");
    	jbuttonDeleteAll.addMouseListener(mouseListener);
    	jbuttonDeleteAll.setActionCommand("DELETE_ALL");
    	jpanelButtons.add(jbuttonDelete);
    	jpanelButtons.add(jbuttonDeleteAll);
    	
    	jpanelPlayground = new JPanelPlayGround(mouseListener);
    	mouseListener.setPlayGround(jpanelPlayground);
    	new MyDropTargetListener(jpanelPlayground);
    	JScrollPane scrollPane = new JScrollPane(jpanelOfElements);
    	scrollPane.setBounds(0,0,400,60);
    	jpanelButtons.setBounds(0,60,400,40);
    	    	
    	scrollPanePlayground = new JScrollPane(jpanelPlayground);
    	scrollPanePlayground.setBounds(0,100,400,320);
    	
    	this.add(scrollPane);
    	this.add(jpanelButtons);
    	this.add(scrollPanePlayground);
    	this.setLayout(null);
    	this.setBounds(0,0,400,420);
    }
}
