package com.spiru.dev.timeTask_addon;

import java.applet.Applet;
import java.awt.Dimension;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class TimeTaskAddOnApplet extends Applet{
	
	private TimeTaskAddOnJPanel gpanel;
	
	    @Override
	    public void init() {
	    	int width = this.getWidth();
	    	this.setSize(new Dimension(width,420));
	    	this.setMinimumSize(new Dimension(width,420));
	    	this.setPreferredSize(new Dimension(width,420));
	    	this.setLayout(null);	  	    
	    	boolean isCorrected = Boolean.parseBoolean(this.getParameter("corrected"));
	    	gpanel = new TimeTaskAddOnJPanel(width, isCorrected);	  
	    	load();
	        add(gpanel);   	
	    }

	    public void load(){	    	
				String xml = getParameter("param");		
				boolean isCorrected = Boolean.parseBoolean(this.getParameter("corrected"));
				String loadFromHandling = null; 
				loadFromHandling = getParameter("handling");
				byte[] x = null; // needed for ByteArrayInputStream
				if (xml == null) return;
				// assumption: Not editing existing-, but adding new Question -> nothing to do
				if (xml.length() == 0) return;
				// from moodle we will get a base64 string
				x = DatatypeConverter.parseBase64Binary(xml);
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				factory.setIgnoringComments(true);
				factory.setCoalescing(true); // Convert CDATA to Text nodes
				factory.setNamespaceAware(false); // No namespaces: this is default
				factory.setValidating(false); // Don't validate DTD: also default
				
				try{					
					DocumentBuilder parser = factory.newDocumentBuilder();
					Document document = parser.parse(new InputSource(new ByteArrayInputStream(x)));
					Element memento = (Element) document.getElementsByTagName("Memento").item(0);									
					Element timelineSubTaskDef = (Element) memento.getElementsByTagName("timelineSubTaskDef").item(0);
					NodeList assignedEventList = timelineSubTaskDef.getElementsByTagName("assignedEvent");									
					for (int i = 0; i < assignedEventList.getLength(); i++) {
						Element assignedEvent = (Element) assignedEventList.item(i);						
						String id = assignedEvent.getAttribute("id");
						String name = assignedEvent.getAttribute("name");
						String color = assignedEvent.getAttribute("color");
						gpanel.addElement(id, name, color);						
					}
					NodeList dateList = timelineSubTaskDef.getElementsByTagName("date");
					for(int i=0; i<dateList.getLength(); i++){
						Element date = (Element) dateList.item(i);
						String datePoint1 = date.getAttribute("datePoint1");
						String datePoint2 = date.getAttribute("datePoint2");
						String dPasTextbox = date.getAttribute("whichDatePointAsTextbox");																			
						if (dPasTextbox == null){
						// visible = true bei beiden
							gpanel.addDatePoint(datePoint1, true, null, isCorrected);
							gpanel.addDatePoint(datePoint2, true, null, isCorrected);
						}
						else if (dPasTextbox.equals("all")){
						// visible = false bei beiden
							String datePointStudent1 = date.getAttribute("datePointStudent1");
							String datePointStudent2 = date.getAttribute("datePointStudent2");
							gpanel.addDatePoint(datePoint1, false, datePointStudent1, isCorrected);
							gpanel.addDatePoint(datePoint2, false, datePointStudent2, isCorrected);
						}
						else if (dPasTextbox.equals("datePoint1")){
						// datePoint1 as Textbox
							String datePointStudent1 = date.getAttribute("datePointStudent1");
							gpanel.addDatePoint(datePoint1, false,datePointStudent1, isCorrected);
							gpanel.addDatePoint(datePoint2, true, null, isCorrected);
						}
						else{
						// datePoint2 as Textbox
							String datePointStudent2 = date.getAttribute("datePointStudent2");
							gpanel.addDatePoint(datePoint1, true, null, isCorrected);
							gpanel.addDatePoint(datePoint2, false, datePointStudent2, isCorrected);
						}
						if(loadFromHandling != null && loadFromHandling.equals("true")){
							NodeList correctAssignmentIDList = date.getElementsByTagName("correctAssignmentID");
							for(int k=0; k<correctAssignmentIDList.getLength(); k++){
								Element n = (Element) correctAssignmentIDList.item(k);
								String eventID = n.getAttribute("eventId");
								String posX = n.getAttribute("PosX");
								String posY = n.getAttribute("PosY");
								String lineX = n.getAttribute("LineX");
								gpanel.addSymbol(eventID, posX, posY, lineX);
							}
						}
					}
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    }

	    
	    @Override
	    public void start() {	   	    	
	    }

	    @Override
	    public void stop() {
	    }	    
	    
		public String getResult() {		
			return gpanel.save();
		}
		
		public boolean hasChanged() {
			return true;
		}
}
