package com.spiru.dev.groupingTaskProfessor_addon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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
	public GroupingTaskAddOnJPanel(String mementoxml_as_string/*String[][] allElements*/) {
		this.setDoubleBuffered(false);
		elementList = new ArrayList<DragElement>();
		listener = new MyMouseListener();
		// Jetzt ist alles toll
		initComponents(load(mementoxml_as_string));
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
	private void initComponents(String[][] allElements) {    	
		// Panel mit zur Auswahl stehenden Elementen
		jPanelElements = new JPanel(); 
		jPanelElements.setDoubleBuffered(false);

		//******************************
		JPanelEditor jpanelEditor = new JPanelEditor(0,0,400,65, listener, elementList, this);    	
		this.add(jpanelEditor);
		//******************************

    	// add Elements
    	if (allElements != null)
    		for(int i = 0; i<allElements.length; i++){
    			DragElement element = new DragElement(allElements[i][0],allElements[i][1],null);
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
		scrollPane = new ScrollPane();
		scrollPane.add(jPanelElements);
		scrollPane.setSize(600, 75); 	
		scrollPane.setLocation(0,65);

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
		jPanelElements.setSize(600,75);
		jPanelButtons.setLocation(0,jPanelElements.getHeight()+jpanelEditor.getHeight());
		jPanelButtons.setSize(600,40);
		jPanelSpielplatz.setLocation(0,jPanelButtons.getHeight()+jPanelElements.getHeight());   

		this.setLayout(null);
		this.setLocation(0,0);
		this.setSize(600,400);
		this.add(scrollPane);
		this.add(jPanelButtons);

		// Groesse des Panels fuer ScrollPane wichtig, sonst wird es nicht angezeigt
		jPanelSpielplatz.setPreferredSize(new Dimension(600,600));
		JScrollPane scroll = new JScrollPane(jPanelSpielplatz,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);    	
		scroll.setMinimumSize(new Dimension(160, 200));
		scroll.setPreferredSize(new Dimension(160, 200));
		scroll.setBounds(0,jPanelButtons.getHeight()+jPanelElements.getHeight()+jpanelEditor.getHeight(),600,220);        
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
		String ret = "";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			Element Memento = document.createElement("Memento");
			document.appendChild(Memento);
			Element addonConfig = document.createElement("addonConfig");
			Memento.appendChild(addonConfig);
			Element dragSubTaskDef = document.createElement("dragSubTaskDef");
			Memento.appendChild(dragSubTaskDef);
			Element Solution = document.createElement("Solution");
			Solution.setAttribute("id",jPanelSpielplatz.getBase64StringFromImage());
			dragSubTaskDef.appendChild(Solution);
			for(DragElement n:elementList) {
				Element BoxContainer = document.createElement("BoxContainer");
				BoxContainer.setAttribute("BoxName",n.getCaption());
				BoxContainer.setAttribute("count",n.getMaxCount());
				dragSubTaskDef.appendChild(BoxContainer);
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
	
	public String[][] load(String xml) {
		byte[] x = null; // needed for ByteArrayInputStream
		if (xml == null) { // Ist NULL, wenn Applet nicht von HTML, sondern von Eclipse aus gestartet wird
			xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Memento><addonConfig /><dragSubTaskDef>" +
					"<Solution id=\"\" /><BoxContainer BoxName=\"jhhds\" count=\"5\" />" +
					"<BoxContainer BoxName=\"aaaajhhds\" count=\"n\" /></dragSubTaskDef></Memento>";
			try { x = xml.getBytes("utf-8"); } catch (UnsupportedEncodingException e) { e.printStackTrace(); }
		} else if (xml.length() == 0) {
			return null; // assumption: Not editing existing-, but adding new Question -> nothing to do
		} else {
			// from moodle we will get a base64 string
			x = DatatypeConverter.parseBase64Binary(xml);
		}
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringComments(true);
		factory.setCoalescing(true); // Convert CDATA to Text nodes
		factory.setNamespaceAware(false); // No namespaces: this is default
		factory.setValidating(false); // Don't validate DTD: also default
		try {
			DocumentBuilder parser = factory.newDocumentBuilder();
			Document document = parser.parse(new InputSource(new ByteArrayInputStream(x)));
			//Document document = parser.parse(new File("/home/rrae/src/SHK2012/Dropbox/ElateXamV2Team/SHK/Yves/Test.xml"));
			Element Memento = (Element) document.getFirstChild(); //document.getChildNodes().item(0);
			/* getElementsByTagName always operates in the context of element it is called on.
			 * If called on Element, only child elements by the given tag name would be accessed.
			 * Do not confuse this with Document.getElementsByTagName(), which returns all 
			 * elements by the given tag name in the hole document. */
			//Element addonConfig = (Element) Memento.getElementsByTagName("addonConfig").item(0); // not needed yet
			Element dragSubTaskDef = (Element) Memento.getElementsByTagName("dragSubTaskDef").item(0);
			Element solution = (Element) dragSubTaskDef.getElementsByTagName("Solution").item(0); // TODO was du damit tun willst
			this.base64String = solution.getAttribute("id");
			NodeList boxcontainers = dragSubTaskDef.getElementsByTagName("BoxContainer");
			String[][] elements = new String[boxcontainers.getLength()][2];
			for (int i = 0; i < boxcontainers.getLength(); i++) {
				Element BoxContainer = (Element) boxcontainers.item(i);
				String BoxName = BoxContainer.getAttribute("BoxName");
				String Count = BoxContainer.getAttribute("count");
				elements[i][0] = BoxName;
				elements[i][1] = Count;
			}
			return elements;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
