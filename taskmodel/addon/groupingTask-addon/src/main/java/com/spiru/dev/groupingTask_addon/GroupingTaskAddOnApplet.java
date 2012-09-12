package com.spiru.dev.groupingTask_addon;

import java.applet.Applet;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;


public class GroupingTaskAddOnApplet extends Applet{
	
	private GroupingTaskAddOnJPanel gpanel;
	
	    @Override
	    public void init() {	 	    	
	    	this.setSize(new Dimension(400,400));
	    	this.setMinimumSize(new Dimension(400,400));
	    	this.setPreferredSize(new Dimension(400,400));
	    	this.setLayout(null);	    		    		    
	    	
	    	List<String> params = new ArrayList<String>();
	    	String param = this.getParameter("e0");
	    	int anz = 0;
	    	while(param != null){
	    		params.add(param);
	    		anz++;
	    		param = this.getParameter("e"+anz);	    		
	    	}
	    	
	    	String[][] elements = new String[params.size()/2][2];
	    	int j = 0;
	    	for(int k=0; k<params.size(); k++){	    		
	    		if(k % 2 == 0){
	    			// caption
	    			elements[j][0] = params.get(k);	    				    				    			
	    		}else{ 
	    			// count
	    			elements[j][1] = params.get(k);
	    			j++;
	    		}
	    	}
	    		    	 
	    	/*
	    //	try{
	    		File targetFile = new File("C:\\Users\\Yves\\Desktop\\Test.xml");
	    		Document doc = null;
				try {
					doc = new SAXBuilder().build(targetFile);
				} catch (JDOMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		Element memento = doc.getRootElement();
	    		Element dragSubTaskDef = memento.getChild("dragSubTaskDef");
	    		List<Element> xmlList = dragSubTaskDef.getChildren("BoxContainer");
	    		elements = new String[xmlList.size()][2];
				for (int i = 0; i < xmlList.size(); i++) {
					String name = xmlList.get(i).getAttributeValue("BoxName");
					String count = xmlList.get(i).getAttributeValue("count");
					elements[i][0] = " "+name+" ";
					elements[i][1] = count;
				}
				*//*
	    	elements[0][0] = "name";
	    	elements[0][1] = "6";
	    	elements[1][0] = "name323";
	    	elements[1][1] = "98";
	    	*/
	    		String image = this.getParameter("handling");
				gpanel = new GroupingTaskAddOnJPanel(elements, image);	        
		        add(gpanel);		        
		        
	    //	}
	    	//catch(Exception e){
	    		
	    	//}	    		       
	    }

	    @Override
	    public void start() {
	    	//
	    }

	    @Override
	    public void stop() {
	    }	  
	    
		public String getResult() {
			return gpanel.getPlayGround().getBase64StringFromImage();
		}
		public boolean hasChanged() {
			return true;//jpanel.getRightTextAreaContent() != jpanel.getLeftTextAreaContent();
		}
}
