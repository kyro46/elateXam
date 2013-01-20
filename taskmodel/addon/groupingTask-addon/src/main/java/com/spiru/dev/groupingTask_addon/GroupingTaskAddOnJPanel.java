package com.spiru.dev.groupingTask_addon;

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

import com.spiru.dev.groupingTask_addon.Utils.MyMouseListener;
import com.spiru.dev.groupingTask_addon.Utils.DragElement;
import com.spiru.dev.groupingTask_addon.Utils.MyDropTargetListener;
import com.spiru.dev.groupingTask_addon.Utils.PanelSpielplatz;
import com.spiru.dev.groupingTask_addon.Utils.Verbindung;

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
    private JPanel jPanelButtons;
    private ScrollPane scrollPane;
    private JScrollPane scroll;
    /** Panel auf dem die zur Auswahl stehenden Elemente stehen */
    private JPanel jPanelElements;
    /** Panel auf das Elemente gezogen werden */
    private PanelSpielplatz jPanelSpielplatz;
    /** Liste mit allen zur Auswahl stehenden Elementen */
    private List<DragElement> elementList;
    private boolean oldIsProcessed = false;

    /**
     * Creates new form AddonOnJPanel
     * @param initElementList String-Array mit allen Captions fuer Elemente
     */
    public GroupingTaskAddOnJPanel(int width, int height, boolean corrected) {
    	elementList = new ArrayList<DragElement>();
    	listener = new MyMouseListener();    	
        initComponents(width, height, corrected);
    }
    
    /**  
     * Getter fuer Liste mit allen Elementen
     * @return List von zur Auswahl stehenden Elementen
     */
    public List<DragElement> getComponentList(){    	
    	return elementList;
    }
    
	public boolean isModified(){
		return jPanelSpielplatz.isModified();
	}

    /**
     * initialisiert alle Komponenten
     * @param initElementList String-Array mit allen Captions fuer Elemente
     */
    private void initComponents(int width, int height, boolean corrected) {
    	// Panel mit zur Auswahl stehenden Elementen
    	int spielpaltzHeight = height;
    	jPanelElements = new JPanel();      	    	    	    	
    	// Panel mit allen Buttons
    	jPanelButtons = new JPanel();
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
    	spielpaltzHeight-=75;
    	// Panel auf dem Elemente angeordnet werden sollen    	
    	jPanelSpielplatz = new PanelSpielplatz(elementList, listener);    
    	jPanelSpielplatz.setBackground(Color.LIGHT_GRAY);
    	jPanelSpielplatz.setBorder(BorderFactory.createLineBorder(Color.black));
    	// jPanelSpielplatz soll auf Drop reagieren
    	if (!corrected){
    		new MyDropTargetListener(jPanelSpielplatz);
    	}
    	// damit Listener Methoden aufrufen kann
    	listener.setPanel(jPanelSpielplatz);
    	
    	
    	// Panels anordnen
    	jPanelElements.setLocation(0,0);
    	jPanelElements.setSize(width,75);
    	spielpaltzHeight-=75;
    	jPanelButtons.setLocation(0,jPanelElements.getHeight());
    	jPanelButtons.setSize(width,40);
    	spielpaltzHeight-=40;
    	jPanelSpielplatz.setLocation(0,jPanelButtons.getHeight()+jPanelElements.getHeight());
    	
    	this.setLayout(null);
    	this.setLocation(0,0);
    	this.setSize(width,height);
    	this.add(scrollPane);
    	this.add(jPanelButtons);
    	
    	// Groesse des Panels fuer ScrollPane wichtig, sonst wird es nicht angezeigt
    	jPanelSpielplatz.setPreferredSize(new Dimension(1000,1000));
    	//jPanelSpielplatz.setBase64String(image);
    	scroll = new JScrollPane(jPanelSpielplatz,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
    			JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);    	
    	scroll.setMinimumSize(new Dimension(width, spielpaltzHeight));
        scroll.setPreferredSize(new Dimension(width, spielpaltzHeight));
        scroll.setBounds(0,jPanelButtons.getHeight()+jPanelElements.getHeight(),width,spielpaltzHeight);        
    	this.add(scroll);    
    	
    	//this.setDoubleBuffered(false);
    }  
    
    public PanelSpielplatz getPlayGround(){
    	return jPanelSpielplatz;
    }
    
	private void addElement(String name, String count, String id){
		DragElement de = new DragElement(name, count, listener, Integer.parseInt(id), -1);
		elementList.add(de);
		jPanelElements.add(de);
	}
    
    public void load(boolean isHandling, String xml, boolean corrected){
		byte[] text = null; // needed for ByteArrayInputStream
		if (xml == null) { // Ist NULL, wenn Applet nicht von HTML, sondern von Eclipse aus gestartet wird
			return;			
		} else if (xml.length() == 0) {
			return; 
		} else {
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
			if (!isHandling){				
				return;
			}
			NodeList isProcessed = dragSubTaskDef.getElementsByTagName("Processed");
			if (isProcessed != null){							
				Element el = (Element) isProcessed.item(0);						
				if (el != null){							
					if (el.getTextContent().equals("true")){
						oldIsProcessed = true;									
					}
					else
						oldIsProcessed = false;								
				}
			}
			else oldIsProcessed = false;
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
						DragElement de;
				    	if (corrected)
				    		 de = new DragElement(n.getCaption(), null, null, n.getBoxId(), id);
				    	else {
				    		de = new DragElement(n.getCaption(), null, listener, n.getBoxId(), id);
				    		de.addMouseListener(listener);
				    	}											
						n.decAnz();						
						jPanelSpielplatz.getElemente().add(de);
						jPanelSpielplatz.add(de);
						de.setLocation(x, y);
						break;
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
			if (corrected){
				jPanelElements.setVisible(false);
				this.remove(jPanelElements);
				jPanelButtons.setVisible(false);
				this.remove(jPanelButtons);
				scrollPane.setVisible(false);
				this.remove(scrollPane);
				scroll.setBounds(0, 0, this.getWidth(), this.getHeight());			
				this.jPanelSpielplatz.removeMouseListener(listener);
				this.repaint();
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
    
    public String save(){
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
			Element img = document.createElement("image");
			img.setTextContent(jPanelSpielplatz.getBase64StringFromImage());
			dragSubTaskDef.appendChild(img);
    		Element isProcessed = document.createElement("Processed");
    		if (isModified() || oldIsProcessed){    			
    			isProcessed.setTextContent("true");    			
    		}
    		else{
    			isProcessed.setTextContent("false");    			
    		}
    		// alle zur Wahl stehenden DragElemente
    		dragSubTaskDef.appendChild(isProcessed);
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

}
