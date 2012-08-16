package com.spiru.dev.groupingTask_addon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.spiru.dev.groupingTask_addon.Utils.MyMouseListener;
import com.spiru.dev.groupingTask_addon.Utils.Element;
import com.spiru.dev.groupingTask_addon.Utils.MyDropTargetListener;
import com.spiru.dev.groupingTask_addon.Utils.PanelSpielplatz;

/**
 * Panel auf dem alle anderen Objekte liegen
 * @author Yves
 *
 */
public class GroupingTaskAddOnJPanel extends JPanel {
	
	/** Fuer Mausaktionen der Buttons und zum Markieren von Elementen */
	private MyMouseListener listener;	
	/**  Action-Command zum Loeschen eines Selektierten Objektes */
	public static final String DELETE_ACTION = "delete";
	/**  Action-Command zum Loeschen aller Objekte auf der Arbeitsflaeche */
	public static final String DELETE_ALL_ACTION = "delete_all";
	/**  Action-Command zum Ziehen einer Verbindung */		
	public static final String PLUS_ACTION = "+";
	/** Button zum Loeschen des selektierten Objektes */
    private JButton jButtonDelete;
    /** Button zum loeschen aller Objekte auf der Arbeitsflaeche*/
    private JButton jButtonDeleteAll;
    /** Panel auf dem die zur Auswahl stehenden Elemente stehen */
    private JPanel jPanelElements;
    /** Panel auf das Elemente gezogen werden */
    private PanelSpielplatz jPanelSpielplatz;
    /** Liste mit allen zur Auswahl stehenden Elementen */
    private List<Element> elementList;

    /**
     * Creates new form AddonOnJPanel
     * @param initElementList String-Array mit allen Captions fuer Elemente
     */
    public GroupingTaskAddOnJPanel(String[][] allElements) {
    	elementList = new ArrayList<Element>();
    	listener = new MyMouseListener();
        initComponents(allElements);
    }
    
    /**  
     * Getter fuer Liste mit allen Elementen
     * @return List von zur Auswahl stehenden Elementen
     */
    public List<Element> getComponentList(){    	
    	return elementList;
    }

    /**
     * initialisiert alle Komponenten
     * @param initElementList String-Array mit allen Captions fuer Elemente
     */
    private void initComponents(String[][] allElements) {
    	// Panel mit zur Auswahl stehenden Elementen
    	jPanelElements = new JPanel();      	    	    	
    	// add Elements
    	if (allElements != null)
    		for(int i = 0; i<allElements.length; i++){
    			Element element = new Element(allElements[i][0],allElements[i][1],null);
    			jPanelElements.add(element);
    			elementList.add(element);  
    		}       	
    	// Panel mit allen Buttons
    	JPanel jPanelButtons = new JPanel();
    	// Button zum loeschen des selektierten Elementes
    	jButtonDelete = new JButton("Selektion entfernen");
    	jButtonDelete.setActionCommand(DELETE_ACTION);
    	jButtonDelete.addMouseListener(listener);    	
    	// Button um alles zu loeschen
    	jButtonDeleteAll = new JButton("Alles entfernen");
    	jButtonDeleteAll.setActionCommand(DELETE_ALL_ACTION);
    	jButtonDeleteAll.addMouseListener(listener);
    	
    	jPanelButtons.add(jButtonDelete);
    	jPanelButtons.add(jButtonDeleteAll);
    	
    	// ScrollPane, falls mehr Elemente auf Panel als dargestellt werden koennen
    	ScrollPane scrollPane = new ScrollPane();
    	scrollPane.add(jPanelElements);
    	scrollPane.setSize(400, 75); 	   
    	
    	// Panel auf dem Elemente angeordnet werden sollen    	
    	jPanelSpielplatz = new PanelSpielplatz(elementList, listener);    
    	jPanelSpielplatz.setBackground(Color.GRAY);
    	jPanelSpielplatz.setBorder(BorderFactory.createLineBorder(Color.black));
    	// jPanelSpielplatz soll auf Drop reagieren
    	new MyDropTargetListener(jPanelSpielplatz);
    	// damit Listener Methoden aufrufen kann
    	listener.setPanel(jPanelSpielplatz);
    	
    	
    	// Panels anordnen
    	jPanelElements.setLocation(0,0);
    	jPanelElements.setSize(400,75);
    	jPanelButtons.setLocation(0,jPanelElements.getHeight());
    	jPanelButtons.setSize(400,40);
    	jPanelSpielplatz.setLocation(0,jPanelButtons.getHeight()+jPanelElements.getHeight());
    	
    	this.setLayout(null);
    	this.setLocation(0,0);
    	this.setSize(400,400);
    	this.add(scrollPane);
    	this.add(jPanelButtons);
    	
    	// Groesse des Panels fuer ScrollPane wichtig, sonst wird es nicht angezeigt
    	jPanelSpielplatz.setPreferredSize(new Dimension(600,600));
    	JScrollPane scroll = new JScrollPane(jPanelSpielplatz,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
    			JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);    	
    	scroll.setMinimumSize(new Dimension(160, 200));
        scroll.setPreferredSize(new Dimension(160, 200));
        scroll.setBounds(0,jPanelButtons.getHeight()+jPanelElements.getHeight(),400,275);        
    	this.add(scroll);    	
    }    
}
