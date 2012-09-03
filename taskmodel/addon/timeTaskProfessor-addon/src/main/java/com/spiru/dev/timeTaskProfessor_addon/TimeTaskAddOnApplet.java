package com.spiru.dev.timeTaskProfessor_addon;

import java.applet.Applet;
import java.awt.Dimension;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.spiru.dev.timeTaskProfessor_addon.Utils.DatePoint;
import com.spiru.dev.timeTaskProfessor_addon.Utils.Element;
import com.spiru.dev.timeTaskProfessor_addon.Utils.Symbol;

public class TimeTaskAddOnApplet extends Applet{
	
	private TimeTaskAddOnJPanel gpanel;
	
	    @Override
	    public void init() {	 	    	
	    	this.setSize(new Dimension(400,460));
	    	this.setMinimumSize(new Dimension(400,460));
	    	this.setPreferredSize(new Dimension(400,460));
	    	this.setLayout(null);	    	
	    	gpanel = new TimeTaskAddOnJPanel(this);	        
	        add(gpanel);	       
	    }

	    @Override
	    public void start() {	   	    	
	    }

	    @Override
	    public void stop() {
	    }
	    
	    public void save(List<Element> elements, List<DatePoint> datePoints, List<Symbol> symbols){
	    	try{
	    		File targetFile = new File("C:\\Users\\Yves\\Desktop\\TimeTest.xml");
	    		FileWriter fw = new FileWriter(targetFile);
	    		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
	    		
	    		org.jdom.Element memento = new org.jdom.Element("Memento");
	    		Document doc = new Document(memento);
	    		org.jdom.Element addonConfig = new org.jdom.Element("addonConfig");
	    		memento.addContent(addonConfig);
	    		org.jdom.Element timelineSubTaskDef = new org.jdom.Element("timelineSubTaskDef");
	    		memento.addContent(timelineSubTaskDef);
	    		
	    		for(Element n:elements){
	    			org.jdom.Element assignedEvent = new org.jdom.Element("assignedEvent");
	    			assignedEvent.setAttribute("id", ""+n.getId());
	    			assignedEvent.setAttribute("name",n.getCaption());
	    			assignedEvent.setAttribute("color",String.valueOf(n.getColor().getRGB()));
	    			timelineSubTaskDef.addContent(assignedEvent);
	    		}	
	    		// alle Intervalle prüfen
	    		for(int i=1; i<datePoints.size(); i++){
	    			org.jdom.Element date = new org.jdom.Element("date");
	    			date.setAttribute("dateId",""+i);
	    			date.setAttribute("datePoint1",datePoints.get(i-1).getCaption());
	    			date.setAttribute("datePoint2",datePoints.get(i).getCaption());
	    			if (!datePoints.get(i-1).isDateVisible() && !datePoints.get(i).isDateVisible()){
	    				date.setAttribute("wichDatePointAsTextbox","all");
	    			}else{
	    				if (!datePoints.get(i-1).isDateVisible())
	    					date.setAttribute("wichDatePointAsTextbox","datePoint1");
	    				if (!datePoints.get(i).isDateVisible())
	    					date.setAttribute("wichDatePointAsTextbox","datePoint2");	    				
	    			}
	    			timelineSubTaskDef.addContent(date);
	    			int dp1x = datePoints.get(i-1).getX()+datePoints.get(i-1).getWidth()/2;
	    			int dp2x = datePoints.get(i).getX()+datePoints.get(i).getWidth()/2;
	    			List<Symbol> listSymbols = new ArrayList<Symbol>();	    			
	    			// alle Elemente zwischen dem Intervall
	    			for(Symbol n:symbols){
	    				int sx = (int)n.getConnectionLine().getLine().getP2().getX();
	    				if(sx >= dp1x && sx <= dp2x){
	    					listSymbols.add(n);
	    				}
	    			}
	    			boolean sort = true;
	    			while(sort){
	    				sort=false;
	    				for(int k=1; k<listSymbols.size(); k++){
	    					int x1 = (int)listSymbols.get(k-1).getConnectionLine().getLine().getP2().getX();
	    					int x2 = (int)listSymbols.get(k).getConnectionLine().getLine().getP2().getX();
	    					if(x1>x2){
	    						sort=true;
	    						Symbol tmp = listSymbols.get(k-1);
	    						listSymbols.set(k-1, listSymbols.get(k));
	    						listSymbols.set(k, tmp);
	    					}
	    				}
	    			}
	    			// in xml speichern
	    			int order = 0;
	    			for(Symbol n:listSymbols){	    				
	    				org.jdom.Element correctAssignmentID = new org.jdom.Element("correctAssignmentID");
	    				correctAssignmentID.setAttribute("order",""+order++);
	    				correctAssignmentID.setAttribute("eventId",""+n.getId());
	    				int sx = (int)n.getConnectionLine().getLine().getP2().getX();
	    				if (dp1x == sx){
	    					correctAssignmentID.setAttribute("isFixedToDate","datePoint1");
	    					// test, element zum linken date nicht doppelt speichern
	    					if (i>1)
	    						continue;
	    				}
	    				else if(dp2x == sx){
	    					correctAssignmentID.setAttribute("isFixedToDate","datePoint2");
	    				}
	    				date.addContent(correctAssignmentID);
	    			}
	    		}
	    		out.output( doc, fw );
	    		fw.close();
	    	}
	    	catch (Exception e){
	    		
	    	}
	    }
}
