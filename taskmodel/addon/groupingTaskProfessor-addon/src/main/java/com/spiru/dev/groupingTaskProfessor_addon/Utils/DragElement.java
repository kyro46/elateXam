package com.spiru.dev.groupingTaskProfessor_addon.Utils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import com.spiru.dev.groupingTaskProfessor_addon.GroupingTaskAddOnJPanel;

/**
 * Klasse fuer Elemente, die angeordnet werden sollen
 * @author Yves
 *
 */
public class DragElement extends JPanel implements DragGestureListener, Comparable<DragElement>{		
		
	private int boxId=-1;
	static int idElement=0;// start
	private int elementId=-1;
	/** Anzahl an Elementen, die momentan zur Verfuegung stehen */
	private String anz;
	/** wieviele Elemente es maximal gibt */
	private String anzAnfang;
	/** String, der das Element repraesentiert */
	private String name;
	/** JLabel fuer Caption */
	private JLabel labelCaption;
	/** JLabel fuer Anzahl vorhandener Objekte von diesem Element */
	private JLabel labelAnz;
	/** Kleiner Button auf dem Element, um eine Verbindung zu einem anderen zu ziehen */
	private JButton jButtonVerbindung;
	private boolean marked = false;
	private static MouseListenerElement mouseElementListener = new MouseListenerElement();
	private static GroupingTaskAddOnJPanel panel;
	
	
	public static void setPanel(GroupingTaskAddOnJPanel pa){
		panel = pa;
	}
	/**
	 * Konstruktor eines Elementes
	 * @param text Repraesentiert das Element
	 * @param anz Anzahl Objekte von diesem Element
	 * @param listener MyMouseListener fuer Mausaktionen
	 */
	public DragElement (String name, String anz, MyMouseListener listener,  int initBoxId, int initPlayId){
		if (initPlayId == -1){
			this.boxId = initBoxId;
		}else{
			this.boxId = initBoxId;
			this.elementId = initPlayId;
			if (idElement <= initPlayId){
				idElement = initPlayId +1;
			}
		}
		labelCaption = new JLabel(" "+name+" ", JLabel.CENTER);
		labelCaption.setBorder(BorderFactory.createLineBorder(Color.black));	
		this.add(labelCaption);		
		// Element liegt auf ElementePanel
		if (anz!=null){
			// 2 Labels untereinander
			this.setLayout(new GridLayout(2,1,5,2));
			if(anz.equals("n")){
				anz = "\u221e";
			}
			labelAnz = new JLabel(""+anz, JLabel.CENTER);
			labelAnz.setBorder(BorderFactory.createLineBorder(Color.black));			
			this.add(labelAnz);
			this.addMouseListener(mouseElementListener);
		}
		// Element liegt auf Spielplatz
		else{
			// Label mit Caption links, kleiner Button fuer Verbindung rechts
			this.setLayout(null);
			FontMetrics fm = getFontMetrics(labelCaption.getFont());			
			labelCaption.setBounds(5,5,fm.stringWidth(name)+25,20);
			// Verbindungsbutton
			jButtonVerbindung = new JButton("+");
			jButtonVerbindung.setActionCommand(GroupingTaskAddOnJPanel.PLUS_ACTION);
			jButtonVerbindung.addMouseListener(listener);						
			jButtonVerbindung.setFont(new Font("Arial", Font.PLAIN, 12));
			jButtonVerbindung.setBounds(labelCaption.getWidth()+10,5,20,20);
			jButtonVerbindung.setMargin(new Insets(-10,-2,-8,-2));	
			jButtonVerbindung.setFocusable(false);
			this.setBounds(0,0,jButtonVerbindung.getWidth()+labelCaption.getWidth()+20,30);
			this.add(jButtonVerbindung);
		}
		
		this.setBackground(Color.PINK);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.anz = anz;
		this.anzAnfang = anz;
		this.name = name;
		// Element kann Drag
		DragSource ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(this,
            DnDConstants.ACTION_COPY, this);                     
	}	
	
	static public int getNextId(){
		return idElement++;
	}
	
	public int getBoxId(){
		return boxId;
	}
	
	public int getPlayId(){
		return elementId;
	}

	//@Override
	public void dragGestureRecognized(DragGestureEvent event) {
        Cursor cursor = null;
        DragElement element = this;        
        // Element soll nur kopiert werden
        if (event.getDragAction() == DnDConstants.ACTION_COPY) {
            cursor = DragSource.DefaultCopyDrop;
        }
        // Drag starten, Cursor uebergeben sowie Element, das gedropt werden soll
        event.startDrag(cursor, new TransferableElement(element));
		
	}	
	
	/**
	 * Markiert ein Element oder hebt Markierung auf
	 * @param mark true, Markierung wird gesetzt; false, Markierung wird aufgehoben
	 */
	public void markiereElement(boolean mark){		
		Border border = this.getBorder();
		Border margin;
		
		if (mark){
			margin = new LineBorder(Color.YELLOW,2);
			this.setBorder(new CompoundBorder(border, margin));
			labelCaption.setForeground(Color.YELLOW);	
			marked=true;
			if(elementId==-1){
				panel.setEditMode(this);
			}
		}
		else{
			margin = new LineBorder(Color.BLACK,1);
			this.setBorder(margin);
			labelCaption.setForeground(Color.BLACK);
			marked=false;
			if(elementId==-1){
				panel.setEditMode(null);
			}
		}
	}
	
	public boolean isElementMarked(){
		return marked;
	}		
	
	/**
	 * liefert Namen des Objektes
	 * @return Name des Objektes
	 */
	public String getCaption(){
		return name;
	}
	
	/**
	 * veringert Anzahl an vorhandenen Elementen 
	 */
	public void decAnz(){
		if (!anz.equals("\u221e"))
			anz = (Integer.parseInt(anz)-1)+"";	
		labelAnz.setText(""+anz);		
	}
	
	/**
	 * erhoeht Anzahl an vorhandenen Elementen
	 */
	public void incAnz(){
		if (!anz.equals("\u221e"))
			anz = (Integer.parseInt(anz)+1)+"";
		labelAnz.setText(""+anz);
	}
	
	/**
	 * liefert Anzahl noch vorhandener Elemente
	 * @return Anzahl momentan vorhandener Elemente
	 */
	public int getAnz(){
		if (anz.equals("\u221e"))
			return 200;
		return Integer.parseInt(anz);
	}
	
	/**
	 * setzt Anzahl momentan vorhandener Elemente wieder auf ausgangswert
	 */
	public void reset(){
		anz = anzAnfang;
		labelAnz.setText(anz);
	}	
	
	public String getMaxCount(){
		return anzAnfang;
	}
	
	@Override
	public int compareTo(DragElement a) {
        if( this.getX() < a.getX() )
            return -1;
        if( this.getX() > a.getX() )
            return 1;
            
        return 0;
	}
	
	public void changeCaption(String text){
		labelCaption.setText(text);
		name = text;
		FontMetrics fm = getFontMetrics(labelCaption.getFont());			
		labelCaption.setBounds(5,5,fm.stringWidth(name)+30,20);
		if (labelAnz == null){
			// auf spielplatz
			this.setSize(jButtonVerbindung.getWidth()+labelCaption.getWidth()+20,30);
			jButtonVerbindung.setLocation(labelCaption.getWidth()+10, 5);
		}
	}
	
	public void changeCount(String anzMax, String anzNow){
		this.anzAnfang = anzMax;
		this.anz = anzNow;
		labelAnz.setText(anzNow);
		
	}
}
