package com.spiru.dev.timeTaskProfessor_addon;

import java.applet.Applet;
import java.awt.Dimension;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

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

import com.spiru.dev.timeTaskProfessor_addon.Utils.DatePoint;
import com.spiru.dev.timeTaskProfessor_addon.Utils.DragElement;
import com.spiru.dev.timeTaskProfessor_addon.Utils.Symbol;

public class TimeTaskAddOnApplet extends Applet{
	
	private TimeTaskAddOnJPanel gpanel;
	
	    @Override
	    public void init() {	
	    	int width = this.getWidth();
	    	int height = this.getHeight();
	    	if (height < 400)
	    		height = 400;
	    	if (width < 400)
	    		width = 400;
	    	
	    	this.setSize(new Dimension(width,height));
	    	this.setMinimumSize(new Dimension(width,height));
	    	this.setPreferredSize(new Dimension(width,height));
	    	this.setLayout(null);	    	
	    	//gpanel = new TimeTaskAddOnJPanel(this, width);
	    	gpanel = new TimeTaskAddOnJPanel(width, height);
	    	//load();
	        add(gpanel);	  
	        load();
	    }
	    
	    @Override
	    public void start() {	   	    	
	    }

	    @Override
	    public void stop() {	    	    	
	    }
	    
		/**
		 * Interface for Javascript, accessible via
		 *   document.getElementsById("appletid").getMemento()
		 *
		 * @return XML containing the Memento Part of the Addon as String 
		 */	    
		public String getMemento() {
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
				// get Event list and save
				List<DragElement> events = gpanel.getEvents();
				for(DragElement event:events){
					Element assignedEvent = document.createElement("Event");
					assignedEvent.setAttribute("id", ""+event.getId());
					assignedEvent.setAttribute("name",event.getCaption());
					assignedEvent.setAttribute("color",String.valueOf(event.getColor().getRGB()));
					timelineSubTaskDef.appendChild(assignedEvent);
				}
				// get ObjectList and save
				List<DatePoint> objList = gpanel.getObjects();
				for(DatePoint obj : objList){
					Element assignedEvent = document.createElement("Date");
					assignedEvent.setAttribute("id", ""+obj.getPos());
					assignedEvent.setAttribute("name", obj.getCaption());
					assignedEvent.setAttribute("visible", ""+obj.isDateVisible());
					if (obj.getSymbol() != null)
						assignedEvent.setAttribute("connected", ""+obj.getSymbol().getId());
					timelineSubTaskDef.appendChild(assignedEvent);
				}
				List<Symbol> symbols = gpanel.getSymbols();
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
			return decode(ret);
		}							
		
