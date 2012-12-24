package com.spiru.dev.timeTaskProfessor_addon;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.spiru.dev.timeTaskProfessor_addon.Utils.DatePoint;
import com.spiru.dev.timeTaskProfessor_addon.Utils.DragElement;
import com.spiru.dev.timeTaskProfessor_addon.Utils.JPanelButtons;
import com.spiru.dev.timeTaskProfessor_addon.Utils.JPanelEditor;
import com.spiru.dev.timeTaskProfessor_addon.Utils.JPanelOfElements;
import com.spiru.dev.timeTaskProfessor_addon.Utils.JPanelPlayGround;
import com.spiru.dev.timeTaskProfessor_addon.Utils.MyDropTargetListener;
import com.spiru.dev.timeTaskProfessor_addon.Utils.MyMouseListener;
import com.spiru.dev.timeTaskProfessor_addon.Utils.Symbol;

public class TimeTaskAddOnJPanel extends JScrollPane {
	
	private JPanel view;	
	private JPanelEditor panelEditor;
	private JPanelOfElements panelOfElements;
	private JPanelPlayGround panelPlayground;
	private JPanelButtons panelButtons;
	private MyMouseListener mouseListener;	
	private JScrollPane scrollPanePlayground;
	
	public TimeTaskAddOnJPanel (int width, int height){
		super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.setBounds(0, 0, width, height);
		this.setPreferredSize(new Dimension(width, height));		
		mouseListener = new MyMouseListener();
				
		createView(width);
		
		this.getViewport().add(view);	
	}
	
	private void createView(int width){
		view = new JPanel();
		view.setLayout(null);			
		
		panelEditor = new JPanelEditor(width, this, mouseListener);
		view.add(panelEditor);
		
		panelOfElements = new JPanelOfElements(width);
		panelOfElements.setLocation(0, panelEditor.getHeight());
		view.add(panelOfElements);
		
		panelPlayground = new JPanelPlayGround(mouseListener, this);
		mouseListener.setPlayGround(panelPlayground);		
		new MyDropTargetListener(panelPlayground);
    	scrollPanePlayground = new JScrollPane(panelPlayground, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    	scrollPanePlayground.setBounds(0,panelEditor.getHeight()+panelOfElements.getHeight(),width,210);
    	
		view.add(scrollPanePlayground);
		
		panelButtons = new JPanelButtons(mouseListener);
		panelButtons.setBounds(0, panelEditor.getHeight()+panelOfElements.getHeight()+scrollPanePlayground.getHeight(), width, 35);
		view.add(panelButtons);
		
		int height = panelEditor.getHeight()+panelOfElements.getHeight()+scrollPanePlayground.getHeight()+panelButtons.getHeight();
		view.setBounds(0, 0, width, height);	
		view.setPreferredSize(new Dimension(width, height));
		this.revalidate();
		
		// EditorPanel
	}
	
	public void sortObjects(){
		panelPlayground.getTimeLine().sortObjects();
		panelPlayground.repaint();
	}
	
	public void addSymbol(Symbol sym){
		panelPlayground.addSymbol(sym);
	}
	
	public boolean addObject(DatePoint dp, int pos){
		return panelPlayground.addObject(dp,pos);		
	}
	
	public void removeObject(DatePoint dp){
		panelPlayground.removeObject(dp);
	}
	
	public void editSymbol(Symbol sym){
		panelEditor.editSymbol(sym);
	}
	
	public void editElement(DragElement e){
		panelEditor.editElement(e, panelPlayground.getSymbols());		
	}
	
	public boolean addElement(DragElement e){
		return panelOfElements.addElement(e);
	}
	
	public void removeElement(DragElement e, Symbol sym){
		if (e!=null)
			panelOfElements.removeElement(e);
		if (sym != null)
			panelPlayground.removeSymbol(sym);
	}
	
	public void closeEditView(){
		panelEditor.closeObjectView();
		panelEditor.closeSymbolView();
	}
	
	public void removeSymbol(Symbol sym){		
		if (sym != null){			
			panelPlayground.removeSymbol(sym);			
		}
	}
	
	public List<DatePoint> getObjects(){
		return panelPlayground.getTimeLine().getDatePoints();
	}
	
	public List<Symbol> getSymbols(){
		return panelPlayground.getSymbols();
	}
	
	public List<DragElement> getEvents(){
		return panelEditor.getEvents();		
	}
	
	public void load(String[][] eventListStrings, String[][] dateListStrings, String[][] symbolListStrings){
		panelEditor.loadElements(eventListStrings);
		panelEditor.loadObjects(dateListStrings, eventListStrings);
		panelEditor.loadSymbols(symbolListStrings, eventListStrings);
	}
	
}
