package com.spiru.dev.timeTask_addon;

import java.applet.Applet;
import java.awt.Dimension;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

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
	private Task task;
	private boolean oldIsProcessed = false;
	
	    @Override
	    public void init() {
	    	int width = 400;//this.getWidth();
	    	this.setSize(new Dimension(width,420));
	    	this.setMinimumSize(new Dimension(width,420));
	    	this.setPreferredSize(new Dimension(width,420));
	    	this.setLayout(null);	  	    
	    	boolean isCorrected = Boolean.parseBoolean(this.getParameter("corrected"));
	    	String muster = this.getParameter("muster");
	    	if (muster == null)
	    		muster = "false";
	    			    	
	    	boolean isMuster = Boolean.parseBoolean(muster);
	    	
	    	
	    	gpanel = new TimeTaskAddOnJPanel(width, isCorrected, isMuster);	    	
	    	String xml = getParameter("param");	    		    	
	    	String handling = getParameter("handling");		
	    	//xml = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9Im5vIj8+PG1lbWVudG8+PHRpbWVsaW5lU3ViVGFza0RlZj48RXZlbnQgY29sb3I9Ii0xNjc3Njk2MSIgaWQ9IjAiIG5hbWU9IkEiLz48RXZlbnQgY29sb3I9Ii0xMTEwNjUwMyIgaWQ9IjEiIG5hbWU9IkIiLz48RXZlbnQgY29sb3I9Ii0xNTg1OTA1IiBpZD0iMiIgbmFtZT0iQyIvPjxFdmVudCBjb2xvcj0iLTEyNzQ0NzU4IiBpZD0iMyIgbmFtZT0iRCIvPjxFdmVudCBjb2xvcj0iLTEyOTg3MTk3IiBpZD0iNCIgbmFtZT0iUSIvPjxFdmVudCBjb2xvcj0iLTEwMDQwMzIwIiBpZD0iNSIgbmFtZT0iV2FzIHNvbGwgaGllciByZWluPyIvPjxFdmVudCBjb2xvcj0iLTc2NDI0NjEiIGlkPSI2IiBuYW1lPSJJY2ggaGFiZSBrZWluZSBBaG51bmcuIi8+PERhdGUgaWQ9IjEiIG5hbWU9IkciIHZpc2libGU9InRydWUiLz48RGF0ZSBpZD0iMiIgbmFtZT0iQSIgdmlzaWJsZT0idHJ1ZSIvPjxEYXRlIGNvbm5lY3RlZD0iMCIgaWQ9IjMiIG5hbWU9IkIiIHZpc2libGU9ImZhbHNlIi8+PERhdGUgY29ubmVjdGVkPSIyIiBpZD0iNCIgbmFtZT0iQyIgdmlzaWJsZT0idHJ1ZSIvPjxEYXRlIGlkPSI1IiBuYW1lPSJEIiB2aXNpYmxlPSJmYWxzZSIvPjxEYXRlIGNvbm5lY3RlZD0iNCIgaWQ9IjYiIG5hbWU9IkUiIHZpc2libGU9InRydWUiLz48RGF0ZSBjb25uZWN0ZWQ9IjMiIGlkPSI3IiBuYW1lPSJGIiB2aXNpYmxlPSJmYWxzZSIvPjxTeW1ib2wgaWRFdmVudD0iMSIgeD0iNTUiIHhPbkxpbmU9IjI1MCIgeT0iMzciLz48U3ltYm9sIGlkRXZlbnQ9IjUiIHg9IjM1MiIgeE9uTGluZT0iMzY1IiB5PSIyMiIvPjxTeW1ib2wgaWRFdmVudD0iNiIgeD0iMjM1IiB4T25MaW5lPSIxMjUiIHk9IjU4Ii8+PC90aW1lbGluZVN1YlRhc2tEZWY+PC9tZW1lbnRvPg==";
	    	if (handling != null && handling.equals("true"))
	    		load(xml, true);
	    	else 
	    		load(xml, false);	    	
	        add(gpanel);  
	        gpanel.setProcessed(oldIsProcessed);
	        Timer timer = new Timer();	
	        task = new Task(this);
	        timer.schedule  ( task, 1000, 1500 );
	    }
	    
	    private Document getDocument(String xml){
			byte[] xbyte = null; 
			if (xml == null) return null;
			// assumption: Not editing existing-, but adding new Question -> nothing to do
			if (xml.length() == 0) return null;
			// from moodle we will get a base64 string				
			xbyte = DatatypeConverter.parseBase64Binary(xml); 				
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringComments(true);
			factory.setCoalescing(true); // Convert CDATA to Text nodes
			factory.setNamespaceAware(false); // No namespaces: this is default
			factory.setValidating(false); // Don't validate DTD: also default	
			DocumentBuilder parser;
			try {
				parser = factory.newDocumentBuilder();
				Document document = parser.parse(new InputSource(new ByteArrayInputStream(xbyte)));
				return document;
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	    }
	    
	    private String unescapeHtml(String text){
	    	text = text.replaceAll("&Auml;", "Ä");
	    	text = text.replaceAll("&auml;", "ä");
	    	text = text.replaceAll("&Ouml;", "Ö");
	    	text = text.replaceAll("&ouml;", "ö");
	    	text = text.replaceAll("&Uuml;", "Ü");
	    	text = text.replaceAll("&uuml;", "ü");    	
	    	text = text.replaceAll("&szlig;", "ß");
	    	text = text.replaceAll("&euro;", "€");
	    	return text;
	    }
	    
	    private String[][] getEventList(Element timelineSubTaskDef){
	    	NodeList assignedEventList = timelineSubTaskDef.getElementsByTagName("Event");
	    	String[][] eventListStrings = new String[assignedEventList.getLength()][3];
	    	for (int i = 0; i < assignedEventList.getLength(); i++) {
				Element assignedEvent = (Element) assignedEventList.item(i);						
				String id = assignedEvent.getAttribute("id");
				String name = unescapeHtml(assignedEvent.getAttribute("name"));
				String color = assignedEvent.getAttribute("color");
				eventListStrings[i][0] = id;
				eventListStrings[i][1] = name;
				eventListStrings[i][2] = color;
			}
	    	return eventListStrings;
	    }
	    
	    private String[][] getDateList(Element timelineSubTaskDef){
			NodeList dateList = timelineSubTaskDef.getElementsByTagName("Date");
			String[][] dateListStrings = new String[dateList.getLength()][5];
			for(int i=0; i<dateList.getLength(); i++){
				Element date = (Element) dateList.item(i);
				String id = date.getAttribute("id");
				String name = unescapeHtml(date.getAttribute("name"));
				String visible = date.getAttribute("visible");
				String connected = date.getAttribute("connected");
				String text = unescapeHtml(date.getAttribute("text"));
				dateListStrings[i][0] = id;
				dateListStrings[i][1] = name;
				dateListStrings[i][2] = visible;
				dateListStrings[i][3] = connected;
				dateListStrings[i][4] = text;
			}
			return dateListStrings;
	    }	
	    
	    private String[][] getSymbolList(Element timelineSubTaskDef){
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
			return symbolListStrings;
	    }
	    
	    private void load(String xml, boolean handling){	    	
	    	Document document = getDocument(xml);
	    	if (document == null)
	    		return;	    	
	    	Element memento = (Element) document.getFirstChild();
			Element timelineSubTaskDef = (Element) memento.getElementsByTagName("timelineSubTaskDef").item(0);
			
			if(handling){						
				NodeList isProcessed = timelineSubTaskDef.getElementsByTagName("Processed");
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
			}			
			String[][] eventListStrings = getEventList(timelineSubTaskDef);
			String[][] dateListStrings = getDateList(timelineSubTaskDef);
			
			if (handling){
				String[][] symbolListStrings = getSymbolList(timelineSubTaskDef);
				gpanel.load(eventListStrings, dateListStrings, symbolListStrings);
			}
			else
				gpanel.load(eventListStrings, dateListStrings);
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
			return gpanel.isModified();
		}
		
	    public void draw(){	        	        
	        task.setTest();	              
	    }
}

class Task extends TimerTask{
	private TimeTaskAddOnApplet a;
    private boolean test = false;
    private int count = 0;
	public Task(TimeTaskAddOnApplet a){
		this.a = a;
	}
    
    public void setTest(){
      this.test = true;
    }
	 public void run()  {      
        if(test || (count < 2 && count != 0)){
        //a.drawAll();
            a.paintAll(a.getGraphics());            
          //a.repaint();            
            test = false;     
            count++;
        }
        if (count >= 2) count = 0;
	  }	
}