		private String decode(String xml){
			try {
				return DatatypeConverter.printBase64Binary(xml.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {				
				e.printStackTrace();
			}
			return null;
		}

	    public void load(){
				String xml = getParameter("memento");	
				//xml = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9Im5vIj8+PG1lbWVudG8+PHRpbWVsaW5lU3ViVGFza0RlZj48RXZlbnQgY29sb3I9Ii0xNjc3Njk2MSIgaWQ9IjAiIG5hbWU9IkEiLz48RXZlbnQgY29sb3I9Ii0xMTEwNjUwMyIgaWQ9IjEiIG5hbWU9IkIiLz48RXZlbnQgY29sb3I9Ii0xNTg1OTA1IiBpZD0iMiIgbmFtZT0iQyIvPjxFdmVudCBjb2xvcj0iLTEyNzQ0NzU4IiBpZD0iMyIgbmFtZT0iRCIvPjxFdmVudCBjb2xvcj0iLTEyOTg3MTk3IiBpZD0iNCIgbmFtZT0iUSIvPjxFdmVudCBjb2xvcj0iLTEwMDQwMzIwIiBpZD0iNSIgbmFtZT0iV2FzIHNvbGwgaGllciByZWluPyIvPjxFdmVudCBjb2xvcj0iLTc2NDI0NjEiIGlkPSI2IiBuYW1lPSJJY2ggaGFiZSBrZWluZSBBaG51bmcuIi8+PERhdGUgaWQ9IjEiIG5hbWU9IkciIHZpc2libGU9InRydWUiLz48RGF0ZSBpZD0iMiIgbmFtZT0iQSIgdmlzaWJsZT0idHJ1ZSIvPjxEYXRlIGNvbm5lY3RlZD0iMCIgaWQ9IjMiIG5hbWU9IkIiIHZpc2libGU9ImZhbHNlIi8+PERhdGUgY29ubmVjdGVkPSIyIiBpZD0iNCIgbmFtZT0iQyIgdmlzaWJsZT0idHJ1ZSIvPjxEYXRlIGlkPSI1IiBuYW1lPSJEIiB2aXNpYmxlPSJmYWxzZSIvPjxEYXRlIGNvbm5lY3RlZD0iNCIgaWQ9IjYiIG5hbWU9IkUiIHZpc2libGU9InRydWUiLz48RGF0ZSBjb25uZWN0ZWQ9IjMiIGlkPSI3IiBuYW1lPSJGIiB2aXNpYmxlPSJmYWxzZSIvPjxTeW1ib2wgaWRFdmVudD0iMSIgeD0iNTUiIHhPbkxpbmU9IjI1MCIgeT0iMzciLz48U3ltYm9sIGlkRXZlbnQ9IjUiIHg9IjM1MiIgeE9uTGluZT0iMzY1IiB5PSIyMiIvPjxTeW1ib2wgaWRFdmVudD0iNiIgeD0iMjM1IiB4T25MaW5lPSIxMjUiIHk9IjU4Ii8+PC90aW1lbGluZVN1YlRhc2tEZWY+PC9tZW1lbnRvPg==";
				// needed for ByteArrayInputStream
				byte[] xbyte = null; 
				if (xml == null) return;
				// assumption: Not editing existing-, but adding new Question -> nothing to do
				if (xml.length() == 0) return;
				// from moodle we will get a base64 string			
				xbyte = DatatypeConverter.parseBase64Binary(xml); 				
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				factory.setIgnoringComments(true);
				factory.setCoalescing(true); // Convert CDATA to Text nodes
				factory.setNamespaceAware(false); // No namespaces: this is default
				factory.setValidating(false); // Don't validate DTD: also default				
				try{
					DocumentBuilder parser = factory.newDocumentBuilder();
					Document document = parser.parse(new InputSource(new ByteArrayInputStream(xbyte)));
					Element memento = (Element) document.getFirstChild();
					Element timelineSubTaskDef = (Element) memento.getElementsByTagName("timelineSubTaskDef").item(0);
					NodeList assignedEventList = timelineSubTaskDef.getElementsByTagName("Event");	
					String[][] eventListStrings = new String[assignedEventList.getLength()][3];
					for (int i = 0; i < assignedEventList.getLength(); i++) {
						Element assignedEvent = (Element) assignedEventList.item(i);						
						String id = assignedEvent.getAttribute("id");
						String name = assignedEvent.getAttribute("name");
						String color = assignedEvent.getAttribute("color");
						eventListStrings[i][0] = id;
						eventListStrings[i][1] = name;
						eventListStrings[i][2] = color;
					}
					NodeList dateList = timelineSubTaskDef.getElementsByTagName("Date");
					String[][] dateListStrings = new String[dateList.getLength()][4];
					for(int i=0; i<dateList.getLength(); i++){
						Element date = (Element) dateList.item(i);
						String id = date.getAttribute("id");
						String name = date.getAttribute("name");
						String visible = date.getAttribute("visible");
						String connected = date.getAttribute("connected");	
						dateListStrings[i][0] = id;
						dateListStrings[i][1] = name;
						dateListStrings[i][2] = visible;
						dateListStrings[i][3] = connected;
					}
					NodeList symbols = timelineSubTaskDef.getElementsByTagName("Symbol");
					String[][] symbolListStrings = new String[symbols.getLength()][4];
					for(int i=0; i<symbols.getLength(); i++){
						Element symbol = (Element) symbols.item(i);
						String id = symbol.getAttribute("idEvent");
						String x = symbol.getAttribute("x");
						String y = symbol.getAttribute("y");
						String xOnLine = symbol.getAttribute("xOnLine");	
						symbolListStrings[i][0] = id;
						symbolListStrings[i][1] = x;
						symbolListStrings[i][2] = y;
						symbolListStrings[i][3] = xOnLine;
					}
					gpanel.load(eventListStrings, dateListStrings, symbolListStrings);
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    }
	    

}
