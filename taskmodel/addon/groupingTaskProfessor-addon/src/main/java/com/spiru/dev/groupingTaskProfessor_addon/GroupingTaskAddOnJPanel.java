package com.spiru.dev.groupingTaskProfessor_addon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.ScrollPane;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.spiru.dev.groupingTaskProfessor_addon.Utils.DragElement;
import com.spiru.dev.groupingTaskProfessor_addon.Utils.JPanelEditor;
import com.spiru.dev.groupingTaskProfessor_addon.Utils.MyDropTargetListener;
import com.spiru.dev.groupingTaskProfessor_addon.Utils.MyMouseListener;
import com.spiru.dev.groupingTaskProfessor_addon.Utils.PanelSpielplatz;
import com.spiru.dev.groupingTaskProfessor_addon.Utils.Verbindung;


/**
 * Panel auf dem alle anderen Objekte liegen
 * @author Yves
 *
 */
@SuppressWarnings("serial")
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
	private List<DragElement> elementList;

	private ScrollPane scrollPane;
	private String base64String = null;

	/**
	 * Creates new form AddonOnJPanel
	 * @param initElementList String-Array mit allen Captions fuer Elemente
	 */
	public GroupingTaskAddOnJPanel(int width, int height, String mementoxml_as_string/*String[][] allElements*/) {
	//	this.setDoubleBuffered(false);
		elementList = new ArrayList<DragElement>();
		listener = new MyMouseListener();
		// Jetzt ist alles toll
		initComponents(width,height);
		load(mementoxml_as_string);
		jPanelSpielplatz.setBase64String(base64String);
	}

	/**  
	 * Getter fuer Liste mit allen Elementen
	 * @return List von zur Auswahl stehenden Elementen
	 */
	public List<DragElement> getComponentList(){    	
		return elementList;
	}

	/**
	 * initialisiert alle Komponenten
	 * @param initElementList String-Array mit allen Captions fuer Elemente
	 */
	private void initComponents(int width, int height) {   
		int h = height;
		// Panel mit zur Auswahl stehenden Elementen
		jPanelElements = new JPanel(); 
		jPanelElements.setDoubleBuffered(false);

		//******************************
		JPanelEditor jpanelEditor = new JPanelEditor(0,0,width,65, listener, elementList, this);    	
		this.add(jpanelEditor);
		h-=65;
		//******************************
/*
    	// add Elements
    	if (allElements != null)
    		for(int i = 0; i<allElements.length; i++){
    			DragElement element = new DragElement(allElements[i][0],allElements[i][1],null);
    			jPanelElements.add(element);
    			elementList.add(element);  
    		}
    		*/    	
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
		scrollPane = new ScrollPane();
		scrollPane.add(jPanelElements);
		scrollPane.setSize(width, 75); 	
		h-=75;
		scrollPane.setLocation(0,65);

		// Panel auf dem Elemente angeordnet werden sollen    	
		jPanelSpielplatz = new PanelSpielplatz(elementList, listener);    
		//jPanelSpielplatz.setBackground(Color.LIGHT_GRAY);
		jPanelSpielplatz.setBorder(BorderFactory.createLineBorder(Color.black));
		// jPanelSpielplatz soll auf Drop reagieren
		new MyDropTargetListener(jPanelSpielplatz);
		// damit Listener Methoden aufrufen kann
		listener.setPanel(jPanelSpielplatz);


		// Panels anordnen
		jPanelElements.setLocation(0,0);
		jPanelElements.setSize(width,75);		
		jPanelButtons.setLocation(0,jPanelElements.getHeight()+jpanelEditor.getHeight());
		jPanelButtons.setSize(width,40);
		h-=40;
		
		jPanelSpielplatz.setLocation(0,jPanelButtons.getHeight()+jPanelElements.getHeight());   

		this.setLayout(null);
		this.setLocation(0,0);
		this.setSize(width,height);
		this.add(scrollPane);
		this.add(jPanelButtons);

		// Groesse des Panels fuer ScrollPane wichtig, sonst wird es nicht angezeigt
		jPanelSpielplatz.setPreferredSize(new Dimension(700,700));
		JScrollPane scroll = new JScrollPane(jPanelSpielplatz,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);    	
		scroll.setMinimumSize(new Dimension(160, 200));
		scroll.setPreferredSize(new Dimension(160, 200));
		scroll.setBounds(0,jPanelButtons.getHeight()+jPanelElements.getHeight()+jpanelEditor.getHeight(),width,h);        
		this.add(scroll);    

		this.setDoubleBuffered(false);
	}    

	public void paint(Graphics g){    	    	    	    	 
		scrollPane.paintAll(scrollPane.getGraphics());
		super.paint(g);
	}

	public void addElements(){
		for(DragElement n:elementList){
			jPanelElements.add(n);
		}
		repaint();
	}

	public void deleteElement(){
		
		/*
		 * test!!!
		 * 
		 */
		save();
		
		List<DragElement> deleteList = new ArrayList<DragElement>();
		List<DragElement> deleteListPlayGround = new ArrayList<DragElement>();
		for(DragElement n: elementList){
			if (n.isElementMarked()){
				jPanelElements.remove(n);
				for(int i=0; i<jPanelSpielplatz.getElemente().size(); i++){
					DragElement de = jPanelSpielplatz.getElemente().get(i); 
					if (de.getCaption().equals(n.getCaption())){						
						deleteListPlayGround.add(de);
					}
				}
				deleteList.add(n);				
			}
		}
		boolean del = true;
		while(del){
			del=false;
			for(int i=0; i<jPanelSpielplatz.getElemente().size(); i++){
				DragElement n = jPanelSpielplatz.getElemente().get(i);
				for(int j=0; j<deleteListPlayGround.size(); j++){
					DragElement k = deleteListPlayGround.get(j);
					if(k.getCaption().equals(n.getCaption())){
						deleteListPlayGround.remove(k);
						jPanelSpielplatz.removeElement(k);
						del=true;
						break;
					}
				}
			}
		}
		elementList.removeAll(deleteList);
		jPanelElements.paintAll(jPanelElements.getGraphics());
	}

	
	public String save() {
		ArrayList<ArrayList<DragElement>> sortedLists = sort();
		String ret = "";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			Element Memento = document.createElement("Memento");
			document.appendChild(Memento);
			Element dragSubTaskDef = document.createElement("dragSubTaskDef");
			Memento.appendChild(dragSubTaskDef);
			for(DragElement n:elementList) {
				Element BoxContainer = document.createElement("BoxContainer");
				BoxContainer.setAttribute("boxName",n.getCaption());
				String count = n.getMaxCount();
				if(count.equals("\u221e")){
					count = "n";
				}
				BoxContainer.setAttribute("count", count);
				BoxContainer.setAttribute("boxID",""+n.getId());
				dragSubTaskDef.appendChild(BoxContainer);
			}
			// alle DragElements aufm spielplatz
			for(DragElement n:jPanelSpielplatz.getElemente()) {
				Element dragElement = document.createElement("DragElement");
				dragElement.setAttribute("id",""+n.getOrderID());
				for(DragElement k:elementList){
					if(k.getCaption().equals(n.getCaption())){
						dragElement.setAttribute("boxID",""+k.getId());
						break;
					}					
				}
				dragElement.setAttribute("x",""+n.getX());
				dragElement.setAttribute("y",""+n.getY());
				dragSubTaskDef.appendChild(dragElement);
			}
			
			// save solution
			for(ArrayList<DragElement> list:sortedLists){
				for(DragElement el:list){
					// liste mit allen Kanten
					ArrayList<Verbindung> connections = new ArrayList<Verbindung>();
					for(Verbindung con : jPanelSpielplatz.getVerbindungen()){
						if(con.find(el)){
							connections.add(con);
						}
					}
					String kanten = "";
					// das andere Element holen
					for(Verbindung con:connections){
						DragElement el2 = con.getElement1();
						if (el2 == el)
							el2 = con.getElement2();
						if(el2.getOrderID()>el.getOrderID()){
							kanten += el2.getOrderID()+",";
						}
					}
					Element solution = document.createElement("Solution");					
					solution.setAttribute("fromID",""+el.getOrderID());
					solution.setAttribute("toIDs",""+kanten);
					if (!kanten.equals(""))
						dragSubTaskDef.appendChild(solution);
					
				}
			}
			
			// write DOM to string
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StringWriter stringWriter = new StringWriter();
			StreamResult result =  new StreamResult(stringWriter);
			transformer.transform(source, result);
			ret = stringWriter.toString();
			// having it as base64 string so browsers won't complain
			System.out.println(ret);
			ret = DatatypeConverter.printBase64Binary(ret.getBytes("utf-8"));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public void addElement(String name, String count, String id){
		DragElement de = new DragElement(name, count, id, listener);
		elementList.add(de);
		jPanelElements.add(de);
	}
	
	public void load(String xml) {
		byte[] text = null; // needed for ByteArrayInputStream
		if (xml == null) { // Ist NULL, wenn Applet nicht von HTML, sondern von Eclipse aus gestartet wird
			xml = testXml;
			try { text = xml.getBytes("utf-8"); } catch (UnsupportedEncodingException e) { e.printStackTrace(); }
		} else if (xml.length() == 0) {
			return; // assumption: Not editing existing-, but adding new Question -> nothing to do
		} else {
			// from moodle we will get a base64 string
			text = DatatypeConverter.parseBase64Binary(xml);
		}
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringComments(true);
		factory.setCoalescing(true); // Convert CDATA to Text nodes
		factory.setNamespaceAware(false); // No namespaces: this is default
		factory.setValidating(false); // Don't validate DTD: also default
		try {
			DocumentBuilder parser = factory.newDocumentBuilder();
			Document document = parser.parse(new InputSource(new ByteArrayInputStream(text)));
			//Document document = parser.parse(new File("/home/rrae/src/SHK2012/Dropbox/ElateXamV2Team/SHK/Yves/Test.xml"));
			Element Memento = (Element) document.getFirstChild(); //document.getChildNodes().item(0);
			/* getElementsByTagName always operates in the context of element it is called on.
			 * If called on Element, only child elements by the given tag name would be accessed.
			 * Do not confuse this with Document.getElementsByTagName(), which returns all 
			 * elements by the given tag name in the hole document. */
			Element dragSubTaskDef = (Element) Memento.getElementsByTagName("dragSubTaskDef").item(0);
			// list all BoxContainer
			NodeList boxcontainers = dragSubTaskDef.getElementsByTagName("BoxContainer");			
			for (int i = 0; i < boxcontainers.getLength(); i++) {
				Element boxContainer = (Element) boxcontainers.item(i);
				String name = boxContainer.getAttribute("boxName");
				String count = boxContainer.getAttribute("count");
				String id = boxContainer.getAttribute("boxID");
				addElement(name, count, id);
			}
			// list all used DragElements
			NodeList dragElements = dragSubTaskDef.getElementsByTagName("DragElement");			
			for (int i = 0; i < dragElements.getLength(); i++) {
				Element dragElement = (Element) dragElements.item(i);
				String boxID = dragElement.getAttribute("boxID");
				int id = Integer.parseInt(dragElement.getAttribute("id"));
				int  x = Integer.parseInt(dragElement.getAttribute("x"));
				int  y = Integer.parseInt(dragElement.getAttribute("y"));
				// add Element
				for(DragElement n:elementList){
					if(n.getId() == Integer.parseInt(boxID)){
						DragElement de = new DragElement(n.getCaption(), null, null, listener);
						de.addMouseListener(listener);
						n.decAnz();
						de.setOrderID(id);
						jPanelSpielplatz.getElemente().add(de);
						jPanelSpielplatz.add(de);
						de.setLocation(x, y);
					}
				}
			}
			// create all connectionLines
			NodeList solutions = dragSubTaskDef.getElementsByTagName("Solution");			
			for (int i = 0; i < solutions.getLength(); i++) {
				Element sol = (Element) solutions.item(i);
				int fromID = Integer.parseInt(sol.getAttribute("fromID"));
				String toIDs = sol.getAttribute("toIDs");
				String[] to = toIDs.split(",");
				// find start Element
				DragElement de = null;				
				for(int pos=0; pos<jPanelSpielplatz.getElemente().size(); pos++){
					DragElement d = jPanelSpielplatz.getElemente().get(pos); 					
					if(d.getOrderID() == fromID){										
						de = d;
					}
				}
				// find element2 and create line
				for(int line=0; line<to.length; line++){
					if(to[line].equals("") || to[line] == null){						
						continue;
					}
					DragElement d2 = null;
					for(int pos=0; pos<jPanelSpielplatz.getElemente().size(); pos++){
						DragElement d = jPanelSpielplatz.getElemente().get(pos); 
						if(d.getOrderID() == Integer.parseInt(to[line])){										
							d2 = d;
						}						
					}					
					if (de != null && d2 != null){
						Verbindung ver = new Verbindung(de,d2);
						jPanelSpielplatz.addVerbindung(ver);
					}
				}				
			}
			return;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	
	String testXml = 
	"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"+
	"<Memento>"+
	    "<dragSubTaskDef>"+
        "<BoxContainer boxID=\"0\" boxName=\"Lebewesen\" count=\"1\"/>"+
        "<BoxContainer boxID=\"1\" boxName=\"Tier\" count=\"2\"/>"+
        "<BoxContainer boxID=\"2\" boxName=\"Pflanze\" count=\"3\"/>"+
        "<BoxContainer boxID=\"3\" boxName=\"Mensch\" count=\"4\"/>"+
        "<BoxContainer boxID=\"4\" boxName=\"Pilze\" count=\"5\"/>"+
        "<BoxContainer boxID=\"5\" boxName=\"Saeugetier\" count=\"5\"/>"+
        "<BoxContainer boxID=\"6\" boxName=\"Eierleger\" count=\"8\"/>"+
        "<BoxContainer boxID=\"7\" boxName=\"Fliegenpilz\" count=\"11\"/>"+
        "<BoxContainer boxID=\"8\" boxName=\"Elefantenfuss\" count=\"100\"/>"+
        "<BoxContainer boxID=\"9\" boxName=\"AAAAAAAAA\" count=\"n\"/>"+
        "<DragElement boxID=\"0\" id=\"0\" x=\"261\" y=\"11\"/>"+
        "<DragElement boxID=\"1\" id=\"1\" x=\"128\" y=\"74\"/>"+
        "<DragElement boxID=\"2\" id=\"2\" x=\"267\" y=\"70\"/>"+
        "<DragElement boxID=\"3\" id=\"7\" x=\"112\" y=\"200\"/>"+
        "<DragElement boxID=\"4\" id=\"3\" x=\"447\" y=\"72\"/>"+
        "<DragElement boxID=\"5\" id=\"4\" x=\"66\" y=\"134\"/>"+
        "<DragElement boxID=\"6\" id=\"5\" x=\"213\" y=\"137\"/>"+
        "<DragElement boxID=\"7\" id=\"6\" x=\"420\" y=\"142\"/>"+
        "<DragElement boxID=\"8\" id=\"8\" x=\"323\" y=\"192\"/>"+
        "<Solution fromID=\"0\" toIDs=\"2,1,3,\"/>"+
        "<Solution fromID=\"1\" toIDs=\"4,5,\"/>"+
        "<Solution fromID=\"2\" toIDs=\"8,\"/>"+
        "<Solution fromID=\"3\" toIDs=\"6,\"/>"+
        "<Solution fromID=\"4\" toIDs=\"7,\"/>"+
	    "</dragSubTaskDef>"+
	"</Memento>";
	
	
	private ArrayList<ArrayList<DragElement>> sort(){
		List<DragElement> all = jPanelSpielplatz.getElemente();		
				
		ArrayList<ArrayList<DragElement>> dragList = new ArrayList<ArrayList<DragElement>>();
				
		// Sortiert alle Elemente in einer Zeile in eine Liste
		for(int i=0; i<jPanelSpielplatz.getHeight()/60; i++){
			ArrayList<DragElement> list = new ArrayList<DragElement>();
			for(DragElement n:all){
				if(n.getY()>= i*60 && n.getY()<i*60+60){
					list.add(n);
				}
			}
			if (list.size() != 0){	
				java.util.Collections.sort(list);
				dragList.add(list);
				
			}
		}
				
		int id = 0;
		for(ArrayList<DragElement> k:dragList){
			for(DragElement x:k){
				x.setOrderID(id++);
			}
		}		
		return dragList;
	}
}
