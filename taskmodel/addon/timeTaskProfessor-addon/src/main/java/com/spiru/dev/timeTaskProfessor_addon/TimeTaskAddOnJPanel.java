package com.spiru.dev.timeTaskProfessor_addon;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jdom.Document;

import com.spiru.dev.timeTaskProfessor_addon.Utils.DatePoint;
import com.spiru.dev.timeTaskProfessor_addon.Utils.Element;
import com.spiru.dev.timeTaskProfessor_addon.Utils.JPanelEditor;
import com.spiru.dev.timeTaskProfessor_addon.Utils.JPanelOfElements;
import com.spiru.dev.timeTaskProfessor_addon.Utils.JPanelPlayGround;
import com.spiru.dev.timeTaskProfessor_addon.Utils.MyDropTargetListener;
import com.spiru.dev.timeTaskProfessor_addon.Utils.MyMouseListener;


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
	private JScrollPane scrollPane;
	private TimeTaskAddOnApplet applet;
	
    public TimeTaskAddOnJPanel(TimeTaskAddOnApplet applet) {
    	this.applet = applet;
    	init();
    }
    
    @Override
    public void paint(Graphics g){
    	scrollPanePlayground.paintAll(scrollPanePlayground.getGraphics());
    	super.paint(g);
    }
    
    private void init(){
    	mouseListener = new MyMouseListener();
    	// The elements 
    	List<Object> objects = new ArrayList<Object>(); 
    	jpanelOfElements = new JPanelOfElements(objects, mouseListener);
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
    	scrollPane = new JScrollPane(jpanelOfElements);
    	scrollPane.setBounds(0,130,400,60);
    	jpanelButtons.setBounds(0,190,400,40);
    	    	
    	scrollPanePlayground = new JScrollPane(jpanelPlayground);
    	scrollPanePlayground.setBounds(0,230,400,170);
    	
    	JPanelEditor panelInput = new JPanelEditor(this, mouseListener);
    	panelInput.setBounds(0,0,400,130);
    	
    	JButton buttonSave = new JButton("Save");
    	buttonSave.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evt) {
				buttonActionSave();
				
			}
    		
    	});
    	buttonSave.setBounds(
    			10,
    			scrollPanePlayground.getY()+scrollPanePlayground.getHeight()+5,
    			370,
    			25
    			);
    	
    	this.add(panelInput);
    	this.add(scrollPane);
    	this.add(jpanelButtons);
    	this.add(scrollPanePlayground);
    	this.add(buttonSave);
    	this.setLayout(null);    	
    	this.setBounds(0,0,400,460);
    }
    
    private void buttonActionSave(){
    	applet.save(jpanelOfElements.getElementList(), jpanelPlayground.getTimeLine().getDatePoints(),
    			jpanelPlayground.getSymbols());
    }
    
    public void addElement(Element e){
    	for(Element n:jpanelOfElements.getElementList()){
    		if (e.getColor() == n.getColor() || e.getCaption().equals(n.getCaption())){
    			return;
    		}
    	}
    	jpanelOfElements.getElementList().add(e);
    	jpanelOfElements.add(e);
    	scrollPane.paintAll(scrollPane.getGraphics());
    }
    
    public void deleteElement(){
    	List<Element> elementList = jpanelOfElements.getElementList();
    	List<Element> deleteList = new ArrayList<Element>();
    	for(Element n:elementList){
    		if(n.isMarked()){
    			jpanelOfElements.remove(n);
    			deleteList.add(n);
    		}
    	}
    	elementList.removeAll(deleteList);
    	scrollPane.paintAll(scrollPane.getGraphics());
    }
    
    public void addDatePoint(DatePoint d){
    	List<DatePoint> datePoints = jpanelPlayground.getTimeLine().getDatePoints();
    	for(DatePoint n:datePoints){
    		if(n.getCaption().equals(d.getCaption())){
    			return;
    		}
    	}
    	datePoints.add(d);
    	jpanelPlayground.add(d);
    	
    	// fill
    	List<Date> dates = new ArrayList<Date>();
    	for(DatePoint n:datePoints){
    		dates.add(n.getDate());
    	}    	
    	// sort
    	boolean sort = true;
    	while(sort){
    		sort=false;
    		for(int i=1; i<dates.size(); i++){
    			if (dates.get(i-1).after(dates.get(i))){
    				Date tmp = dates.get(i-1);
    				dates.set(i-1, dates.get(i));
    				dates.set(i, tmp);
    				DatePoint dp = datePoints.get(i-1);
    				datePoints.set(i-1, datePoints.get(i));
    				datePoints.set(i, dp);
    				sort = true;
    			}
    		}
    	}
    	
    	jpanelPlayground.getTimeLine().sortDatePoints();   
    	scrollPanePlayground.paintAll(scrollPanePlayground.getGraphics());    	
    }
    
    public void deleteDate(){
    	List<DatePoint> datePoints = jpanelPlayground.getTimeLine().getDatePoints();
    	List<DatePoint> dates = new ArrayList<DatePoint>();
    	for(DatePoint n:datePoints){
    		if(n.isMarked()){
    			dates.add(n);
    			jpanelPlayground.remove(n);
    		}
    	}
    	datePoints.removeAll(dates);
    	jpanelPlayground.getTimeLine().sortDatePoints();   
    	scrollPanePlayground.paintAll(scrollPanePlayground.getGraphics()); 
    }
        
}
