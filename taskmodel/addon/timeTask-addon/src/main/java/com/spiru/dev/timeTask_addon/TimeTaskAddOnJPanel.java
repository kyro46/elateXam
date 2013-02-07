package com.spiru.dev.timeTask_addon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

import com.spiru.dev.timeTask_addon.Utils.ConnectionLine;
import com.spiru.dev.timeTask_addon.Utils.DatePoint;
import com.spiru.dev.timeTask_addon.Utils.DragElement;
import com.spiru.dev.timeTask_addon.Utils.JPanelOfElements;
import com.spiru.dev.timeTask_addon.Utils.JPanelPlayGround;
import com.spiru.dev.timeTask_addon.Utils.MyDropTargetListener;
import com.spiru.dev.timeTask_addon.Utils.MyKeyListener;
import com.spiru.dev.timeTask_addon.Utils.MyMouseListener;
import com.spiru.dev.timeTask_addon.Utils.Symbol;


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
	private MyMouseListener mouseListener = null;	
	private JScrollPane scrollPanePlayground;
	private JScrollPane scrollPane;
	private boolean isMuster = false;
	
	private boolean oldIsProcessed = false;
	
    public TimeTaskAddOnJPanel(int width, boolean corrected, boolean isMuster) {
    	this.isMuster = isMuster;
    	init(width, corrected);
    }
    
    @Override
    public void paint(Graphics g){
    	scrollPanePlayground.paintAll(scrollPanePlayground.getGraphics());
    	super.paint(g);
    }
    
    private void init(int width, boolean corrected){   
    	mouseListener = new MyMouseListener();    	
    	jpanelOfElements = new JPanelOfElements(mouseListener);
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
    	scrollPane = new JScrollPane(jpanelOfElements);
    	scrollPane.setBounds(0,0,width,60);
    	jpanelButtons.setBounds(0,60,width,40);
    	    	
    	scrollPanePlayground = new JScrollPane(jpanelPlayground);
    	scrollPanePlayground.setBounds(0,100,width,320);
    	    	
    	this.add(scrollPane);
    	if(!corrected){    		
    		this.add(jpanelButtons);
    		new MyDropTargetListener(jpanelPlayground);    		
    	}    	
    	jpanelPlayground.setCorrected(corrected);
    	mouseListener.setCorrected(corrected);
    	
    	this.add(scrollPanePlayground);
    	this.setLayout(null);
    	this.setBounds(0,0,width,420);
    }       
    
    public void addElement(String id, String name, String color){
    	Color c = new Color(Integer.parseInt(color));
    	DragElement e = new DragElement(name, c);
    	addElement(e);    	
    }
    
    public boolean isModified(){
    	return jpanelPlayground.isModified();
    }
    
    public void placeAllSymbols(){
    	List<DragElement> eList = new ArrayList<DragElement>();
    	eList = jpanelOfElements.getElementList();
    	int row = 20;
    	int column = 20;
    	for(DragElement n:eList){
    		jpanelPlayground.addSymbol(n, new Point(row, column));
    		row+=30;
    		if (row > 300 && row>jpanelPlayground.getTimeLine().getLine().getX2()){
    			column+=25;
    			row = 20;
    		}
    	}
    }
    
    public void addElement(DragElement e){
    	for(DragElement n:jpanelOfElements.getElementList()){
    		if (e.getColor() == n.getColor() || e.getCaption().equals(n.getCaption())){
    			return;
    		}
    	}
    	jpanelOfElements.getElementList().add(e);
    	jpanelOfElements.add(e);
    	scrollPane.paintAll(scrollPane.getGraphics());
    }
        
    public void sortDatePoint(LinkedList<DatePoint> datePoints){    	
    	jpanelPlayground.getTimeLine().sortDatePoints(datePoints);
    	int w = 20;
    	for(DatePoint n:jpanelPlayground.getTimeLine().getDatePoints()){
    		w += n.getWidth() + 10;
    	}    	
    	jpanelPlayground.setPreferredSize( new Dimension(w,jpanelPlayground.getHeight()));
    	scrollPanePlayground.paintAll(scrollPanePlayground.getGraphics());    	
    }
    
    
    public void addSymbol(String eventID, String posX, String posY, String lineX){
    	List<DragElement> elementList = jpanelOfElements.getElementList();
    	List<Symbol> symbolList = jpanelPlayground.getSymbols();
    	
    	for(DragElement n:elementList){
    		if(n.getId() == Integer.parseInt(eventID)){       			
    			Point pos = new Point(Integer.parseInt(posX),Integer.parseInt(posY));
    			Symbol sym = new Symbol(pos,n.getColor(),n.getId());
    			symbolList.add(sym);    
    			if (lineX !=null)
    				sym.setConnectionLine(new ConnectionLine(sym, Integer.parseInt(lineX), (int)jpanelPlayground.getTimeLine().getLine().getP1().getY()));
    			jpanelPlayground.add(sym);
    			sym.addMouseListener(mouseListener);
    			scrollPane.paintAll(scrollPane.getGraphics());
    			break;
    		}
    	}    	
    }    
    
    private String escapeHtml(String text){
    	text = text.replaceAll("Ä", "&Auml;");
    	text = text.replaceAll("ä", "&auml;");
    	text = text.replaceAll("Ö", "&Ouml;");
    	text = text.replaceAll("ö", "&ouml;");
    	text = text.replaceAll("Ü", "&Uuml;");
    	text = text.replaceAll("ü", "&uuml;");    	
    	text = text.replaceAll("ß", "&szlig;");
    	text = text.replaceAll("€", "&euro;");
    	return text;
    }
   
    public String save(){    	
		String ret = null;
		try {
			// create Document
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder;
			documentBuilder = factory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			Element memento = document.createElement("Memento");
			document.appendChild(memento);	  
			Element timelineSubTaskDef = document.createElement("timelineSubTaskDef");
			memento.appendChild(timelineSubTaskDef);
						
    		Element isProcessed = document.createElement("Processed");
    		if (isModified() || oldIsProcessed){    			
    			isProcessed.setTextContent("true");    			
    		}
    		else{
    			isProcessed.setTextContent("false");    			
    		}
    		timelineSubTaskDef.appendChild(isProcessed);
					
    		Element img = document.createElement("image");
			img.setTextContent(jpanelPlayground.getBase64StringFromImage());
			timelineSubTaskDef.appendChild(img);
    		
			// get Event list and save
			List<DragElement> events = jpanelOfElements.getElementList();
			for(DragElement event:events){
				Element assignedEvent = document.createElement("Event");
				assignedEvent.setAttribute("id", ""+event.getId());
				assignedEvent.setAttribute("name",escapeHtml(event.getCaption()));
				assignedEvent.setAttribute("color",String.valueOf(event.getColor().getRGB()));
				timelineSubTaskDef.appendChild(assignedEvent);
			}
			// get ObjectList and save
			List<DatePoint> objList = jpanelPlayground.getTimeLine().getDatePoints();
			for(DatePoint obj : objList){
				Element assignedEvent = document.createElement("Date");
				assignedEvent.setAttribute("id", ""+obj.getId());
				assignedEvent.setAttribute("name", escapeHtml(obj.getCaption()));
				assignedEvent.setAttribute("visible", ""+obj.isDateVisible());
				if (obj.getSymbol() != null){
					assignedEvent.setAttribute("connected", ""+obj.getSymbol().getId());
					if (obj.isDateVisible()){
						// Date visible -> getTextFromSymbol
						String text = obj.getSymbol().getText();
						text = escapeHtml(text);
						assignedEvent.setAttribute("text", text);
					}
					else{
						// Date !visible -> getTextFromDate
						String text = obj.getDateFromStudent();
						text = escapeHtml(text);
						assignedEvent.setAttribute("text", text);
					}
				}
				else{
					if (!obj.isDateVisible()){
						// Date !visible -> getTextFromDate
						String text = obj.getDateFromStudent();
						text = escapeHtml(text);
						assignedEvent.setAttribute("text", text);
					}
					
				}
				timelineSubTaskDef.appendChild(assignedEvent);
			}
			List<Symbol> symbols = jpanelPlayground.getSymbols();
			for(Symbol sym : symbols){
				if (sym.getDatePoint() != null){
					// saved with Object
					continue;
				}
				Element assignedEvent = document.createElement("Symbol");
				assignedEvent.setAttribute("idEvent", ""+sym.getId());					
				assignedEvent.setAttribute("x", ""+sym.getX());
				assignedEvent.setAttribute("y", ""+sym.getY());				
				if (sym.getConnectionLine() != null){
					assignedEvent.setAttribute("xOnLine", ""+Math.round(sym.getConnectionLine().getLine().getX2()));
				}
				timelineSubTaskDef.appendChild(assignedEvent);
			}
			// write DOM to string
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StringWriter stringWriter = new StringWriter();
			StreamResult result =  new StreamResult(stringWriter);
			transformer.transform(source, result);
			ret = stringWriter.toString();			
		} catch (ParserConfigurationException e) {				
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		return ret;
    }
    
    public void setProcessed(boolean value){
    	oldIsProcessed = value;
    }
    
    public void load(String[][] elementsList, String[][] objList, String[][] symbolsList){
    	load(elementsList, objList);
		// idEvent, x, y, xOnLine
		// fuer alle symbole
		for(int i=0; i<symbolsList.length; i++){
			// finde id von DragElement
			String name = null;
			for(int k=0; k<elementsList.length; k++){				
				if(symbolsList[i][0].equals(elementsList[k][0])){
					name = elementsList[k][1];					
					break;
				}
			}
			// finde Element nach name
			for(DragElement n:jpanelOfElements.getElementList()){
				if(n.getCaption().equals(name)){
					Symbol sym = new Symbol(new Point(Integer.parseInt(symbolsList[i][1]), Integer.parseInt(symbolsList[i][2])), n.getColor(), n.getId());
					if(symbolsList[i][3].length()>0){
						ConnectionLine line = new ConnectionLine(sym, Integer.parseInt(symbolsList[i][3]), (int)jpanelPlayground.getTimeLine().getLine().getY1());
						sym.setConnectionLine(line);
					}
					jpanelPlayground.addSymbol(sym);					
					break;
				}
			}			
		}	
    }
    
    public void load(String[][] elementsList, String[][] objList){
		// id, name, color
		for(int i=0; i<elementsList.length; i++){
			DragElement el = new DragElement(elementsList[i][1], new Color(Integer.parseInt(elementsList[i][2])));
			addElement(el);			
			//el.addMouseListener(mouseListener);
		}		
		MyKeyListener keyLis = new MyKeyListener(jpanelPlayground);
		// id, name, visible, connected, text
		LinkedList<DatePoint> datePoints = new LinkedList<DatePoint>();
		for(int i=0; i<objList.length; i++){
			DatePoint dp = null;
			if (objList[i][2].equals("true") || isMuster)
				dp = new DatePoint(objList[i][1], true, Integer.parseInt(objList[i][0]), keyLis);				
			else{
				dp = new DatePoint(objList[i][1], false, Integer.parseInt(objList[i][0]), keyLis);
				dp.setDateFromStudent(objList[i][4]);				
			}
	    	jpanelPlayground.add(dp); 	    	
	    	dp.setBackground(Color.PINK);						
			// find Element			
			for(int k=0; k<elementsList.length; k++){
				if(objList[i][3].equals(elementsList[k][0])){					
					for(DragElement de:jpanelOfElements.getElementList()){
						if (de.getCaption().equals(elementsList[k][1])){							
							Symbol sym = new Symbol(new Point(2,2),de.getColor(),de.getId());
							sym.setDatePoint(dp, keyLis);								
							if (dp.isDateVisible()){
								if (!isMuster)
									de.hideCaption();
								sym.setText(objList[i][4]);								
							}
							dp.setSymbol(sym);
							sym.setMuster(isMuster, jpanelOfElements.getElementList());
							jpanelPlayground.getSymbols().add(sym);
			    			jpanelPlayground.add(sym);				    	
			    			scrollPane.paintAll(scrollPane.getGraphics());
						}							
					}
				}
			}
			jpanelPlayground.getTimeLine().getDatePoints().add(dp);
			datePoints.add(dp);			
		}		
		sortDatePoint(datePoints);
		placeAllSymbols();		
    }
}
