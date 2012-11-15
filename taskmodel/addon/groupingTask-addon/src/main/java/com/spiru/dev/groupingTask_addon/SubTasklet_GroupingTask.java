package com.spiru.dev.groupingTask_addon;

import java.util.List;

import de.thorstenberger.taskmodel.complex.complextaskhandling.AddOnSubTasklet;

public interface SubTasklet_GroupingTask extends AddOnSubTasklet {
	

	public String getAnswer();
	//public String getLastCorrectedAnswer();

	//public String getGroupingGradeDoc();
	//public double getGroupingScore();

	public int getTextFieldWidth();
	public int getTextFieldHeight();
	
	public List<String> getBoxContainerAttributes();
	public boolean loadFromHandling();
	public String getImage();
	public String getMemento();
	public String getMementoFromTaskDef();	
	
}


