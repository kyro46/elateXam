package com.spiru.dev.groupingTask_addon;

import java.applet.Applet;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class GroupingTaskAddOnApplet extends Applet{
	
	private GroupingTaskAddOnJPanel gpanel;
	private Task task;
	
	    @Override
	    public void init() {	
	    	int width = this.getWidth();
	    	int height = this.getHeight();
	    	this.setSize(new Dimension(width,height));
	    	this.setMinimumSize(new Dimension(width,height));
	    	this.setPreferredSize(new Dimension(width,height));
	    	this.setLayout(null);	    		    		    
	    	/*
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
	    	}*/
	    		boolean isHandling = Boolean.parseBoolean(this.getParameter("handling"));
	    		boolean isCorrected = Boolean.parseBoolean(this.getParameter("correction"));
				gpanel = new GroupingTaskAddOnJPanel(width, height, isCorrected);
				gpanel.load(isHandling, this.getParameter("memento"), isCorrected);
		        add(gpanel);
		        Timer timer = new Timer();	
		        task = new Task(this);
		        timer.schedule  ( task, 1000, 1500 );
	    }

	    @Override
	    public void start() {
	    	//
	    }

	    @Override
	    public void stop() {
	    }	  
	    
		public String getResult() {
			return gpanel.save();
		}
		public boolean hasChanged() {
			return true;//jpanel.getRightTextAreaContent() != jpanel.getLeftTextAreaContent();
		}
		
	    public void draw(){	        	        
	        task.setTest();	              
	    }
}

class Task extends TimerTask{
	private GroupingTaskAddOnApplet a;
    private boolean test = false;
    private int count = 0;
	public Task(GroupingTaskAddOnApplet a){
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

