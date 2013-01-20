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
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
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
	private JPanelEditor jpanelEditor;

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
		DragElement.setPanel(this);
		int h = height;
		// Panel mit zur Auswahl stehenden Elementen
		jPanelElements = new JPanel(); 
		jPanelElements.setDoubleBuffered(false);

		//******************************
		jpanelEditor = new JPanelEditor(0,0,width,65, listener, elementList, this);    	
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
		//save();
		
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
	
	public void updateElementPanel(){
		jPanelElements.paintAll(jPanelElements.getGraphics());
	}

	
	public String save() {
		List<DragElement> list = jPanelSpielplatz.getElemente();						
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
				BoxContainer.setAttribute("boxID",""+n.getBoxId());
				dragSubTaskDef.appendChild(BoxContainer);
			}
			// alle DragElements aufm spielplatz
			for(DragElement n:jPanelSpielplatz.getElemente()) {
				Element dragElement = document.createElement("DragElement");
				dragElement.setAttribute("id",""+n.getPlayId());
				dragElement.setAttribute("boxID",""+n.getBoxId());				
				dragElement.setAttribute("x",""+n.getX());
				dragElement.setAttribute("y",""+n.getY());
				dragSubTaskDef.appendChild(dragElement);
			}
			
			// save solution			
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
						if(el2.getPlayId()>el.getPlayId()){
							kanten += el2.getPlayId()+",";
						}
					}
					Element solution = document.createElement("Solution");					
					solution.setAttribute("fromID",""+el.getPlayId());
					solution.setAttribute("toIDs",""+kanten);
					if (!kanten.equals(""))
						dragSubTaskDef.appendChild(solution);
					
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
			//System.out.println(ret);
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
		DragElement de = new DragElement(name, count, listener, Integer.parseInt(id), -1);
		elementList.add(de);
		jPanelElements.add(de);
	}
	
	public void load(String xml) {
		byte[] text = null; // needed for ByteArrayInputStream
		if (xml == null) { // Ist NULL, wenn Applet nicht von HTML, sondern von Eclipse aus gestartet wird
			return;
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
					if(n.getBoxId() == Integer.parseInt(boxID)){
						DragElement de = new DragElement(n.getCaption(), null, listener,  n.getBoxId(), id);
						de.addMouseListener(listener);
						n.decAnz();						
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
					if(d.getPlayId() == fromID){										
						de = d;
					}
				}
				// find element2 and create line
				for(int line=0; line<to.length; line++){
					if(to[line] == null || to[line].equals("")){							
						continue;
					}
					DragElement d2 = null;
					for(int pos=0; pos<jPanelSpielplatz.getElemente().size(); pos++){
						DragElement d = jPanelSpielplatz.getElemente().get(pos); 
						if(d.getPlayId() == Integer.parseInt(to[line])){										
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
	
	public void setEditMode(DragElement el){
		jpanelEditor.setEditMode(el);
	}
	
	public void changeElementCaption(String ori, String change){
		jPanelSpielplatz.changeElementCaption(ori, change);
		jPanelSpielplatz.repaint();
	}
	
	public int getCountOfElement(DragElement el){
		return jPanelSpielplatz.getCountOfElement(el);
	}
	
	public void changeElementCount(DragElement el, String anzMax){
		if (anzMax.equals("\u221e")){
			el.changeCount(anzMax, anzMax);
		}else{
			int anzNow = jPanelSpielplatz.getCountOfElement(el);
			el.changeCount(anzMax, ""+(Integer.parseInt(anzMax)-anzNow));
		}
		
	}
	
	public PanelSpielplatz getPlayground(){
		return jPanelSpielplatz;
	}
	
}
